const endpointBase = "http://localhost:8080/stats";
let chart;

function toIsoLocal(dt) {
    return dt.includes(":") ? dt + ":00" : dt + "T00:00:00";
}

function desenharGrafico(valores) {
    const ctx = document.getElementById("chartProb").getContext("2d");
    if (chart) chart.destroy();
    chart = new Chart(ctx, {
        type: "bar", // gráfico de barras
        data: {
            labels: valores.map((_, i) => "Voo " + (i + 1)),
            datasets: [{
                label: "Probabilidade",
                data: valores,
                backgroundColor: "rgba(11,110,253,0.6)"
            }]
        },
        options: {
            responsive: true,
            maintainAspectRatio: false,
            scales: {
                y: { beginAtZero: true, max: 1 }
            }
        }
    });
}

async function loadStats() {
    const startIso = toIsoLocal(document.getElementById("start").value);
    const endIso = toIsoLocal(document.getElementById("end").value);
    const url = `${endpointBase}?start=${encodeURIComponent(startIso)}&end=${encodeURIComponent(endIso)}`;

    try {
        const res = await fetch(url);
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        const data = await res.json();

        document.getElementById("meta").textContent =
            `Período: ${data.start} até ${data.end} | Total de voos: ${data.totalVoos}`;

        // pega probabilidades individuais para o gráfico
        const probs = (data.resultados || []).map(r => r.probabilidade);

        // usa estatísticas já calculadas pelo backend
        document.getElementById("stats").innerHTML = `
            <h2>Resultados</h2>
            <p><strong>Média:</strong> ${(data.media * 100).toFixed(2)} %</p>
            <p><strong>Mínimo:</strong> ${(data.min * 100).toFixed(2)} %</p>
            <p><strong>Máximo:</strong> ${(data.max * 100).toFixed(2)} %</p>
            <p><strong>Desvio Padrão:</strong> ${(data.desvio * 100).toFixed(2)} %</p>
        `;

        desenharGrafico(probs);
    } catch (err) {
        document.getElementById("stats").innerHTML =
            `<p style="color:red">Erro ao carregar: ${err.message}</p>`;
    }
}

document.getElementById("load").addEventListener("click", loadStats);