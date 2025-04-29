# HubSpot Integration API

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
- MVC

---

## ⚙️ Pré-requisitos

Antes de começar, você precisará ter os seguintes itens instalados na sua máquina e conta a ser criada:

- **Java 17**.
- **Docker** e **Docker Compose**.
- **Gradle** (opcional, caso queira rodar o projeto fora do Docker).
- **Postman** ferramenta usada para testar APIs REST
- **Ngrok**
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

⚠️ **Aviso Importante de Segurança**:  
Essas credenciais são sensíveis. Se recomenda usar um arquivo `.env` ao invés de hardcodar nos arquivos de configuração.


### 3. Construa e Execute os Contêineres

Use o Docker Compose para construir e executar os contêineres da aplicação:

```bash
docker-compose up --build
```
### 4. Instale o Ngrok e configure

Este passo é necessário para expor localmente sua aplicação backend na internet, pois os webhooks da conta de desenvolvedor do HubSpot enviam requisições via HTTPS, o que não é possível se comunicar diretamente pelo localhost.

Sem essa exposição via HTTPS, os webhooks do HubSpot não conseguirão se comunicar com sua aplicação local.

Com o Ngrok em execução, você pode copiar a URL HTTPS gerada e inseri-la na configuração dos webhooks da sua conta HubSpot.


### 5. Acesse a Aplicação e rode o fluxo completo

Para iniciar o fluxo de autenticação OAuth com o HubSpot, siga os passos abaixo:

- Gerar a URL de Autorização:
Acesse o seguinte endpoint em seu navegador para gerar a URL de autorização:
http://localhost:8080/oauth/authorize

- Autorizar a Aplicação:
Ao acessar o endpoint acima, uma nova URL será exibida. Essa URL deve ser copiada e acessada no navegador. Ela redirecionará para o HubSpot, onde você deverá conceder permissão à aplicação.

- Receber o Código de Autorização:
Após a autorização, o HubSpot redirecionará automaticamente para um segundo endpoint da aplicação, que é responsável por receber o código de autorização e trocá-lo por um access token. O token será exibido no navegador e armazenado temporariamente em uma variável da aplicação.

- Consumir os Endpoints Protegidos:
Com o access token obtido, você já pode fazer requisições autenticadas. Por exemplo, é possível criar um novo contato usando o Postman.
Consulte a documentação Swagger do projeto (próxima etapa) para visualizar todos os endpoints disponíveis e os respectivos DTOs.

⚠️ Atenção: O access token é armazenado apenas em memória. Ou seja, ao reiniciar a aplicação, o token será perdido e o processo de autenticação deverá ser reiniciado desde o início.


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
- Manipular o Token de forma mais segura e mais eficiente (Guardar num banco de dados ou Redis)
- Implementar testes unitários e testes de integração
- Completar a implementação do Swagger

Desenvolvido por [George Obeid](https://github.com/Georgeobeid) 💻