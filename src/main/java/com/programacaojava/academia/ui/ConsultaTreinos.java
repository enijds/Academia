package com.programacaojava.academia.ui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.programacaojava.academia.util.DateFormatter;

public class ConsultaTreinos implements IConsulta {
    // interface implementada para consulta de treinos

    // Larguras das colunas
    private final int larguraId         = 3 ;
    private final int larguraAlunoId    = 7 ;
    private final int larguraTipoTreino = 12;
    private final int larguraDescricao  = 55;
    private final int larguraDuracao    = 3 ;
    private final int larguraDataInicio = 12;
    private final int larguraEspaco     = 2 ;
    private final String strEspacos     = " ".repeat(larguraEspaco);

    private String formato;

    public void setFormatoLinha(String formato) {
        this.formato = formato;
    }

  

    // implementação do método da interface sem parametro
    @Override
    public void Consulta() {
        
        // Adiconando uma string vazia
        ConsultaInterna("");

        }

    // implementação do método da interface com parametro
    // Sobrecarga: mesma ação, mas com filtro opcional
    @Override
    public void Consulta(String filtroWhereSQL) {

        // Para adicionar o parametro
        ConsultaInterna(filtroWhereSQL);
        
        }

    // O método abaixo evita ter que copiar dois métodos
    // para alterar apenas o sql
    public void ConsultaInterna(String filtroWhereSQL) {

        // Declaração de variáveis para conexão, statement e resultSet
        // Essas variáveis serão usadas para conectar ao banco de dados e executar a consulta
        Connection connection = null;
        Statement statement   = null;
        ResultSet resultSet   = null;

        // Exibe uma mensagem informando que os treinos estão sendo listados
        System.out.println("Listando Treinos...");

        try {
            connection = com.programacaojava.academia.dao.UsuarioDao.conecta();
            if (connection == null) {
                System.err.println("Falha ao conectar ao banco de dados. Encerrando o aplicativo.");
                System.exit(1);
            }


            // Cria um statement para executar a consulta
            statement = connection.createStatement();
            StringBuilder sql = new StringBuilder("SELECT * FROM treinos"); // Consulta SQL

            // Se houver filtro não‐vazio, anexa " WHERE <filtro>"
            if (filtroWhereSQL != null && !filtroWhereSQL.trim().isEmpty()) {
                sql.append(" WHERE ").append(filtroWhereSQL);
                }

            // Executa a consulta SQL e obtém o resultado
            // O método executeQuery executa a consulta e retorna um ResultSet contendo os resultados
            resultSet = statement.executeQuery(sql.toString());

            int contador = 0;
            cabecalho();

            while (resultSet.next()) {
                if (contador % 5 == 0 && contador != 0) {
                    cabecalho();
                }

                int    id         = resultSet.getInt(   "id"             );
                int    alunoId    = resultSet.getInt(   "aluno_id"       );
                String tipoTreino = resultSet.getString("tipo_treino"    );
                String descricao  = resultSet.getString("descricao"      );
                int    duracao    = resultSet.getInt(   "duracao_minutos");
                String data_db    = resultSet.getString("data_inicio"    );

                // Manipulador de string de data
                DateFormatter dateFormatter = new DateFormatter();

                // Formata a data de nascimento para o formato "dd/MM/yyyy"
                String dataInicio = null;
                if (data_db != null) {
                    dataInicio = dateFormatter.formatDate(data_db);
                    } // if (data_db != null) {
                
                tipoTreino = (tipoTreino != null && tipoTreino.length() > larguraTipoTreino) ? tipoTreino.substring(0, larguraTipoTreino) : tipoTreino;
                descricao  = (descricao  != null && descricao.length()  > larguraDescricao)  ? descricao.substring(0, larguraDescricao)    : descricao;
                dataInicio = (dataInicio != null && dataInicio.length() > larguraDataInicio) ? dataInicio.substring(0, larguraDataInicio) : dataInicio;

                System.out.printf(formato,
                        id,
                        alunoId,
                        tipoTreino,
                        descricao,
                        duracao,
                        dataInicio);

                contador++;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao consultar o banco de dados: " + e.getMessage());
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                System.err.println("Erro ao fechar recursos: " + e.getMessage());
            }
        }
    } // Consulta()

  public void cabecalho() {

        //Pula a linha
        System.out.println("\n");

        // Define o formato da linha de cabeçalho
        // Utiliza String.format para criar o formato da linha
        formato = String.format("%%-%ds%s%%-%ds%s%%-%ds%s%%-%ds%s%%-%ds%s%%-%ds%%n",
                larguraId, strEspacos,
                larguraAlunoId, strEspacos,
                larguraTipoTreino, strEspacos,
                larguraDescricao, strEspacos,
                larguraDuracao, strEspacos,
                larguraDataInicio);

        // Define os títulos das colunas
        String tituloId         = "Id";
        String tituloAlunoId    = "AlunoId";
        String tituloTipoTreino = "TipoTreino";
        String tituloDescricao  = "Descricao";
        String tituloDuracao    = "Minutos";
        String tituloDataInicio = "DataInicio";

        // Ajusta o tamanho dos títulos
        tituloId         = tituloId.length()         > larguraId         ? tituloId.substring(0, larguraId)         : tituloId;
        tituloAlunoId    = tituloAlunoId.length()    > larguraAlunoId    ? tituloAlunoId.substring(0, larguraAlunoId): tituloAlunoId;
        tituloTipoTreino = tituloTipoTreino.length() > larguraTipoTreino ? tituloTipoTreino.substring(0, larguraTipoTreino): tituloTipoTreino;
        tituloDescricao  = tituloDescricao.length()  > larguraDescricao  ? tituloDescricao.substring(0, larguraDescricao): tituloDescricao;
        tituloDuracao    = tituloDuracao.length()    > larguraDuracao    ? tituloDuracao.substring(0, larguraDuracao): tituloDuracao;
        tituloDataInicio = tituloDataInicio.length() > larguraDataInicio ? tituloDataInicio.substring(0, larguraDataInicio): tituloDataInicio;

        System.out.printf(formato,
                tituloId         , 
                tituloAlunoId    , 
                tituloTipoTreino , 
                tituloDescricao  , 
                tituloDuracao    , 
                tituloDataInicio );

        System.out.printf(formato,
                "-".repeat(larguraId),
                "-".repeat(larguraAlunoId),
                "-".repeat(larguraTipoTreino),
                "-".repeat(larguraDescricao),
                "-".repeat(larguraDuracao),
                "-".repeat(larguraDataInicio));
    }


    public static void main(String[] args) {
        ConsultaTreinos consulta = new ConsultaTreinos();
        consulta.Consulta();
    }
}
