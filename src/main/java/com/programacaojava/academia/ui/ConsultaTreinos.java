package com.programacaojava.academia.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.programacaojava.academia.util.DateFormatter;

/**
 * Consulta dinamicamente os registros da tabela "treinos",
 * imprimindo em blocos de 5 linhas e permitindo filtro
 * incremental via método Consulta(String).
 */
public class ConsultaTreinos implements IConsulta {

    // largura de cada coluna na saída
    private final int larguraId             = 3;
    private final int larguraAlunoId        = 8;
    private final int larguraTipoTreino     = 15;
    private final int larguraDescricao      = 30;
    private final int larguraDuracao        = 4;
    private final int larguraDataInicio     = 12;
    private final int larguraEspaco         = 2;
    private final String strEspacos         = " ".repeat(larguraEspaco);

    // resultados da última consulta
    private int iContagemResultados;
    private int idTreinoUnico;

    // JDBC
    private Connection    connection = null;
    private Statement     statement  = null;
    private ResultSet     resultSet  = null;
    private ResultSet     countSet   = null;

    // formato de linha
    private String formato;

    /** retorna o ID único, quando iContagemResultados == 1 */
    public int getIdTreino() {
        return idTreinoUnico;
    }

    /** retorna quantos registros casaram no último filtro */
    public int getCount() {
        return iContagemResultados;
    }

    @Override
    public void Consulta() {
        ConsultaInterna("");
    }

    @Override
    public void Consulta(String filtroWhereSQL) {
        ConsultaInterna(filtroWhereSQL);
    }

    private void ConsultaInterna(String filtroWhereSQL) {
        System.out.println("\nListando Treinos...");
        try {
            // 1) abre conexão
            connection = com.programacaojava.academia.dao.UsuarioDao.conecta();
    
            // 2) monta consultas base e de contagem
            StringBuilder sql       = new StringBuilder("SELECT * FROM treinos");
            StringBuilder sqlCount  = new StringBuilder("SELECT COUNT(*) FROM treinos");

            boolean hasFiltro = filtroWhereSQL != null && !filtroWhereSQL.isBlank();
            if (hasFiltro) {
                String like = "%" + filtroWhereSQL + "%";
                String expr =
                      " WHERE CAST(id AS CHAR)           LIKE ? "
                    + "OR CAST(aluno_id AS CHAR)        LIKE ? "
                    + "OR tipo_treino                   LIKE ? "
                    + "OR descricao                      LIKE ? "
                    + "OR CAST(duracao_minutos AS CHAR) LIKE ? "
                    + "OR CAST(data_inicio AS CHAR)     LIKE ? ";

                sql.append(expr);
                sqlCount.append(expr);

                // executa COUNT(*)
                try (PreparedStatement pst = connection.prepareStatement(sqlCount.toString())) {
                    for (int i = 1; i <= 6; i++) {
                        pst.setString(i, like);
                    }
                    countSet = pst.executeQuery();
                    if (countSet.next()) {
                        iContagemResultados = countSet.getInt(1);
                        if (iContagemResultados == 1) {
                            // captura o ID do único treino
                            try (PreparedStatement pst2 = connection.prepareStatement(
                                    "SELECT id FROM treinos" + expr)) {
                                for (int i = 1; i <= 6; i++) pst2.setString(i, like);
                                ResultSet rs2 = pst2.executeQuery();
                                if (rs2.next()) {
                                    idTreinoUnico = rs2.getInt("id");
                                }
                                rs2.close();
                            }
                        }
                    }
                }
            } else {
                // sem filtro, conta todos
                statement = connection.createStatement();
                countSet  = statement.executeQuery(sqlCount.toString());
                if (countSet.next()) {
                    iContagemResultados = countSet.getInt(1);
                }
            }

            // 3) executa SELECT * e imprime
            if (hasFiltro) {
                PreparedStatement pst = connection.prepareStatement(sql.toString());
                String like = "%" + filtroWhereSQL + "%";
                for (int i = 1; i <= 6; i++) pst.setString(i, like);
                resultSet = pst.executeQuery();
            } else {
                resultSet = statement.executeQuery(sql.toString());
            }

            // imprime cabeçalho
            Cabecalho();

            int contador = 0;
            DateFormatter df = new DateFormatter();
            while (resultSet.next()) {
                if (contador % 5 == 0 && contador != 0) {
                    Cabecalho();
                }

                int    id            = resultSet.getInt("id");
                int    alunoId       = resultSet.getInt("aluno_id");
                String tipoTreino    = resultSet.getString("tipo_treino");
                String descricao     = resultSet.getString("descricao");
                int    duracao       = resultSet.getInt("duracao_minutos");
                String dataDb        = resultSet.getString("data_inicio");
                String dataFmt      = dataDb != null ? df.formatDate(dataDb) : "";

                // ajusta truncamentos
                if (tipoTreino   != null && tipoTreino.length()   > larguraTipoTreino)   tipoTreino   = tipoTreino.substring(0, larguraTipoTreino);
                if (descricao    != null && descricao.length()    > larguraDescricao)    descricao    = descricao.substring(0, larguraDescricao);
                String durStr     = String.valueOf(duracao);
                if (durStr.length() > larguraDuracao) durStr = durStr.substring(0, larguraDuracao);

                // imprime linha formatada
                System.out.printf(formato,
                    id,
                    alunoId,
                    tipoTreino,
                    descricao,
                    durStr,
                    dataFmt
                );

                contador++;
            }

        } catch (SQLException e) {
            System.err.println("Erro ao consultar treinos: " + e.getMessage());
        } finally {
            // fecha recursos
            try {
                if (countSet   != null) countSet.close();
                if (resultSet  != null) resultSet.close();
                if (statement  != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    }

    /** imprime cabeçalho dinamicamente montado */
    public void Cabecalho() {
        System.out.println();
        formato = String.format(
            "%%-%ds%s%%-%ds%s%%-%ds%s%%-%ds%s%%-%ds%s%%-%ds%%n",
             larguraId,         strEspacos,
             larguraAlunoId,    strEspacos,
             larguraTipoTreino, strEspacos,
             larguraDescricao,  strEspacos,
             larguraDuracao,    strEspacos,
             larguraDataInicio
        );

        String hId         = "ID";
        String hAlunoId    = "AlunoID";
        String hTipo       = "TipoTreino";
        String hDesc       = "Descrição";
        String hDuracao    = "Min";
        String hData       = "DataInício";

        System.out.printf(formato,
            pad(hId,         larguraId),
            pad(hAlunoId,    larguraAlunoId),
            pad(hTipo,       larguraTipoTreino),
            pad(hDesc,       larguraDescricao),
            pad(hDuracao,    larguraDuracao),
            pad(hData,       larguraDataInicio)
        );
        System.out.printf(formato,
            "-".repeat(larguraId),
            "-".repeat(larguraAlunoId),
            "-".repeat(larguraTipoTreino),
            "-".repeat(larguraDescricao),
            "-".repeat(larguraDuracao),
            "-".repeat(larguraDataInicio)
        );
    }

    /** preenche ou trunca até o tamanho desejado */
    private String pad(String s, int width) {
        if (s == null) s = "";
        if (s.length() > width) return s.substring(0, width);
        return String.format("%-" + width + "s", s);
    }

    public static void main(String[] args) {
        ConsultaTreinos c = new ConsultaTreinos();
        c.Consulta("10");  // testa listagem completa
    }
}
