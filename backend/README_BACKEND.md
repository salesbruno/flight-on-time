# ✈️ Backend — API Java (Spring Boot)

Parte do projeto **flight-on-time**, responsável por:
- Receber os dados do voo
- Enviar ao modelo de previsão
- Retornar previsão + probabilidade para o cliente

---

## 📘 1. Objetivo do Backend

O backend é responsável por:

- Expor o endpoint **POST `/predict`**
- Validar dados da requisição
- Enviar dados ao microserviço **Python (FastAPI)**
- Receber a previsão e devolver ao usuário
- Registrar logs no banco **PostgreSQL**
- Expor endpoints adicionais:
    - **GET `/stats`**
    - **GET `/airports`**
    - **GET `/companies`**
- Documentar a API com **Swagger/OpenAPI**
- Garantir segurança básica com **Spring Security**

---

## 🧱 2. Estrutura do Projeto

```
backend
│── pom.xml
│── src
│   ├── main
│   │   ├── java/com/flightontime
│   │   │   ├── controller        # Endpoints REST
│   │   │   ├── dto               # Objetos de transferência
│   │   │   ├── service           # Regras de negócio
│   │   │   ├── client            # Comunicação com FastAPI
│   │   │   ├── repository        # Repositórios Spring Data
│   │   │   ├── model             # Entidades JPA
│   │   │   ├── exception         # Tratamento de erros
│   │   │   └── security          # Configurações de segurança
│   │   └── resources
│   │       ├── application.properties
│   │       └── db/migration      # Scripts Flyway
│   └── test/java/com/flightontime

```

---

## 🔧 3. Tecnologias Utilizadas

- Java 17
- Spring Boot 3.2.x
- Spring Web / Validation / Data JPA / WebFlux
- Spring Security
- Flyway (migração de banco)
- H2 Database (ambiente local)
- PostgreSQL 16 (produção via Docker / OCI)
- Swagger (OpenAPI)
- Docker / Docker Compose
- JUnit 5 / Mockito
- Lombok
- OpenCSV (importação de dados históricos)

---

## ⚙️ 4. Dependências (pom.xml)

Principais bibliotecas:
- Spring Boot Starter Web, Validation, Data JPA, WebFlux, Security
- Lombok
- H2 Database (dev)
- PostgreSQL Driver (prod)
- Flyway Core
- Swagger/OpenAPI (springdoc)
- Jackson Datatype JSR310
- OpenCSV
- DevTools
- Spring Boot Starter Test

---

## 🧠 5. Endpoints

### 🔹 POST `/predict`
**Entrada:**
```json
{
     "companhia": "AA",
     "origem": "ABQ",
     "destino": "ATL",
     "estado_origem": "New Mexico",
     "estado_destino": "Georgia",
     "distancia_km": 2040,
     "data_partida": "2026-01-15T10:00:00"
}
```

**Saída:**

```json
{
    "previsao": "Pontual",
    "probabilidade": "0.18"
}
```

## ✈️ Endpoints da API

- **GET /airports**  
  Lista todos os aeroportos disponíveis.

- **GET /airports/distance**  
  Retorna a distância entre dois aeroportos (origem, destino).

- **GET /companies**  
  Lista todas as companhias aéreas.

- **GET /stats**  
  Retorna estatísticas de atrasos em um período.

- **GET /stats/estado**  
  Retorna estatísticas de atrasos por estado.

---

## 📂 6. Estrutura do Projeto

### Controllers
- **AirportController** → lista aeroportos.
- **AirportDistanceController** → retorna distância entre aeroportos.
- **CompanyController** → lista companhias aéreas.
- **DelayStatsController** → estatísticas de atrasos (geral e por estado).
- **VooController** → previsão de atraso de voo.

### DTOs
- **AirportResponse** → dados de aeroporto.
- **AirportDistanceResponse** → distância entre aeroportos.
- **CompanyResponse** → dados de companhia aérea.
- **DelayStatsResponse** → estatísticas gerais de atrasos.
- **DelayStatsState** → estatísticas por estado.
- **VooRequest** → requisição de previsão de voo (com validação).
- **VooResponse** → resposta da previsão.

### Services
- **AirportService** → lista aeroportos.
- **AirportDistanceService** → calcula distância (DB ou fórmula Haversine).
- **CompanyService** → lista companhias aéreas.
- **DelayStatsService** → calcula estatísticas de atrasos (geral e por estado).
- **VooService** → integra com FastAPI para previsão de atrasos.

### Repositories
- **AirportRepository** → CRUD de aeroportos.
- **AirportDistanceRepository** → CRUD de distâncias entre aeroportos.
- **CompanyRepository** → CRUD de companhias aéreas.
- **DelayStatsRepository** → histórico de voos para estatísticas.
- **VooRepository** → persistência de voos.

### Models
- **Airport** → tabela `airports`.
- **AirportDistance** → tabela `airports_distance`.
- **Company** → tabela `companies`.
- **DelayStats** → tabela `flights_historic`.
- **Voo** → tabela `voos`.

### Exception Handling
- **ApiExceptionHandler** → trata erros de validação, indisponibilidade de serviço, erros genéricos.
- **PredictionServiceUnavailableException** → serviço de predição indisponível.
- **ResourceNotFoundException** → recurso não encontrado.

### Security
- Configuração com **Spring Security**.
- Endpoints públicos: `/predict`, `/airports`, `/companies`, `/stats`, **Swagger**.
- Qualquer outro endpoint exige autenticação.
- **CORS** configurado para `http://localhost:5500`.
- Sessões **stateless** e **CSRF** desabilitado.

### Configuração (application.properties)
- Porta: **8080**.
- Banco: **PostgreSQL local (flightdb)**.
- JPA: `ddl-auto=validate`, `show-sql=true`.
- Flyway: migração automática (`db/migration`).
- Integração: `fastapi.url=http://localhost:8000/predict-model`.
- Threshold de predição: **0.5**.

---

## 🐳 7. Execução via Docker

O backend roda junto com:
- **PostgreSQL**
- **Microserviço FastAPI**

---

## ▶️ 8. Como Rodar Localmente

```bash
cd backend
mvn spring-boot:run
```

---

## 📑 9. Swagger

Disponível em: [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

---

## 🧪 10. Testes Unitários

- **JUnit 5**
- **Mockito**
- **Spring Boot Test**

---

## 🧑‍💻 11. Dev Notes

- O backend não carrega o modelo na memória.
- Toda previsão é feita via requisição ao microserviço Python.
- API simples, rápida e segura.

