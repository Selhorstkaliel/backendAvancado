# 🔍 Atividade: O Diagnóstico do Monolito "Frankenstein"

Bem-vindo ao Monolito Frankenstein! Este projeto foi construído com diversas falhas conceituais propositais. Seu objetivo é identificar, diagnosticar e refatorar o código seguindo as melhores práticas de mercado: **SOLID**, **Arquitetura Hexagonal** e **TDD**.

## 🛠️ O Projeto
O sistema é um gerenciador de Autores e Livros. Atualmente, ele possui um Controller gordo, uma segurança inexistente e um banco de dados que chora por performance.

---

## 🚩 Task 1: O Mistério do Banco de Dados (Nível Spring Data)
**O Erro:** A relação `@OneToMany` na entidade `Author` está configurada de forma ineficiente.
**O Desafio:** 
1. Popule o banco com cerca de 50 autores, cada um com alguns livros.
2. Acesse o endpoint `GET /authors`.
3. Olhe o log do console. **Por que o Spring está fazendo 50 selects para trazer 50 itens?**
4. **Resolução:** Refatore a consulta para utilizar `JOIN FETCH` ou mude a estratégia de carga para evitar o problema do **N+1**.

---

## 🚩 Task 2: A Segurança de Papel (Nível Spring Security)
**O Erro:** O `SecurityConfig` está configurado com `permitAll()` em tudo e utiliza credenciais hardcoded em memória.
**O Desafio:**
1. Restrinja o endpoint `DELETE /authors/{id}` para que APENAS usuários com a Role `ADMIN` possam acessá-lo.
2. Implemente uma autenticação real usando **JWT (JSON Web Token)**.
3. Remova as senhas do código.

---

## 🚩 Task 3: O Service "Gordo" (Nível Clean Code/Java)
**O Erro:** Toda a lógica de validação de CPF, cálculo de imposto e conversão de dados está socada dentro do `AuthorController`.
**O Desafio:**
1. Aplique o Princípio da Responsabilidade Única (SRP).
2. Mova a lógica para a camada de **Service**.
3. Utilize **Records** (Java modern) para criar **DTOs** e evitar expor a entidade JPA diretamente na API.
4. Implemente **Arquitetura Hexagonal**: Separe o domínio (lógica) de adaptadores (controller/repository).

---

## 🧪 Desafio Global: TDD
Para cada refatoração, **crie testes unitários ou de integração primeiro**. Prove que o erro existe com um teste falhando e depois prove que ele foi resolvido com o teste passando.

---

## Como rodar?
### Local (sem Docker)
1. Defina variáveis de ambiente:
	 - `APP_JWT_SECRET` (min. 32 chars)
	 - `APP_ADMIN_USERNAME`
	 - `APP_ADMIN_PASSWORD_BCRYPT` (ex.: `{noop}admin123`)
	 - `APP_USER_USERNAME`
	 - `APP_USER_PASSWORD_BCRYPT` (ex.: `{noop}user123`)
2. Rode: `mvn spring-boot:run`
3. H2 Console: `http://localhost:8080/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`)

### Docker (backend + frontend)
1. Rode: `docker compose up --build`
2. Backend: `http://localhost:8080`
3. Frontend: `http://localhost:8081`

---

## O que foi refatorado

### ✅ Task 1 - N+1
- A associação `Author -> Book` foi alterada para `LAZY`.
- Foi criada a query com `JOIN FETCH`:
	- `AuthorRepository.findAllWithBooks()`
- Endpoint de listagem agora usa o fluxo que evita N+1.
- Seed automático de 50 autores (3 livros cada) no startup (`DataSeeder`).

### ✅ Task 2 - Segurança JWT + RBAC
- `DELETE /authors/{id}` e `DELETE /api/v1/authors/{id}` exigem role `ADMIN`.
- Login JWT implementado em `POST /auth/login`.
- Segurança `stateless` com filtro JWT.
- Senhas removidas do código Java e externalizadas para variáveis de ambiente.

Exemplo de login:

```bash
curl -X POST http://localhost:8080/auth/login \
	-H "Content-Type: application/json" \
	-d '{"username":"admin","password":"admin123"}'
```

### ✅ Task 3 - SRP + Hexagonal + DTO com Records
- Controller virou adaptador fino (sem regra de negócio).
- Regras de domínio separadas:
	- `CpfValidator`
	- `TaxCalculator`
- Casos de uso e portas:
	- `AuthorUseCase`
	- `AuthorPersistencePort`
- Adaptadores:
	- `AuthorController` (entrada)
	- `AuthorPersistenceAdapter` (saída)
- API usa records DTOs e não expõe entidade JPA diretamente.

---

## Testes (TDD)
- `CpfValidatorTest`
- `TaxCalculatorTest`
- `AuthorApplicationServiceTest`
- `AuthorRepositoryNPlusOneTest`
	- prova comportamento N+1 no `findAll` + lazy access
	- prova correção com `findAllWithBooks` em 1 query
- `SecurityIntegrationTest`
	- 401 sem token
	- 403 com token de `USER`
	- 204 com token de `ADMIN`
