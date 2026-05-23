# SAFT-BNDES (Sistema de Apoio ao Financiamento Tecnológico)

## Integrantes
- Luccas Miguel da Cruz dos Santos
- Mateus Balduino da Silva
- Miguel Henrique Gonsaga dos Santos

---

## Descrição
O **SAFT-BNDES** é uma aplicação Full Stack desenvolvida para transformar dados brutos de financiamentos do BNDES em informações estratégicas.  
O sistema permite consultas, filtros e análises sobre operações de crédito, apoiando pequenas empresas de tecnologia na tomada de decisão.

---

## Arquitetura
- **Frontend:** React/HTML
- **Backend:** Spring Boot (Java 17, Maven)
- **Banco de Dados:** PostgreSQL (com migrações via Flyway e JPA/Hibernate)

Fluxo:
- **Requisição:** Frontend → Controller → Service → Repository → Banco PostgreSQL
- **Retorno:** Banco PostgreSQL → Repository → Service → Controller → Frontend

---

## Instalação e Execução

### Backend (Spring Boot)
Via Maven:
```bash
mvn clean install
mvn spring-boot:run
```
Via IDE:
- Localize a classe SaftBndesApplication
- Clique em Run
- O servidor sobe em: http://localhost:8080/saft-bndes

### Frontend (React)
```bash
npm install
npm start
```
Disponível em: http://localhost:3000

---

## Endpoints da API
| Método | Rota                             | Descrição                                    | Status         |
|--------|----------------------------------|----------------------------------------------|----------------|
| GET    | /operacoes                       | Filtra por estado                            | 200 OK         |
| POST   | /operacoes/carga                 | Adiciona dados por um CSV                    | 202 Accepted   |
| GET    | /operacoes/setor                 | Filtra por setor                             | 200 OK         |
| GET    | /operacoes/valor/{valorAprovado} | Filtra com valor igual ou maior              | 200 OK         |
| GET    | /operacoes/{id}                  | Filtra por ID                                | 200 OK         |
| GET    | /operacoes/total-por-estado      | Total de financiamentos agrupados por estado | 200 OK         |
| DELETE | /operacoes/{id}                  | Deleta por ID                                | 204 No Content |

