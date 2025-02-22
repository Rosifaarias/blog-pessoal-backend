# Projeto Blog Pessoal *Backend*

## 🚀 Descrição
O **Blog Pessoal** é uma API desenvolvida em Java com Spring Boot para gerenciamento de postagens e usuários em um blog. O sistema permite a criação, leitura, atualização e remoção (**CRUD**) de postagens, categorias e usuários, além de contar com autenticação e autorização via JWT.

## 🛠️ Tecnologias Utilizadas
- **Java 17**
- **Spring Boot**
- **Spring Security**
- **JWT (JSON Web Token)**
- **JPA / Hibernate**
- **MySQL**
- **Docker**
- **Swagger (Springdoc OpenAPI)**

## 🏗️ Arquitetura do Projeto
A arquitetura segue o padrão **MVC (Model-View-Controller)**, separando as responsabilidades em diferentes camadas:

### 📌 1. Model
Define as entidades principais do sistema:
- `Usuario`: Representa os usuários do sistema.
- `Postagem`: Representa as postagens do blog.
- `Categoria`: Define categorias para classificar as postagens.

### 📌 2. Repository
Interfaces que fazem a comunicação com o banco de dados utilizando **Spring Data JPA**.

### 📌 3. Service
Camada de serviços contendo as regras de negócio e manipulação dos dados.

### 📌 4. Controller
Camada responsável por expor as **APIs REST**, recebendo requisições HTTP e retornando respostas.

### 🔐 5. Security
Implementação de autenticação e autorização com **Spring Security e JWT**.

### ⚙️ 6. Configuration
Classes de configuração para segurança, CORS e documentação da API.

## ▶️ Como Executar o Projeto
### 🎯 Requisitos:
- Java 17+
- Maven
- Docker (opcional)

### 📌 Passos:
1. Clone o repositório:
   ```sh
   git clone https://github.com/seu-usuario/blog-pessoal.git
   cd blog-pessoal
   ```
2. Configure o banco de dados no arquivo `application.properties`.
3. Execute o projeto com Maven:
   ```sh
   mvn spring-boot:run
   ```
4. A API estará disponível em [`http://localhost:8080`](http://localhost:8080).

## 📄 Documentação da API
Acesse a documentação interativa via Swagger:
🔗 [`http://localhost:8080/swagger-ui.html`](http://localhost:8080/swagger-ui.html)

## 👤 Autor
Desenvolvido por **[Rosilene Farias]**. Sinta-se à vontade para contribuir! 😊🚀

