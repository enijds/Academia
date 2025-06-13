/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.programacaojava.academia.core;

import com.programacaojava.academia.Academia;

/**
 *
 * @author enio
 */
public class CadastraAluno extends Academia {
/*
    ppublic void form(){
        // Método para exibir o formulário de cadastro de aluno
        // Aqui você pode implementar a lógica para exibir o formulário, como solicitar os dados do aluno
        System.out.println("Formulário de Cadastro de Aluno");
        System.out.println("Por favor, insira os dados do aluno para continuar.");
        System.out.print("Nome: "); // Solicita o nome do aluno
        // Aqui você pode ler o nome do aluno usando scanner ou outro método de entrada 
        String nome = scanner.nextLine();
       
        // Solicita o CPF do aluno
        System.out.print("CPF(XXX.XXX.XXX-XX): "); // Solicita o CPF do aluno
        String CPF = scanner.nextLine();

        // Solicita a data de nascimento do aluno
        System.out.print("Data de Nascimento(AAAA-MM-DD): "); // Solicita a data de nascimento do aluno

        LocalDate dataNascimento = LocalDate.parse(scanner.nextLine());
        System.out.print("Telefone(XX) XXXXX-XXXX): "); // Solicita o telefone do aluno
        String telefone = scanner.nextLine();

        // Solicita o email do aluno
        System.out.print("Email: "); // Solicita o email do aluno
        String email = scanner.nextLine();


    } // public void form() {
    
    public void InserirAluno(String nome,String CPF, LocalDate dataNascimento, String telefone, String email) throws SQLException {
   
        // Conecta ao banco de dados
        

        // Objetos Statement e ResultSet são usados para executar consultas SQL
        // e processar os resultados retornados pelo banco de dados
        Statement statement = null;
        ResultSet resultSet = null;

        // Instancia a classe conectaDBMariaSQL no objeto conecta
        conectaDBMariaSQL conecta = new conectaDBMariaSQL();

        // Chama o método conecta() para estabelecer a conexão com o banco de dados
        // e armazena a conexão na variável connection
        Connection connection = conecta.conecta();

        if (connection == null) {
            System.err.println("Falha na conexão com o banco de dados.");
            return; // Encerra o método se a conexão falhar
        }

        // Cria a instrução SQL para inserir os dados
        String stringSQL = "INSERT INTO tblpessoa (nome, cpf, data_nascimento, telefone, email) VALUES ('"
                + nome + "', '" + CPF + "', '" + dataNascimento + "', '" + telefone + "', '" + email + "')";


        // Cria um statement para executar a consulta
        statement = connection.createStatement();
        statement.executeUpdate(stringSQL);

        // Testa sucesso da inserção
        // O método executeUpdate retorna o número de linhas afetadas pela consulta
        resultSet = statement.executeQuery("SELECT * FROM tblpessoa WHERE cpf = '" + CPF + "'");

        // Verifica se a consulta retornou algum resultado
        if (resultSet.next()) {
            // Se a consulta retornou resultados, significa que os dados foram inseridos com sucesso
            System.out.println("Dados inseridos com sucesso!");
            // fechando o resultSet e o statement
            resultSet.close();
            statement.close();
        }
        else {
            System.out.println("Erro ao inserir dados.");
        } // if (resultSet.next()) {
        
    }


    
    public static void main(String[] args) throws SQLException {

        // Exemplo de uso do método InserirAluno
        String nome = "João da Silva";
        String CPF = "1234567890";
        LocalDate dataNascimento = LocalDate.of(1990, 1, 1);
        String telefone = "(11) 98765-4321";
        String email = "aBwXv@example.com";

        InserirAluno(nome, CPF, dataNascimento, telefone, email);

        consultaDB.main(null); // Chama o método main da classe consultaDB para exibir os dados inseridos
        
    }
    */
    
}
