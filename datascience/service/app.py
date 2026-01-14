from fastapi import FastAPI, HTTPException
from pydantic import BaseModel
from joblib import load
import pandas as pd
import json
from datetime import datetime

app = FastAPI()

# Carrega o modelo ao iniciar a app
try:
    model = load("C:/Users/Márcio/Desktop/Flight/flight-on-time/datascience/model/modelo_atraso_voo_v1.joblib")
except Exception as e:
    raise RuntimeError(f"Falha ao carregar modelo: {e}")

# Carrega o mapeamento de categorias
try:
    with open("C:/Users/Márcio/Desktop/Flight/flight-on-time/datascience/model/mapeamento_categorias_v1.json", "r") as f:
        mapping = json.load(f)
except Exception as e:
    raise RuntimeError(f"Falha ao carregar mapeamento: {e}")

# JSON esperado
class Voo(BaseModel):
    companhia: str
    origem: str
    destino: str
    estado_origem: str
    estado_destino: str
    distancia_km: int
    data_partida: datetime

def preprocess(voo: Voo) -> pd.DataFrame:
    #dt = datetime.fromisoformat(voo.data_partida)
    dt = voo.data_partida

    data = {
        "month": dt.month,
        "day_of_week": dt.weekday() + 1,  # segunda=0 → 1, domingo=6 → 7
        "op_unique_carrier": mapping["op_unique_carrier"].get(voo.companhia, -1),
        "origin": mapping["origin"].get(voo.origem, -1),
        "origin_state_nm": mapping["origin_state_nm"].get(voo.estado_origem, -1),
        "dest": mapping["dest"].get(voo.destino, -1),
        "dest_state_nm": mapping["dest_state_nm"].get(voo.estado_destino, -1),
        "crs_dep_time": dt.hour * 100 + dt.minute,  # formato HHMM
        "distance": voo.distancia_km
    }

    col_order = list(model.feature_names_in_)
    df = pd.DataFrame([[data[c] for c in col_order]], columns=col_order)

    print("Features esperadas pelo modelo:", model.feature_names_in_)
    print("Colunas do DF:", df.columns.tolist())
    print("Valores do DF:", df.iloc[0].to_dict())

    return df

@app.post("/predict-model")
def predict(voo: Voo) -> float:
    try:
        X = preprocess(voo)
        if hasattr(model, "predict_proba"):
            prob = float(model.predict_proba(X)[:, 1][0])
            return prob
        y = float(model.predict(X)[0])
        return y
    except Exception as e:
        raise HTTPException(status_code=400, detail=f"Erro na predição: {e}")