# HubSpotApplication Integration API

Este projeto é uma API REST construída em Java utilizando Spring Boot para integração com o HubSpot CRM.  
O sistema implementa o fluxo OAuth 2.0 para autenticação, criação de contatos e recebimento de eventos via Webhooks.

---

## 🚀 Tecnologias Utilizadas

- Java 17
- Spring Boot 3.4.5
- Spring Web
- Springdoc OpenAPI (Swagger para documentação)
- OAuth 2.0
- Gradle para build
- Docker & Docker Compose

---

## 📦 Funcionalidades

- Gerar URL de autorização para autenticação OAuth 2.0 no HubSpot
- Trocar `authorization_code` por `access_token`
- Criar contatos no HubSpot CRM
- Receber notificações de Webhook para eventos de criação de contatos
- Interface Swagger para documentação e testes dos endpoints

---

## 🛠️ Tecnologias Utilizadas

- **Spring Boot**: Framework Java para o desenvolvimento de aplicações web.
- **Gradle**: Gerenciador de dependências e build para projetos Java.
- **Docker**: Containerização da aplicação para facilitar o deploy e a execução em qualquer ambiente.

---

## 🏗️ Arquitetura e padrões de projeto

- SOLID
- DTO 
- Exception Handling

---

## ⚙️ Pré-requisitos

Antes de começar, você precisará ter os seguintes itens instalados na sua máquina e conta a ser criada:

- **Java 17**.
- **Docker** e **Docker Compose**.
- **Gradle** (opcional, caso queira rodar o projeto fora do Docker).
- **Conta de desenvolvedor no HubSpot** com credenciais OAuth
---

## 📄 Como Executar o Projeto

### 1. Clone o Repositório

```bash
git clone https://github.com/Georgeobeid/hubspot.git
cd hubspot
```

### 2. Configuração das Variáveis de Ambiente

| Variável                | Descrição                                                                                   |
|-------------------------|---------------------------------------------------------------------------------------------|
| `HUBSPOT_CLIENT_ID`     | ID da sua aplicação no HubSpot                                                              |
| `HUBSPOT_CLIENT_SECRET` | Chave secreta da sua aplicação HubSpot                                                      |
| `HUBSPOT_REDIRECT_URI`  | URL de redirecionamento configurada no HubSpot (deve bater com o callback da sua aplicação) |

⚠️ **Aviso Importante de Segurança**:  
Essas credenciais são sensíveis. Se recomenda usar um arquivo `.env` ao invés de hardcodar nos arquivos de configuração.


### 3. Construa e Execute os Contêineres

Use o Docker Compose para construir e executar os contêineres da aplicação:

```bash
docker-compose up --build
```

### 3. Acesse a Aplicação

A aplicação estará disponível [aqui](http://localhost:8080).

---

---

## 📑 Documentação da API - Swagger OpenAPI

Este projeto utiliza **Swagger OpenAPI** para documentar os endpoints da API.  
Com o Swagger UI, você pode visualizar, testar e entender melhor todas as requisições disponíveis.

### 🔗 Acesse a documentação:
- **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)
- **Especificação OpenAPI:** [http://localhost:8080/v3/api-docs](http://localhost:8080/v3/api-docs)

### 🛠 Como o Swagger foi implementado?
O Swagger foi integrado ao projeto usando a biblioteca **Springdoc OpenAPI**.  
As dependências foram adicionadas ao `build.gradle`, e as anotações como `@Operation` e `@ApiResponse` foram usadas nos endpoints.

---

## 🗂️ Estrutura do Projeto

### src/main/java/com/george/hubspot

- **controller**: Contém os endpoints da API (`ContactController`).
- **DTO**: Classes de data transfer object como `ContactDTO` e `HubspotWebhookEventDTO`.
- **service**: Lógica de negócio da aplicação (`HubspotService`).
- **exception**: Tratamento de exceções personalizadas, exemplos (`OAuthAuthenticationException`,`TooManyRequestsException`).

### src/main/resources

- Arquivos de configuração e propriedades do Spring Boot.

### docker-compose.yml

- Configuração do Docker Compose para subir a aplicação.

---

## 🚀 Possíveis Melhorias
- Implementar MapStruct (caso necessário)
- Implementar novas exceções caso necessário
- Implementar Log4j parae remover os System.out.println
- Manipular o Token de forma mais segura e mais eficiente
- Implementar testes unitários, testes de integração
- Completar a implementação do Swagger