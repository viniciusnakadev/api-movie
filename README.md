# Movies API

    API REST desenvolvida para análise de intervalos entre prêmios do **Golden Raspberry Awards**.

A aplicação lê automaticamente ao iniciar, um arquivo CSV com os filmes vencedores e persiste os dados em um banco de dados **H2**.

A API disponibiliza um endpoint que retorna:

- O produtor com **menor intervalo entre dois prêmios consecutivos**
- O produtor com **maior intervalo entre dois prêmios consecutivos**


## Tecnologias
- Java 25
- Spring Boot
- Spring Data JPA
- H2 Database
- JUnit
- Mockito

## Como rodar o projeto
./mvnw spring-boot:run

## Como rodar os testes
./mvnw test

## Porta da aplicação
http://localhost:8080

## Base Path
/api/v1

## Banco de dados H2

Console disponível em: http://localhost:8080/api/v1/h2-console
User: sa
password:

## Endpoints
GET /api/v1/movies/awards/intervals

## Logs
Os logs são exibidos no console.
