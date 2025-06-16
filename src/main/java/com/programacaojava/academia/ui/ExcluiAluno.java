package com.programacaojava.academia.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class ExcluiAluno {

    // Declaração de variáveis para conexão, statement e resultSet
    // Essas variáveis serão usadas para conectar ao banco de dados e executar a consulta
    Connection        connection        = null;
    ResultSet         resultSet         = null;
   // PreparedStatement preparedStatement = null;
    //PreparedStatement updateStatment    = null;


    // Metodo principal da classe que sera chamado para realizar a exclusão
    public void executarExclusao() {
        // Cria um objeto Scanner pra ler o que o usuario digitar no terminal
        Scanner scanner = new Scanner(System.in);

        // Cria um objeto pra limpar o console (visual mais limpo)
        TerminalTexto terminal = new TerminalTexto();
        terminal.LimpaConsole(); // Chama o método pra limpar o terminal

        // Exibe uma mensagem pedindo o ID do aluno que sera excluido
        System.out.print("Digite o ID do aluno que deseja excluir: ");

        // Lê o valor digitado pelo usuario e guarda na variavel idAluno
        int idAluno = scanner.nextInt();

        // Chama o método que realmente exclui do banco
        excluirAlunoPorId(idAluno); 
    
    }

    // Método que executa a exclusão no banco de dados, recebendo o ID do aluno
    public void excluirAlunoPorId(int id) {

        // Estabelece a conexão com o banco
        connection = com.programacaojava.academia.dao.UsuarioDao.conecta();

        // Comando SQL para deletar o aluno com ID especifico
        String sql = "DELETE FROM alunos WHERE id = ?";

        try (

            // Prepara o comando SQL acima pra ser executado
            PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            // Substitui o "?" da query pelo valor do ID fornecido
            preparedStatement.setInt(1, id);

            // Executa o comando DELETE no banco
            int linhasAfetadas = preparedStatement.executeUpdate();

            // Verifica se algum aluno foi realmente excluído
            if (linhasAfetadas > 0) {
                System.out.println("Aluno excluído com sucesso."); // Sucesso!
            } else {
                System.out.println("Nenhum aluno encontrado com esse ID."); // Nenhum aluno com esse ID
            }

        } catch (SQLException e) {
            // Em caso de erro na comunicação com o banco, exibe a menssagem de erro
            System.err.println("Erro ao excluir aluno: " + e.getMessage());
        }
    }



} // public class ExcluiAluno {
