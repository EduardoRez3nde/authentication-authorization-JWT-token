
# Autenticação e Autorização com JWT

Este projeto demonstra a implementação de autenticação e autorização utilizando JSON Web Tokens (JWT) em uma aplicação Java com Spring Boot. Utilizando a biblioteca JOSE4J.

## Funcionalidades

- **Registro de Usuário**: Permite que novos usuários se registrem na aplicação.
- **Login de Usuário**: Usuários registrados podem fazer login para obter um token JWT.
- **Acesso a Recursos Protegidos**: Utiliza o token JWT para acessar endpoints protegidos da aplicação.

## Tecnologias Utilizadas

- **Java**: Linguagem principal utilizada no desenvolvimento.
- **Spring Boot**: Framework para facilitar a configuração e o desenvolvimento da aplicação.
- **Spring Security**: Módulo do Spring utilizado para configurar a segurança da aplicação.
- **JWT (JSON Web Tokens)**: Para autenticação e autorização baseada em tokens.
- **RSA**: Algoritmo de criptografia assimétrica utilizado para assinar e verificar os tokens JWT.

## Estrutura do Projeto

O projeto está organizado da seguinte forma:

```

authentication-authorization-JWT-token/
├── keys/
│   ├── private.pem
│   └── public.pem
├── src/
│   └── main/
│       └── java/
│           └── com/
│               └── example/
│                   └── auth/
│                       ├── controller/
│                       ├── model/
│                       ├── security/
│                       │   └── RsaKeyGenerator.java
│                       └── service/
├── build.gradle
├── docker-compose.yml
└── README.md
```

## Classe RsaKeyGenerator

A classe `RsaKeyGenerator` é responsável por gerar e gerenciar o par de chaves RSA utilizadas na assinatura e verificação dos tokens JWT.

### Funcionalidades

- **Geração de Chaves**: Gera um par de chaves RSA (privada e pública) com um tamanho de 2048 bits.
- **Armazenamento**: Salva as chaves geradas nos arquivos `private.pem` e `public.pem` dentro do diretório `keys/`.
- **Leitura de Chaves**: Fornece métodos para ler as chaves a partir dos arquivos PEM, permitindo sua utilização na assinatura e verificação dos tokens.


É importante garantir que os arquivos `private.pem` e `public.pem` estejam presentes no diretório `keys/` antes de iniciar a aplicação. Caso contrário, a classe `RsaKeyGenerator` pode ser utilizada para gerar as chaves necessárias.

**Obs:** Somente execute a aplicação e as chaves serão criadas automaticamente.

## Executando a Aplicação

1. **Clone o repositório**:

   ```bash
   git clone https://github.com/EduardoRez3nde/authentication-authorization-JWT-token.git
   ```

2. **Navegue até o diretório do projeto**:

   ```bash
   cd authentication-authorization-JWT-token
   ```
   
3. **Compile e execute a aplicação**:

   ```bash
   ./gradlew bootRun
   ```

4. **Acesse a aplicação**:

   A aplicação estará disponível em `http://localhost:8080`.

## Endpoints Disponíveis

- `POST /register`: Registro de um novo usuário.
- `POST /login`: Autenticação de usuário e geração do token JWT.
- `GET /protected`: Endpoint protegido que requer um token JWT válido.

## Considerações Finais

Este projeto serve como uma base para aplicações que necessitam de autenticação e autorização utilizando JWT. A utilização de chaves RSA proporciona uma camada adicional de segurança, garantindo a integridade e autenticidade dos tokens gerados.

Para ambientes de produção, é recomendável proteger os arquivos de chave com permissões adequadas e considerar o uso de um gerenciador de segredos para armazená-los de forma segura.

---
