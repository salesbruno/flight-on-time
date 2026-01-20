const endpointBase = "http://localhost:8080/stats";
let chart;

function toIsoLocal(dt) {
    return dt.includes(":") ? dt + ":00" : dt + "T00:00:00";
}

function calcularEstatisticas(valores) {
    if (!valores.length) return null;
    const n = valores.length;
    const media = valores.reduce((a, b) => a + b, 0) / n;
    const min = Math.min(...valores);
    const max = Math.max(...valores);
    return { media, min, max };
}

function getRelativeColor(valor, min, max) {
    // normaliza entre 0 e 1
    const ratio = (valor - min) / (max - min || 1);

    // interpolação simples: verde (0), amarelo (0.5), vermelho (1)
    if (ratio < 0.33) return "rgba(40,167,69,0.7)";     // verde
    if (ratio < 0.66) return "rgba(255,193,7,0.7)";     // amarelo
    return "rgba(220,53,69,0.7)";                       // vermelho
}

function desenharGrafico(labels, medias) {
    const ctx = document.getElementById("chartCompany").getContext("2d");
    if (chart) chart.destroy();

    const min = Math.min(...medias);
    const max = Math.max(...medias);

    chart = new Chart(ctx, {
        type: "bar",
        data: {
            labels,
            datasets: [{
                label: "Probabilidade média de atraso por companhia",
                data: medias,
                backgroundColor: medias.map(v => getRelativeColor(v, min, max))
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

        // Agrupar por companhia
        const grupos = {};
        (data.resultados || []).forEach(r => {
            const companhia = r.voo?.companhia ?? "Desconhecida";
            if (!grupos[companhia]) grupos[companhia] = [];
            grupos[companhia].push(r.probabilidade);
        });

        const tbody = document.querySelector("#table tbody");
        tbody.innerHTML = "";

        const labels = [];
        const medias = [];

        Object.entries(grupos).forEach(([companhia, probs]) => {
            const stats = calcularEstatisticas(probs);
            const tr = document.createElement("tr");
            tr.innerHTML = `
            <td>${companhia}</td>
            <td>${probs.length}</td>
            <td>${(stats.media*100).toFixed(2)}</td>
            <td>${(stats.min*100).toFixed(2)}</td>
            <td>${(stats.max*100).toFixed(2)}</td>
          `;
            tbody.appendChild(tr);

            labels.push(companhia);
            medias.push(stats.media);
        });

        // Gráfico de barras por companhia
        desenharGrafico(labels, medias);
    } catch (err) {
        document.getElementById("meta").textContent =
            `Erro ao carregar: ${err.message}`;
    }

}

document.getElementById("load").addEventListener("click", loadStats);