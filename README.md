# Tech Challenge: App Main (Oficina Mecânica)

Esta é a aplicação principal de gerenciamento da Oficina Mecânica, executando como um pod dentro de um cluster Kubernetes.

## Objetivo
Prover a lógica de negócios central (Cadastro e Controle das Ordens de Serviço), além de reportar métricas em tempo real para as ferramentas de observabilidade.

## Tecnologias e Infraestrutura
- Java & Spring Boot
- Docker
- Kubernetes (Manifests e Orquestração)

## Execução Local e Deploy
**(Em desenvolvimento - CI/CD irá gerar Docker Images, enviar para o Registry e realizar o Deploy no GKE)**

## Arquitetura e Monitoramento
- Possui integrações com logs estruturados (JSON) e APM (Datadog/New Relic) para latência de APIs e detecção de falhas.
- Swagger da API protegido pelas validações passadas via API Gateway (Token JWT).
