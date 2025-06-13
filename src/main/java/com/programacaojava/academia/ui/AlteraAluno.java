package com.programacaojava.academia.ui;

import java.sql.Connection;
import java.sql.Date;
//import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
//import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Scanner;

//import com.programacaojava.academia.util.CPFFormatter;
//import com.programacaojava.academia.util.DateFormatter;/
//import com.programacaojava.academia.util.DateFormatterForConsole;
//import com.programacaojava.academia.util.TelefoneFormatter;


public class AlteraAluno {

    // Atributos do aluno
    private int    id              ;
    private String nome            ;
    private String cpf             ;
    private Date   data            ; 
    private String email           ;
    private String telefone        ;

    private SimpleDateFormat data_nascimento = new SimpleDateFormat("dd/MM/yyyy");

    private void atualizaCadastro(int idAluno) throws SQLException {

        Scanner scanner = new Scanner(System.in);
        
        // Declaração de variáveis para conexão, statement e resultSet
        // Essas variáveis serão usadas para conectar ao banco de dados e executar a consulta
        Connection        connection        = null;
        ResultSet         resultSet         = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement updateStmt        = null;
    
        // Exibe uma mensagem informando que os treinos estão sendo listados
        System.out.println("Listando Treinos...");

        // Conecta ao banco de dados usando o método conecta da classe UsuarioDao
        try {

            // Estabelece a conexão com o banco
            connection = com.programacaojava.academia.dao.UsuarioDao.conecta();

            // Consulta o registro pelo ID
            String selectSQL = "SELECT id, nome, cpf, data_nascimento, email, telefone FROM tabela WHERE id = ?";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idAluno);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                id              =resultSet.getInt("id"              );
                nome            =resultSet.getString("nome"         );
                cpf             =resultSet.getString("cpf"          );
                data            =resultSet.getDate("data_nascimento");
                email           =resultSet.getString("email"        );
                telefone        =resultSet.getString("telefone"     );
                
                // Exibe os dados atuais
                // System.out.println("Dados atuais:");
                // System.out.println("ID: " + resultSet.getInt("id"));
                // System.out.println("Nome: " + resultSet.getString("nome"));
                // System.out.println("CPF: " + resultSet.getString("cpf"));
                // System.out.println("Data Nascimento: " + resultSet.getDate("data_nascimento"));
                // System.out.println("Email: " + resultSet.getString("email"));
                // System.out.println("Telefone: " + resultSet.getString("telefone"));

                // Permite edição
                scanner.nextLine(); // Limpa o buffer
                System.out.print("Novo Nome (deixe em branco para manter): ");
                String novoNome     = scanner.nextLine().trim().isEmpty() ? nome            : scanner.nextLine();

                System.out.print("Novo CPF (deixe em branco para manter): ");
                String novoCpf      = scanner.nextLine().trim().isEmpty() ? cpf             : scanner.nextLine();

                System.out.print("Nova Data Nascimento (dd/MM/yyyy, deixe em branco para manter): ");
                String novaData     = scanner.nextLine().trim().isEmpty() ? data_nascimento : scanner.nextLine();

                System.out.print("Novo Email (deixe em branco para manter): ");
                String novoEmail    = scanner.nextLine().trim().isEmpty() ? email           : scanner.nextLine();

                System.out.print("Novo Telefone (deixe em branco para manter): ");
                String novoTelefone = scanner.nextLine().trim().isEmpty() ? telefone        : scanner.nextLine();

                // Atualiza o registro
                String updateSQL = "UPDATE tabela SET nome = ?, cpf = ?, data_nascimento = STR_TO_DATE(?, '%d/%m/%Y'), email = ?, telefone = ? WHERE id = ?";
                updateStmt = connection.prepareStatement(updateSQL);
                updateStmt.setString(1, novoNome);
                updateStmt.setString(2, novoCpf);
                updateStmt.setString(3, novaData);
                updateStmt.setString(4, novoEmail);
                updateStmt.setString(5, novoTelefone);
                updateStmt.setInt(6, id);

                int rowsAffected = updateStmt.executeUpdate();
                if (rowsAffected > 0) {
                                System.out.println("Registro atualizado com sucesso!");
                            } else {
                                System.out.println("Nenhuma alteração realizada.");
                            } // if (rowsAffected > 0) {
                    } else {
                        System.out.println("Registro com ID " + id + " não encontrado.");
                    } // if (resultSet.next()) {

            } finally {
                // Fecha os recursos
                if (resultSet != null) resultSet.close();
                if (preparedStatement != null) preparedStatement.close();
                if (updateStmt != null) updateStmt.close();
                if (connection != null) connection.close();
            } // try


    } // private void atualizaCadastro(int idAluno) {

}
