const endpointBase = "http://localhost:8080/stats/estado";
let chart;

function toIsoLocal(dt) {
    return dt.includes(":") ? dt + ":00" : dt + "T00:00:00";
}

async function loadStats() {
    const startIso = toIsoLocal(document.getElementById("start").value);
    const endIso = toIsoLocal(document.getElementById("end").value);
    const url = `${endpointBase}?start=${encodeURIComponent(startIso)}&end=${encodeURIComponent(endIso)}`;

    try {
        const res = await fetch(url);
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        const data = await res.json(); // já é uma lista de DelayStatsState

        const tbody = document.querySelector("#table tbody");
        tbody.innerHTML = "";

        const labels = [];
        const medias = [];

        data.forEach(stats => {
            const tr = document.createElement("tr");
            tr.innerHTML = `
                <td>${stats.state}</td>
                <td>${stats.totalFlights}</td>
                <td>${(stats.average * 100).toFixed(2)}</td>
                <td>${(stats.min * 100).toFixed(2)}</td>
                <td>${(stats.max * 100).toFixed(2)}</td>
                <td>${(stats.stdDev * 100).toFixed(2)}</td>
            `;
            tbody.appendChild(tr);

            labels.push(stats.state);
            medias.push(stats.average);
        });

        // Gráfico de barras por estado
        const ctx = document.getElementById("chartRegion").getContext("2d");
        if (chart) chart.destroy();
        chart = new Chart(ctx, {
            type: "bar",
            data: {
                labels,
                datasets: [{
                    label: "Média Probabilidade de Atraso",
                    data: medias,
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
    } catch (err) {
        document.getElementById("meta").textContent =
            `Erro ao carregar: ${err.message}`;
    }
}

document.getElementById("load").addEventListener("click", loadStats);