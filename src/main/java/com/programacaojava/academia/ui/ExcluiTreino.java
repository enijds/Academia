package com.programacaojava.academia.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;


/**
 * Classe responsável por excluir registros da tabela "treinos".
 */
public class ExcluiTreino {

    // Variável de conexão com o banco
    private Connection connection = null;

    /**
     * Método principal que solicita ao usuário o ID do treino a ser excluído
     * e dispara a operação de remoção.
     */
    public void executarExclusao() {
        // Leitura de console
        Scanner scanner = new Scanner(System.in);

        // Limpa a tela para melhor visualização
        TerminalTexto terminal = new TerminalTexto();
        terminal.LimpaConsole();

        // Pede ao usuário o ID do treino
        System.out.print("Digite o ID do treino que deseja excluir: ");
        int idTreino = scanner.nextInt();

        // Executa a exclusão no banco
        excluirTreinoPorId(idTreino);
    }

    /**
     * Conecta ao banco e executa o DELETE na tabela "treinos" para o ID informado.
     *
     * @param id o identificador do treino a remover
     */
    public void excluirTreinoPorId(int id) {
        // Abre conexão via TreinoDao
        connection = com.programacaojava.academia.dao.UsuarioDao.conecta();

        // SQL de remoção
        String sql = "DELETE FROM treinos WHERE id = ?";

        try (
            // Prepara o statement
            PreparedStatement pst = connection.prepareStatement(sql)
        ) {
            // Define o parâmetro "?"
            pst.setInt(1, id);

            // Executa o DELETE
            int linhasAfetadas = pst.executeUpdate();

            // Informa o resultado
            if (linhasAfetadas > 0) {
                System.out.println("Treino excluído com sucesso.");
            } else {
                System.out.println("Nenhum treino encontrado com esse ID.");
            }

        } catch (SQLException e) {
            // Trata erro de SQL
            System.err.println("Erro ao excluir treino: " + e.getMessage());
        } finally {
            // Opcional: fechar conexão
            try {
                if (connection != null && !connection.isClosed()) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.err.println("Erro ao fechar conexão: " + ex.getMessage());
            }
        }
    }

    // Método main para teste rápido da classe
    public static void main(String[] args) {
        ExcluiTreino exclui = new ExcluiTreino();
        exclui.executarExclusao();
    }
}
