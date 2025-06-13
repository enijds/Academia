/*

Sincronicação com o GitHub:


Convenções de nomenclatura em Java (nomes de variáveis, métodos, classes, constantes, pacotes e arquivos):
Booleanos (camelCase, prefixados com "is" ou "has")   : boolean isAtivo; boolean hasPermissao; boolean isConectado;
Classes de teste (PascalCase, terminam em "Test")     : class ValidadorCpfTest {...} class ProcessadorPagamentoTest {...}
Classes/Interfaces/Enums (PascalCase)                 : class PedidoVenda {...} interface ClienteDAO {...} enum StatusPedido {...} class GestorFinanceiro {...}
Constantes (SCREAMING_SNAKE_CASE)                     : public static final int MAX_RETRY_COUNT = 5; public static final String API_KEY = "xyz123"; public static final double GRAVIDADE_TERRA = 9.81;
Exceções (PascalCase, terminam em "Exception")        : class DadosInvalidosException {...} class RedeIndisponivelException {...}
Métodos (camelCase, verbos descritivos)               : void calcularTotal() {...} String buscarCliente(int id) {...} int gerarRelatorioAnual() {...} void atualizarEstoque(Produto item) {...}
Pacotes (lowercase, sem underscore)                   : package com.empresa.produto.submodulo; package org.sistema.banco.servicos;
Parâmetros de tipo genérico (uma letra, maiúscula)    : class Fila<T> {...} Map<K, V> mapearDados() {...}
Variáveis de instância (camelCase)                    : private long idTransacao; protected double saldoAtual;
Variáveis locais e parâmetros (camelCase)             : int totalPreco; String nomeUsuario; float mediaNotas; boolean pedidoConfirmado;

Arquivos de classe (PascalCase.java, idêntico à classe pública): PedidoVenda.java; ClienteDAO.java;
*/


package com.programacaojava.academia.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.programacaojava.academia.util.DateFormatter;

public class ConsultaAlunos implements IConsulta{

        // implentando a interface IConsulta para consulta de alunos
        // Define as larguras das colunas para a tabela de consulta

        // Define width variables for each column
        private final int larguraId             = 3 ;
        private final int larguraNome           = 20;
        private final int larguraCpf            = 14;
        private final int larguraDataNascimento = 12;
        private final int larguraEmail          = 18;
        private final int larguraTelefone       = 16;
        private final int larguraEspaco         = 2 ; // Espaços entre as colunas
        private final String strEspacos         = " ".repeat(larguraEspaco) ; 
        private int idPesquisa;
        public int idAluno;
        public int iContagemResultados;


        // Pega os resultados de pesquisa
        public int getIdAluno() {
            return idAluno;
        }

        public int getiContagemResultados() {
            return iContagemResultados;
        }


        // Declara variáveis para conexão e consulta
        // Essas variáveis serão usadas para estabelecer a conexão com o banco de dados e executar a consulta
        private Connection connection    = null;
        private Statement  statement     = null;
        private ResultSet  resultSet     = null;   
        private ResultSet  resultSetKeys = null;
        
        // Variável para armazenar o formato da linha
        private String formato;
        
        public void setFormatoLinha(String formato) {
            this.formato = formato;
            } // public void setFormatoLinha(String formato) {
        
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
            


            System.out.println("Listando Alunos...");

