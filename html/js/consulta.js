// objeto global para armazenar dados carregados
const cacheDados = {};

// Carregar selects logo ao abrir a página
document.addEventListener("DOMContentLoaded", () => {
  carregarSelect("http://localhost:8080/airports", "origemSelect");
  carregarSelect("http://localhost:8080/airports", "destinoSelect");
  carregarSelect("http://localhost:8080/companies", "companhiaSelect");
});

async function carregarSelect(endpoint, selectId) {
  const response = await fetch(endpoint);
  const dados = await response.json();

  cacheDados[selectId] = dados;

  const select = document.getElementById(selectId);
  select.innerHTML = "";

  const campoOrdenacao = dados[0].airportName ? "airportName" : "name";

  dados.sort((a, b) => a[campoOrdenacao].localeCompare(b[campoOrdenacao]))
       .forEach(item => {
         const option = document.createElement("option");
         option.value = item.iataCode;

         option.textContent = campoOrdenacao === "airportName"
           ? `${item.iataCode} - ${item.airportName} (${item.cityName}/${item.stateName})`
           : `${item.iataCode} - ${item.name}`;

         select.appendChild(option);
       });
}

// Preenche estados ao selecionar origem/destino
document.getElementById("origemSelect").addEventListener("change", function(e) {
  const selecionado = cacheDados["origemSelect"].find(a => a.iataCode === e.target.value);
  if (selecionado) {
    document.getElementById("estadoOrigem").value = selecionado.stateName;
  }
});

document.getElementById("destinoSelect").addEventListener("change", function(e) {
  const selecionado = cacheDados["destinoSelect"].find(a => a.iataCode === e.target.value);
  if (selecionado) {
    document.getElementById("estadoDestino").value = selecionado.stateName;
  }
});

// Atualiza distância
async function atualizarDistancia() {
  const origem = document.getElementById("origemSelect").value;
  const destino = document.getElementById("destinoSelect").value;
  const distanciaInput = document.getElementById("distancia_km");

  if (origem && destino) {
    try {
      const response = await fetch(`http://localhost:8080/airports/distance?origem=${origem}&destino=${destino}`);
      if (response.ok) {
        const data = await response.json();
        distanciaInput.value = data && data.distance ? data.distance : "";
      } else {
        distanciaInput.value = "";
      }
    } catch (error) {
      console.error("Erro ao buscar distância:", error);
      distanciaInput.value = "";
    }
  }
}

document.getElementById("origemSelect").addEventListener("change", atualizarDistancia);
document.getElementById("destinoSelect").addEventListener("change", atualizarDistancia);

// Envio do formulário
document.getElementById("vooForm").addEventListener("submit", async function(e) {
  e.preventDefault();

  const formData = new FormData(e.target);

  const data = {
    companhia: formData.get("companhia"),
    origem: formData.get("origem"),
    destino: formData.get("destino"),
    data_partida: formData.get("data_partida"),
    distancia_km: Number(formData.get("distancia_km")) || 1,
    estado_origem: formData.get("estado_origem"),
    estado_destino: formData.get("estado_destino")
  };

  // Ajusta formato da data para ISO completo
  if (data.data_partida && data.data_partida.length === 16) {
    // "2026-01-03T18:30" → "2026-01-03T18:30:00"
    data.data_partida = data.data_partida + ":00";
  }

  const card = document.getElementById("resultado");

  try {
    const response = await fetch("http://localhost:8080/predict", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });

    if (!response.ok) throw new Error("Erro na requisição: " + response.status);

    const result = await response.json();

    card.className = "resultado-card " +
      (result.previsao === "Pontual" ? "pontual" : "atrasado");

    card.innerText = `Previsão: ${result.previsao}\nProbabilidade de atraso: ${(result.probabilidade * 100).toFixed(1)}%`;
    card.style.display = "block";
  } catch (error) {
    card.className = "resultado-card erro";
    card.innerText = "Erro: " + error.message;
    card.style.display = "block";
  }
});