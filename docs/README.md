# 🏗️ Desafio Fullstack Integrado
🚨 Instrução Importante (LEIA ANTES DE COMEÇAR)
❌ NÃO faça fork deste repositório.

Este repositório é fornecido como modelo/base. Para realizar o desafio, você deve:
✅ Opção correta (obrigatória)
  Clique em “Use this template” (se este repositório estiver marcado como Template)
OU
  Clone este repositório e crie um NOVO repositório público em sua conta GitHub.
📌 O resultado deve ser um repositório próprio, independente deste.

## 🎯 Objetivo
Criar solução completa em camadas (DB, EJB, Backend, Frontend), corrigindo bug em EJB e entregando aplicação funcional.

## 📦 Estrutura
- db/: scripts schema e seed
- ejb-module/: serviço EJB com bug a ser corrigido
- backend-module/: backend Spring Boot
- frontend/: app Angular
- docs/: instruções e critérios
- .github/workflows/: CI

## ✅ Tarefas do candidato
1. Executar db/schema.sql e db/seed.sql
2. Corrigir bug no BeneficioEjbService
3. Implementar backend CRUD + integração com EJB
4. Desenvolver frontend Angular consumindo backend
5. Implementar testes
6. Documentar (Swagger, README)
7. Submeter via fork + PR

## 🐞 Bug no EJB
- Transferência não verifica saldo, não usa locking, pode gerar inconsistência
- Espera-se correção com validações, rollback, locking/optimistic locking

## 📊 Critérios de avaliação
- Arquitetura em camadas (20%)
- Correção EJB (20%)
- CRUD + Transferência (15%)
- Qualidade de código (10%)
- Testes (15%)
- Documentação (10%)
- Frontend (10%)


🎯 Objetivo do Desafio
Desenvolver uma solução Fullstack completa, organizada em camadas, corrigindo 
um bug crítico no EJB e entregando uma aplicação funcional com:

- Backend Spring Boot
- EJB com lógica de negócio
- Banco de dados relacional
- Frontend Angular
- Testes automatizados
- Documentação técnica (Swagger + README)

📦 Estrutura do Projeto
├── db/
│   ├── schema.sql
│   └── seed.sql
├── ejb-module/
│   └── BeneficioEjbService
├── backend-module/
│   ├── controllers
│   ├── services
│   ├── repositories
│   ├── dto / mapper
│   └── config
├── frontend/
│   └── Angular App
├── docs/
│   └── instruções
└── .github/workflows/

🧠 Arquitetura da Solução
A aplicação foi organizada seguindo boas práticas de separação de responsabilidades:

🔹 Camada EJB
- Responsável pela regra de negócio crítica (transferência entre benefícios)
- Utiliza JPA + EntityManager
- Executa validações de:
  * existência dos benefícios
  * valor positivo
  * saldo suficiente
- Opera dentro de transação
- Usa dirty checking do JPA
- Preparada para uso de controle transacional e rollback

🔹 Backend (Spring Boot)
- Atua como fachada REST
- Implementa CRUD completo de Benefícios
- Integra com o EJB para operações de transferência
- Centraliza tratamento de erros com @RestControllerAdvice
- Exposição de API documentada via Swagger/OpenAPI

🔹 Frontend (Angular)
- Interface SPA consumindo o backend
- Telas de:
  * Listagem de benefícios
  * Cadastro / edição
  * Transferência entre benefícios
- Utiliza:
  * Angular Material
  * Reactive Forms
  * SnackBar para mensagens
  * Confirm Dialog para exclusão 
  * Paginação e layout responsivo

🐞 Correção do Bug no EJB
Problema original
- Transferência não validava saldo 
- Não havia controle transacional 
- Risco de inconsistência de dados

Solução aplicada
- Validação de regras de negócio no EJB 
- Execução dentro de transação 
- Uso de EntityManager.find com dirty checking 
- Exceções lançadas corretamente para rollback 
- Preparação para locking/controle concorrente

🧪 Estratégia de Testes
Foram implementados testes em múltiplos níveis, garantindo confiabilidade:

✔ Testes de Controller
- @WebMvcTest 
- Mock do service 
- Validação de:
  * status HTTP 
  * payload 
  * cenários de erro

✔ Testes Unitários de Service
- Mock de Repository e EJB
- Validação de regras:
  * criar 
  * atualizar 
  * excluir 
  * buscar 
  * transferir

✔ Testes de Integração

- @DataJpaTest
- Banco H2 em memória
- Execução real de:
  * persistência 
  * transferência 
  * validação de saldo após operação

📘 Documentação da API (Swagger)
A API REST está documentada via Swagger/OpenAPI.

▶ Acesso
Após subir o backend:

http://localhost:8081/swagger-ui.html

ou

http://localhost:8081/swagger-ui/index.html

Endpoints disponíveis
- GET /api/v1/beneficios 
- GET /api/v1/beneficios/{id} 
- POST /api/v1/beneficios 
- PUT /api/v1/beneficios/{id} 
- DELETE /api/v1/beneficios/{id} 
- POST /api/v1/beneficios/transferir

▶ Como Rodar a Aplicação
🗄️ Banco de Dados
Execute os scripts:

db/schema.sql
db/seed.sql

Alternativamente, nos testes e ambiente local é utilizado H2 em memória

🚀 Backend
cd backend-module
mvn clean install
mvn spring-boot:run

Backend disponível em:

http://localhost:8081

🌐 Frontend
cd frontend
npm install
ng serve

Frontend disponível em:

http://localhost:4200

🔗 O frontend já está configurado para consumir o backend local.

🧰 Tecnologias Utilizadas
# Backend
- Java 17 
- Spring Boot 
- Spring Data JPA 
- Hibernate 
- H2 / SQL 
- Swagger / OpenAPI 
- JUnit 5 / Mockito

# Frontend
- Angular 
- Angular Material 
- Reactive Forms 
- RxJS 
- TypeScript