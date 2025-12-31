# ✈️ Data Science — flight-on-time

Módulo responsável por análise de dados, preparação, criação do modelo preditivo e disponibilização do modelo através de um **microserviço FastAPI**.

## 🗺️ Arquitetura do Projeto (End-to-End)
![Diagrama de Arquitetura](docs/diagrama.png)

O fluxo de dados foi estruturado em quatro camadas principais:
* **Ingestão:** Coleta de dados brutos e armazenamento em Google Cloud Storage.
* **Processamento & ETL:** Limpeza e estruturação dos dados utilizando BigQuery e SQL.
* **Ciência de Dados & ML:** Desenvolvimento do modelo de classificação (EDA, Feature Engineering e Treinamento).
* **Entrega & Integração:** Exportação do artefato final para disponibilização via API.

O objetivo é prever se um voo será **Pontual (0)** ou **Atrasado (1)** com base em seus atributos.

---

## 📘 1. Objetivos do Módulo
* Realizar EDA (análise exploratória dos dados) e preparar features relevantes.
* Treinar um modelo de classificação robusto (**RandomForestClassifier**).
* Avaliar métricas: Acurácia, Precision, Recall e F1-Score.
* Exportar o modelo (`.joblib`) e disponibilizar o endpoint `/predict-model` para o backend Java.

---

## 🧱 2. Estrutura da pasta `datascience/`
* `notebooks/` → Notebooks de EDA, Feature Engineering e Treinamento.
* `docs/`      → Diagrama de arquitetura e documentação visual.
* `model/`     → Local para o modelo (`modelo_atraso_voo.joblib`).
* `sql/`       → Scripts de extração e refino no BigQuery.
* `service/`   → Microserviço FastAPI (produção).

---

## 🧠 3. Tecnologias Utilizadas
Linguagem: Python 3.10+

Manipulação de Dados: Pandas, NumPy

Machine Learning: Scikit-Learn (Random Forest)

Engenharia de Dados: SQL e BigQuery

API Framework: FastAPI e Uvicorn

Cloud & DevOps: Oracle Cloud (OCI), Docker, Git/GitHub

---

## 📦 4. Integração e Contrato JSON (Back-End)

Para a integração com o serviço Java, o endpoint `/predict-model` espera o seguinte formato:

```
json
{
  "companhia": "string",
  "origem": "string",
  "destino": "string",
  "data_partida": "YYYY-MM-DD HH:MM:SS",
  "distancia": "float"
}
```

---

## 🧪 5. Pipeline de Desenvolvimento

1. EDA: Identificação de padrões no notebook eda_model.ipynb.

2. Feature Engineering: Criação de variáveis de tempo e tráfego.

3. Treinamento: Modelo Random Forest salvo via Joblib.

  📌 Nota: O arquivo deve ser salvo obrigatoriamente em: datascience/model/modelo_atraso_voo.joblib.

  ⚠️ Importante: O link externo do Drive é para fins exclusivos de demonstração.

4. FastAPI: O arquivo app.py carrega o modelo e retorna a previsão e probabilidade.

---

## ⚙️ 6. Como Rodar Localmente o Microserviço

```
Bash
cd datascience/service
pip install -r requirements.txt
uvicorn app:app --reload --port 8000
Endpoint principal: POST /predict-model
```

---

## 🐳 7. Deploy e Docker
O serviço está preparado para rodar em containers Docker, facilitando o deploy na Oracle Cloud (OCI). As configurações de Dockerfile e docker-compose garantem a escalabilidade do ambiente.

---

👥 Responsável e Autoria
Este módulo de Data Science e Engenharia de Dados foi desenvolvido por:
Sueli da Hora — Analytics Engineer (Machine Learning, Modelagem Preditiva, SQL e Arquitetura).

