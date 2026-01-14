# Microservices com Spring Boot

Projeto completo de microserviços com Spring Boot, Docker Compose, Kafka, PostgreSQL, Saga Pattern, Resilience4j e API Gateway com JWT.

## Estrutura

```
.
├── docker-compose.yml
├── api-gateway
│   ├── Dockerfile
│   └── src/main/java
├── order-service
│   ├── Dockerfile
│   └── src/main/java
└── payment-service
    ├── Dockerfile
    └── src/main/java
```

## Como Executar

1. Compilar os serviços:
```bash
cd order-service && mvn clean package && cd ..
cd payment-service && mvn clean package && cd ..
cd api-gateway && mvn clean package && cd ..
```

2. Subir os serviços:
```bash
docker-compose up --build
```

## Segurança — JWT_SECRET

JWT tokens used by the API Gateway require a strong secret. Follow these guidelines:

- Local development: generate a secure random secret and set it in a local `.env` (do not commit):

```bash
cd /path/to/project
openssl rand -base64 32 > /dev/null && echo "JWT_SECRET=$(openssl rand -base64 32)" > .env
```

- CI/Production: never store secrets in the repository. Use your platform's secret manager and inject the value as an environment variable at runtime (e.g., GitHub Actions Secrets, Kubernetes Secrets, Vault).

- Enforce minimum length/entropy for `JWT_SECRET` (e.g., 32 bytes) and rotate periodically.

If `JWT_SECRET` is not provided, Docker Compose and the application are configured to fail-fast to avoid using insecure defaults.

3. Obter token JWT:
```bash
curl -X POST http://localhost:8082/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"user","password":"pass"}'
```

4. Criar um pedido via API Gateway:
```bash
curl -X POST http://localhost:8082/api/orders \
  -H "Authorization: Bearer <seu-token-jwt>"
```

## Serviços

- **API Gateway**: Porta 8082
- **Order Service**: Porta 8080
- **Payment Service**: Porta 8081
- **PostgreSQL**: Porta 5432
- **Kafka**: Porta 29092

## Funcionalidades Implementadas

### 1. Saga Pattern Completo
- Order Service cria pedido e publica evento `order-created`
- Payment Service processa pagamento e publica `payment-processed` ou `payment-failed`
- Order Service confirma ou cancela pedido baseado no resultado do pagamento
- Rollback automático em caso de falha no pagamento

### 2. Resilience4j no Payment Service
- **Circuit Breaker**: Protege contra falhas em cascata
- **Retry**: Tentativas automáticas com backoff exponencial
- Configuração: 3 tentativas, janela deslizante de 10 chamadas, threshold de 50% de falhas

### 3. API Gateway com JWT
- Spring Cloud Gateway como ponto único de entrada
- Autenticação JWT obrigatória para rotas protegidas
- Endpoint `/auth/login` para obter token
- Rotas protegidas: `/api/orders/**` e `/api/payments/**`

## Fluxo Completo

1. Cliente faz login no API Gateway → recebe JWT token
2. Cliente cria pedido via API Gateway com token JWT
3. Order Service cria pedido e publica evento `order-created`
4. Payment Service recebe evento, processa pagamento com Resilience4j
5. Payment Service publica `payment-processed` (sucesso) ou `payment-failed` (falha)
6. Order Service confirma ou cancela pedido baseado no evento recebido

## Patterns Aplicados

- **Clean Architecture** (order-service)
- **DDD** (Domain-Driven Design)
- **Event-Driven Architecture** (Kafka)
- **Saga Pattern** (Orquestração distribuída)
- **Circuit Breaker** (Resilience4j)
- **API Gateway** (Spring Cloud Gateway)
- **Database per Service** (PostgreSQL isolado)
- **Hexagonal Architecture** (Ports & Adapters)
