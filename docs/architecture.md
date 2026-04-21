# Documentação da Arquitetura - Fase 03

## 1. Diagrama de Componentes

```mermaid
graph TD
    Client[Cliente] -->|Requisicao| APIGateway[GCP API Gateway]
    
    APIGateway -->|Rota: /auth| CloudFunction[Cloud Function - Auth Serverless]
    APIGateway -->|Rotas: /api/*| GKE[Google Kubernetes Engine]
    
    subcluster GKE
        AppMain[Oficina API App]
        Datadog[Datadog APM Agent]
    end
    
    AppMain --> Datadog
    
    CloudFunction -->|JDBC| CloudSQL[(Cloud SQL PostgreSQL)]
    AppMain -->|JDBC/JPA| CloudSQL
    
    Datadog -->|Métricas e Traces| DatadogConsole[Dashboard Datadog]
```

## 2. Diagrama de Sequência (Autenticação e Abertura de OS)

```mermaid
sequenceDiagram
    participant C as Cliente
    participant GW as API Gateway
    participant F as Cloud Function (Auth)
    participant APP as Oficina API (GKE)
    participant DB as Cloud SQL
    
    C->>GW: POST /auth {cpf}
    GW->>F: Invoca Function
    F->>DB: SELECT id FROM clientes WHERE cpf = ?
    DB-->>F: Retorna Cliente (Existe)
    F->>F: Gera JWT assinado com chave secreta
    F-->>GW: Retorna {token: JWT}
    GW-->>C: Retorna {token: JWT}
    
    C->>GW: POST /api/ordens (Header: Bearer JWT)
    GW->>GW: Valida Assinatura JWT
    GW->>APP: Roteia requisicao /api/ordens
    APP->>APP: JwtFilter extrai CPF e permite acesso
    APP->>DB: INSERT nova OS
    DB-->>APP: Retorna OS Criada
    APP-->>GW: 201 Created
    GW-->>C: 201 Created
```

## 3. RFC: Escolha do Padrão Serverless para Autenticação
**Contexto**: A autenticação anteriormente acoplada ao monolito exigia escalabilidade e alta disponibilidade independente do resto da aplicação, visto que é o gargalo de entrada de todos os usuários.
**Decisão**: Desacoplar o serviço de geração de tokens JWT usando Google Cloud Functions (Serverless).
**Consequências**: 
- A aplicação principal agora atua de forma "stateless/passiva" em relação à autenticação, focando apenas no core business (Domain Driven Design puro).
- O API Gateway valida a camada L7 garantindo segurança contra acessos indevidos antes mesmo da requisição bater no GKE, economizando processamento.

## 4. ADR: Escolha do Datadog para Observabilidade
**Status**: Aceito.
**Contexto**: A Fase 3 exige visibilidade total, métricas e alertas.
**Decisão**: Datadog foi escolhido frente ao Prometheus/Grafana purista pela facilidade de injeção via agente (`dd-java-agent`) sem necessidade de refatoração massiva de código, fornecendo APM, logs correlacionados (com Logback JSON) e mapas de serviço "out-of-the-box".

## 5. Diagrama de Entidade-Relacionamento (ER)

```mermaid
erDiagram
    CLIENTE ||--o{ VEICULO : possui
    CLIENTE ||--o{ ORDEM_SERVICO : abre
    VEICULO ||--o{ ORDEM_SERVICO : referenciado_por
    ORDEM_SERVICO ||--o{ SERVICO_EXECUTADO : contem
    ORDEM_SERVICO ||--o{ PECA_UTILIZADA : contem
    
    CLIENTE {
        int id PK
        string nome
        string cpf
        string telefone
    }
    
    VEICULO {
        int id PK
        int cliente_id FK
        string placa
        string marca
        string modelo
    }
    
    ORDEM_SERVICO {
        int id PK
        int cliente_id FK
        int veiculo_id FK
        string status
        decimal valor_total
        datetime data_abertura
    }
```
**Justificativa**: O PostgreSQL foi mantido devido à forte integridade relacional exigida pela gestão de peças e controle financeiro de orçamentos da Oficina. A segregação clara de responsabilidades no domínio (Clientes, Veículos, OS) facilita futuras divisões para microserviços.
