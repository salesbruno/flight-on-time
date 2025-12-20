# Modelo de Predição Treinado - FlightOnTime

Este diretório contém o acesso ao artefato final do modelo de inteligência artificial desenvolvido para a predição de atrasos de voos.

### 🔗 Download do Modelo
Devido a limitações de processamento de arquivos binários (.joblib) via interface web do GitHub (Erro 400), o modelo foi disponibilizado via Google Drive para garantir a integridade do arquivo:

👉 [**Baixar modelo_atraso_voo.joblib**](https://drive.google.com/file/d/1jwScHPdoveOBGXMXaugEnDdGgqQtursp/view?usp=sharing)

---

### 🛠️ Como carregar o modelo (Python)
Para integrar este modelo em sua aplicação ou API, certifique-se de ter a biblioteca `joblib` instalada e utilize o código abaixo:

```python
import joblib

# Após realizar o download do arquivo, carregue-o no seu ambiente
modelo_final = joblib.load('modelo_atraso_voo.joblib')

# O modelo está pronto para realizar predições
# predicao = modelo_final.predict(dados_de_entrada)
