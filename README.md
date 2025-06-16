# Academia

Gestão para Academia com cadastro e manutenção de **alunos** e **treinos**.

---

## Descrição

Este projeto em console oferece as seguintes funcionalidades:

* **Cadastro** de alunos (nome, CPF, data de nascimento, e-mail, telefone).
* **Consulta dinâmica**: filtro em tempo real conforme digitação.
* **Registro**, **consulta**, **edição** e **exclusão** de alunos.
* **Registro**, **consulta**, **edição** e **exclusão** de treinos associados a cada aluno.

A comunicação com o banco de dados MySQL é feita através de JDBC.

---

## Tecnologias

* Java 11+
* Maven (gestão de dependências e build)
* MySQL (banco de dados)
* JDBC (`java.sql`)

---

## Pré-requisitos

* JDK 11 ou superior instalado
* Maven instalado
* Servidor MySQL/MariaDB configurado

---

## Instalação e execução

1. **Clone** o repositório:

   git clone https://github.com/enijds/Academia.git
   cd Academia


2. **Configure** o banco de dados:

   * Crie o schema `academia` no MySQL/MariaDB
   * Execute os scripts na pasta SQL necessários para constuir o banco

	
   * Usuario do script: "usuario"
   * senha do script: "senha"


3. **Execute** o JAR:

   * java -jar target/Academia-1.0.jar


---

## Estrutura do projeto

```
src/
├───SQL
├─ main/
│  ├─ java/com/programacaojava/academia/
│  │  ├─ ui/           	-> interface de console (menus, telas)
│  │  ├─ dao/          	-> acesso a dados (conexões, CRUD)
│  │  └─ util/         	-> formatadores (datas, CPF, telefone)
└───target	 	-> arquivo jar formatado pelo maven
```

---

## Uso básico

1. **Login**: tela de autenticação
2. **Menu principal**: escolha entre gerenciar alunos ou treinos
3. **Gerenciar alunos**:

   * Cadastrar novo
   * Consultar (filtro incremental)
   * Alterar ou excluir registro
4. **Gerenciar treinos**:

   * Associar treino a aluno
   * Consultar, alterar ou excluir treino

---

## Autor

Enio Sousa. — [enijds/Academia](https://github.com/enijds/Academia)

