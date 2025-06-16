package com.programacaojava.academia.ui;

import java.io.Console;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.programacaojava.academia.util.DateFormatter;
import com.programacaojava.academia.util.DateFormatterForConsole;



public class AlteraTreino {

    // Atributos atuais do treino
    private int    id;
    private int    alunoId;
    private String tipoTreino;
    private String descricao;
    private int    duracaoMinutos;
    private String dataInicio;

    // Novos valores a serem gravados
    private int    novoAlunoId;
    private String novoTipoTreino;
    private String novaDescricao;
    private int    novaDuracaoMinutos;
    private String novaDataInicio;

    // Para leitura de um único caractere no console
    private int  ichar;
    private char cchar;

    // Espaço para reposicionar o cursor após leitura de um caractere
    private final int espacos = 80;

    /**
     * Atualiza os dados do treino com o ID informado.
     *
     * @param idTreino o identificador do treino a ser alterado
     * @throws Exception em caso de erro de I/O ou SQL
     */
    public void atualizaTreino(int idTreino) throws Exception {
        // limpa o terminal para exibir só a parte de edição
        TerminalTexto terminal = new TerminalTexto();
        terminal.LimpaConsole();

        // console para leitura de um caractere sem precisar do Enter
        Console console = System.console();
        Reader reader = (console != null) ? console.reader() : null;

        // Scanner para leitura de linhas e números
        Scanner scanner = new Scanner(System.in);

        // Formatadores de data
        DateFormatter dateFormatter = new DateFormatter();
        DateFormatterForConsole dateFormatterForConsole = new DateFormatterForConsole();

        // Variáveis de JDBC
        Connection        connection     = null;
        PreparedStatement selectStmt     = null;
        ResultSet         resultSet      = null;
        PreparedStatement updateStmt     = null;

        try {
            // Estabelece a conexão com o banco
                connection = com.programacaojava.academia.dao.UsuarioDao.conecta();

            // Traz os dados atuais do treino
            String selectSQL = 
                "SELECT id, aluno_id, tipo_treino, descricao, duracao_minutos, data_inicio " +
                "FROM treinos WHERE id = ?";
            selectStmt = connection.prepareStatement(selectSQL);
            selectStmt.setInt(1, idTreino);
            resultSet = selectStmt.executeQuery();

            if (!resultSet.next()) {
                System.out.println("Treino com ID " + idTreino + " não encontrado.");
                return;
            }

            // 3) Carrega valores atuais em atributos
            id              = resultSet.getInt   ("id");
            alunoId         = resultSet.getInt   ("aluno_id");
            tipoTreino      = resultSet.getString("tipo_treino");
            descricao       = resultSet.getString("descricao");
            duracaoMinutos  = resultSet.getInt   ("duracao_minutos");
            dataInicio      = resultSet.getString("data_inicio");  // formato ISO do banco

            // Converte data para exibição "dd/MM/yyyy"
            String dataExibicao = (dataInicio != null)
                ? dateFormatter.formatDate(dataInicio)
                : "00/00/0000";

            // 4) Pergunta e lê cada campo, mantendo ou alterando

            // — aluno_id —
            System.out.print("\nAluno ID: " + alunoId + " (manter? N|n para alterar) ");
            ichar = reader.read();
            cchar = (char) ichar;
            if (cchar == 'N' || cchar == 'n') {
                System.out.print("\rAluno ID:" + " ".repeat(espacos) + "Antigo: " + alunoId);
                System.out.print("\rAluno ID: ");
                novoAlunoId = scanner.nextInt();
                scanner.nextLine();
            } else {
                novoAlunoId = alunoId;
                System.out.println();
            }

            // — tipo_treino —
            System.out.print("Tipo Treino: " + tipoTreino + " (manter? N|n para alterar) ");
            ichar = reader.read();
            cchar = (char) ichar;
            if (cchar == 'N' || cchar == 'n') {
                System.out.print("\rTipo Treino:" + " ".repeat(espacos) + "Antigo: " + tipoTreino);
                System.out.print("\rTipo Treino: ");
                novoTipoTreino = scanner.nextLine();
            } else {
                novoTipoTreino = tipoTreino;
                System.out.println();
            }

            // — descricao —
            System.out.print("Descrição: " + descricao + " (manter? N|n para alterar) ");
            ichar = reader.read();
            cchar = (char) ichar;
            if (cchar == 'N' || cchar == 'n') {
                System.out.print("\rDescrição:" + " ".repeat(espacos) + "Antigo: " + descricao);
                System.out.print("\rDescrição: ");
                novaDescricao = scanner.nextLine();
            } else {
                novaDescricao = descricao;
                System.out.println();
            }

            // — duracao_minutos —
            System.out.print("Duração (min): " + duracaoMinutos + " (manter? N|n para alterar) ");
            ichar = reader.read();
            cchar = (char) ichar;
            if (cchar == 'N' || cchar == 'n') {
                System.out.print("\rDuração (min):" + " ".repeat(espacos) + "Antigo: " + duracaoMinutos);
                System.out.print("\rDuração (min): ");
                novaDuracaoMinutos = scanner.nextInt();
                scanner.nextLine();
            } else {
                novaDuracaoMinutos = duracaoMinutos;
                System.out.println();
            }

            // — data_inicio —
            System.out.print("Data Início: " + dataExibicao + " (manter? N|n para alterar) ");
            ichar = reader.read();
            cchar = (char) ichar;
            if (cchar == 'N' || cchar == 'n') {
                System.out.print("\rData Início:" + " ".repeat(espacos) + "Antigo: " + dataExibicao);
                System.out.print("\rData Início: ");
                novaDataInicio = dateFormatter.formatDateForDB(
                            dateFormatterForConsole.lerData()
                            );
            } else {
                novaDataInicio = dateFormatter.formatDateForDB(dataInicio);
                System.out.println();
            }

            // 5) Prepara e executa o UPDATE
            String updateSQL =
                "UPDATE treinos SET " +
                "aluno_id = ?, tipo_treino = ?, descricao = ?, " +
                "duracao_minutos = ?, data_inicio = ? " +
                "WHERE id = ?";
            updateStmt = connection.prepareStatement(updateSQL);
            updateStmt.setInt   (1, novoAlunoId);
            updateStmt.setString(2, novoTipoTreino);
            updateStmt.setString(3, novaDescricao);
            updateStmt.setInt   (4, novaDuracaoMinutos);
            updateStmt.setString(5, novaDataInicio);
            updateStmt.setInt   (6, id);

            int rows = updateStmt.executeUpdate();
            if (rows > 0) {
                System.out.println("\nTreino atualizado com sucesso!");
            } else {
                System.out.println("\nNenhuma alteração realizada.");
            }

        } finally {
            // 6) Fecha todos os recursos em ordem inversa de abertura
            if (resultSet         != null) resultSet.close();
            if (selectStmt != null) selectStmt.close();
            if (updateStmt != null) updateStmt.close();
            if (connection != null) connection.close();
        }
    }
    

} // public class AlteraTreino
