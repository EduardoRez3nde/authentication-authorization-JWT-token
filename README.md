# Autenticação e Autorização com JWT

Este projeto demonstra a implementação de autenticação e autorização utilizando JSON Web Tokens (JWT) em uma aplicação Java com Spring Boot.

## Funcionalidades

- **Registro de Usuário**: Permite que novos usuários se registrem na aplicação.
- **Login de Usuário**: Usuários registrados podem fazer login para obter um token JWT.
- **Acesso a Recursos Protegidos**: Utiliza o token JWT para acessar endpoints protegidos da aplicação.

## Tecnologias Utilizadas

- **Java**: Linguagem principal utilizada no desenvolvimento.
- **Spring Boot**: Framework para facilitar a configuração e o desenvolvimento da aplicação.
- **Spring Security**: Módulo do Spring utilizado para configurar a segurança da aplicação.
- **JWT (JSON Web Token)- (JOSE4J)**: Utilizado para a autenticação e autorização de usuários.

## Estrutura do Projeto

O projeto segue a estrutura padrão do Spring Boot, com diretórios para controladores, serviços e repositórios. A configuração de segurança está localizada no pacote correspondente, onde são definidos os filtros e as regras de autorização.

## Configuração e Execução

1. **Clonar o Repositório**:

   ```bash
   git clone https://github.com/EduardoRez3nde/authentication-authorization-JWT-token.git
   ```


2. **Navegar para o Diretório do Projeto**:

   ```bash
   cd authentication-authorization-JWT-token
   ```


3. **Construir o Projeto**:

   ```bash
   ./gradlew build
   ```


4. **Executar a Aplicação**:

   ```bash
   ./gradlew bootRun
   ```


A aplicação estará disponível em `http://localhost:8080`.

## Endpoints Principais

- **POST /auth/register**: Registra um novo usuário.
- **POST /auth/login**: Autentica um usuário e retorna um token JWT.
- **GET /api/protected**: Exemplo de endpoint protegido que requer um token JWT válido.