            // Tenta estabelecer a conexão com o banco de dados e verificar as credenciais
            // O bloco try-catch é usado para capturar exceções relacionadas à conexão e consulta ao banco de dados 
            // deve ser utilizada quando surge exceções relacionadas a banco de dados, como falha de conexão ou erro na consulta
            try {

                    // Conecta ao banco de dados usando o método conecta da classe UsuarioDao
                    // O método conecta retorna uma conexão com o banco de dados, ou null se a conexão falhar
                    connection = com.programacaojava.academia.dao.UsuarioDao.conecta();
                    if (connection == null) {

                        // Se a conexão falhar, exibe uma mensagem de erro e encerra o aplicativo
                        System.err.println("Falha ao conectar ao banco de dados. Encerrando o aplicativo.");
                        // System.exit(1) é usado para encerrar o programa com um código de erro
                        System.exit(1);

                        } // if (connection == null) {

                    // Cria um statement para executar a consulta
                    statement = connection.createStatement();
                    StringBuilder strSQL         = new StringBuilder("SELECT * FROM alunos"); // Consulta SQL
                    StringBuilder strSQLContagem = new StringBuilder("SELECT COUNT(*) FROM alunos"); // Quantidade de Consulta SQL
                    
                    // Se houver filtro não‐vazio, anexa " WHERE <filtro>"
                    if (filtroWhereSQL != null && !filtroWhereSQL.trim().isEmpty()) {
                            
                            // configura filtro
                            String filtroSQL    = "%" + filtroWhereSQL + "%";
                            String expressaoSQL = " "
                                        + "WHERE nome                          LIKE ? "
                                        + "   OR cpf                           LIKE ? "
                                        + "   OR CAST(id AS CHAR)              LIKE ? "
                                        + "   OR CAST(data_nascimento AS CHAR) LIKE ? "
                                        + "   OR CAST(telefone AS CHAR)        LIKE ? "
                                        + "   OR CAST(email AS CHAR)           LIKE ?"
                                        ;

                            // adiciona a expressão SQL ao StringBuilder
                            strSQL.append(expressaoSQL);
                            strSQLContagem.append(expressaoSQL);

                            // prepara os parametros para a consulta
                            try (PreparedStatement preparedStatement = connection.prepareStatement(strSQLContagem.toString())) {

                                    // substitui o "?" pelo valor de filtro sql
                                    for (int i = 1; i <= 6; i++) { preparedStatement.setString(i, filtroSQL); }

                                    // executa pesquisa
                                    resultSetKeys = preparedStatement.executeQuery();

                                    // se houver resultados, pega o primeiro registro
                                    if (resultSetKeys.next()) {

                                            // Carrega a quantidade de registros
                                            iContagemResultados = resultSetKeys.getInt(1);

                                            // Se apenas um resultado restar , pega o registro
                                            if (idPesquisa == 1 ) {

                                                    // Reconstroi a string da consulta SQL
                                                    strSQLContagem = new StringBuilder("SELECT id FROM alunos");

                                                    // Adiciona os critérios de pesquisa
                                                    strSQLContagem.append(expressaoSQL);

                                                    // Prepara para fazer a consulta
                                                    PreparedStatement preparedStatement1 = connection.prepareStatement( strSQLContagem.toString() );

                                                    // Substitui o "?" pelo valor de filtro sql
                                                    for (int i = 1; i <= 6; i++) { preparedStatement1.setString(i, filtroSQL); }

                                                    // Executa a consulta
                                                    resultSetKeys = preparedStatement1.executeQuery();
                                                    
                                                    // Pega o resuldo da pesquisa
                                                    resultSetKeys.next();

                                                    // Carrega na variavel de classe
                                                    idAluno = resultSetKeys.getInt("id" );
                                                                    
                                                    } // if (idPesquisa == 1 ) {

                                            } // if (resultSetKeys.next()) {

                                    } // try (PreparedStatement preparedStatement = connection.prepareStatement(strSQLContagem.toString())) {

                            // adiciona os parâmetros da consulta
                            try (PreparedStatement preparedStatement = connection.prepareStatement(strSQL.toString())) {

                                    // substitui o "?" pelo valor de filtro sql
                                    for (int i = 1; i <= 6; i++) { preparedStatement.setString(i, filtroSQL); }

                                    // executa pesquisa
                                    resultSet = preparedStatement.executeQuery();

                                    } // try (PreparedStatement preparedStatement = connection.prepareStatement(strSQL.toString())) {
                                
                            } // if (filtroWhereSQL != null && !filtroWhereSQL.trim().isEmpty()) {

                        else {
                                // Executa a consulta SQL e obtém o resultado
                                // O método executeQuery executa a consulta e retorna um ResultSet contendo os resultados
                                resultSet = statement.executeQuery(strSQL.toString());
                            }

                    // adicona contador
                    int contador = 0;

                    // Chama o método para imprimir o cabeçalho
                    Cabecalho();
                    
                    // Processa os resultados
                    while (resultSet.next()) {

                            // Se o contador for divisível por 5, imprime o cabeçalho novamente
                            // Isso garante que a tabela seja impressa em blocos de 5 linhas
                            if( contador % 5 == 0 && contador != 0) {                                
                                    // Chama o método para imprimir o cabeçalho
                                    Cabecalho();
                                    }

                            int    id              = resultSet.getInt("id"                 );
                            String nome            = resultSet.getString("nome"            );
                            String cpf             = resultSet.getString("cpf"             );
                            String data_db         = resultSet.getString("data_nascimento" );
                            String email           = resultSet.getString("email"           );
                            String telefone        = resultSet.getString("telefone"        );
                            
                            // Manipulador de string de data
                            DateFormatter dateFormatter = new DateFormatter();

                            // Formata a data de nascimento para o formato "dd/MM/yyyy"
                            String data_nascimento = null;
                            if (data_db != null) {
                                    data_nascimento = dateFormatter.formatDate(data_db);
                                    } // if (data_db != null) {

                            // Ajusta o tamanho dos dados para que não ultrapassem os limites definidos
                            nome           = (nome           != null&& nome.length()           >larguraNome          )? nome.substring(0            , larguraNome)          : nome           ;
                            cpf            = (cpf            != null&& cpf.length()            >larguraCpf           )? cpf.substring(0             , larguraCpf)           : cpf            ;
                            data_nascimento= (data_nascimento!= null&& data_nascimento.length()>larguraDataNascimento)? data_nascimento.substring(0 , larguraDataNascimento): data_nascimento;
                            email          = (email          != null&& email.length()          >larguraEmail         )? email.substring(0           , larguraEmail)         : email          ;
                            telefone       = (telefone       != null&& telefone.length()       >larguraTelefone      )? telefone.substring(0        , larguraTelefone)      : telefone       ;

                            // Imprime os resultados formatados
                            System.out.printf(formato, id, nome, cpf, data_nascimento, email, telefone);
                        
                            // incrementa o contador
                            // O contador é usado para controlar a impressão do cabeçalho
                            contador++;

                            } // while (resultSet.next()) {
                        } catch (SQLException e) {
                                System.err.println("Erro ao consultar o banco de dados: " + e.getMessage());
                        } finally {
                                // Fecha os recursos
                                try {
                                            if (resultSet != null) resultSet.close();
                                            if (statement != null) statement.close();
                                            if (connection != null) connection.close();
                                        } catch (SQLException e) {
                                            System.err.println("Erro ao fechar recursos: " + e.getMessage());
                                        } // try
                        } // finally
                } // public void Consulta() {


        public void Cabecalho() {

            // Imprime os dados formatados
            // setFormatoLinha("%-3s  %-20s  %-14s %-11s %-25s  %-15s%n"); 

            // Build the format string dynamically using the width variables
            System.out.println("\n");
            formato = String.format("%%-%ds%s%%-%ds%s%%-%ds%s%%-%ds%s%%-%ds%s%%-%ds%%n",
                                            larguraId            , strEspacos ,
                                            larguraNome          , strEspacos , 
                                            larguraCpf           , strEspacos ,
                                            larguraDataNascimento, strEspacos ,
                                            larguraEmail         , strEspacos , 
                                            larguraTelefone      );

            // brincando de "sed" no java
            String tituloId              = "Id                                ";
            String titulonome            = "Nome                              ";
            String titulocpf             = "CPF                               ";   
            String titulodata_nascimento = "Data Nascimento                   ";
            String tituloemail           = "Email                             ";
            String titulotelefone        = "Telefone                          ";
            
            // Ajusta o tamanho dos títulos para que não ultrapassem os limites definidos
            tituloId              =( tituloId.length()             > larguraId            )? tituloId.substring(0              , larguraId            ): tituloId;
            titulonome            =( titulonome.length()           > larguraNome          )? titulonome.substring(0            , larguraNome          ): titulonome;
            titulocpf             =( titulocpf.length()            > larguraCpf           )? titulocpf.substring(0             , larguraCpf           ): titulocpf;
            titulodata_nascimento =( titulodata_nascimento.length()> larguraDataNascimento)? titulodata_nascimento.substring(0 , larguraDataNascimento): titulodata_nascimento;
            tituloemail           =( tituloemail.length()          > larguraEmail         )? tituloemail.substring(0           , larguraEmail         ): tituloemail;
            titulotelefone        =( titulotelefone.length()       > larguraTelefone      )? titulotelefone.substring(0        , larguraTelefone      ): titulotelefone;

            // Imprime o cabeçalho formatado
            System.out.printf(formato, 
                                    tituloId              ,   //"Id"             ,
                                    titulonome            ,   //"Nome"           , 
                                    titulocpf             ,   //"CPF"            , 
                                    titulodata_nascimento ,   //"Data Nascimento", 
                                    tituloemail           ,   //"Email"          , 
                                    titulotelefone        );  //"Telefone"       );

            // Linhas separadora                                
            System.out.printf(formato,
                                    "-".repeat(larguraId            ),  // ID              : repeats "-" for larguraId
                                    "-".repeat(larguraNome          ),  // Nome            : repeats "-" for larguraNome
                                    "-".repeat(larguraCpf           ),  // CPF             : repeats "-" for larguraCpf
                                    "-".repeat(larguraDataNascimento),  // Data Nascimento : repeats "-" for larguraDataNascimento
                                    "-".repeat(larguraEmail         ),  // Email           : repeats "-" for larguraEmail
                                    "-".repeat(larguraTelefone      )); // Telefone        : repeats "-" for larguraTelefone
            
            } // public void Cabecalho() {

        
        // main para teste de execução dentro da classe
        public static void main(String[] args) {
            
            ConsultaAlunos consulta = new ConsultaAlunos();
            consulta.Consulta("3");
            
        } // public static void main(String[] args) {

} // public class ConsultaAlunos {
