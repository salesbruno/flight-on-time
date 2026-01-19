const endpointBase = "http://localhost:8080/stats";

const elStart = document.getElementById("start");
const elEnd = document.getElementById("end");
const elLoad = document.getElementById("load");
const elMeta = document.getElementById("meta");
const elTbody = document.querySelector("#table tbody");
const elStatus = document.getElementById("status");

function toIsoLocal(dt) {
    // datetime-local -> ISO sem segundos se não houver
    return dt.includes(":") ? dt + ":00" : dt + "T00:00:00";
}

function fmtProb(p) {
    return (typeof p === "number") ? p.toFixed(4) : "-";
}

function fmtKm(km) {
    return (km ?? "-");
}

function setStatus(msg, isError = false) {
    elStatus.textContent = msg || "";
    elStatus.className = isError ? "status error" : "status";
}

async function loadData() {
    setStatus("Carregando...");
    elLoad.disabled = true;
    elTbody.innerHTML = "";

    const startIso = toIsoLocal(elStart.value);
    const endIso = toIsoLocal(elEnd.value);
    const url = `${endpointBase}?start=${encodeURIComponent(startIso)}&end=${encodeURIComponent(endIso)}`;

    try {
        const res = await fetch(url);
        if (!res.ok) throw new Error(`HTTP ${res.status}`);
        const data = await res.json();

        elMeta.textContent = `Período: ${data.start} até ${data.end} | Total de voos: ${data.totalVoos}`;

        (data.resultados || []).forEach(item => {
            const v = item.voo || {};
            const tr = document.createElement("tr");
            tr.innerHTML = `
            <td>${v.data_partida ?? "-"}</td>
            <td>${v.estado_origem ?? "-"}</td>
            <td>${v.origem ?? "-"}</td>
            <td>${v.destino ?? "-"}</td>
            <td>${v.companhia ?? "-"}</td>
            <td>${v.estado_destino ?? "-"}</td>
            <td class="right">${fmtKm(v.distancia_km)}</td>
            <td class="right">${(item.probabilidade * 100).toFixed(2)}</td>
          `;
            elTbody.appendChild(tr);
        });

        setStatus(`Carregado com sucesso: ${data.resultados?.length ?? 0} linhas.`);
    } catch (err) {
        setStatus(`Falha ao carregar: ${err.message}`, true);
    } finally {
        elLoad.disabled = false;
    }
}

elLoad.addEventListener("click", loadData);
// Carrega inicial
//loadData();