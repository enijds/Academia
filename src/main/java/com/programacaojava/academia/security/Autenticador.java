package com.programacaojava.academia.security;

import java.security.NoSuchAlgorithmException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Autenticador {

    private boolean isLoggedIn = false;
    private final GerenciadorCredenciais credenciais = new GerenciadorCredenciais();


    // Método para autenticar o usuário
    // Este método verifica as credenciais do usuário no banco de dados e retorna true se o login for bem-sucedido
    // autenticar é um nome bonito para comparar as credenciais do usuário com as armazenadas no banco de dados
    public boolean autenticar(String usuario, String senha){

            // Declara variáveis para conexão e consulta
            // Essas variáveis serão usadas para estabelecer a conexão com o banco de dados e executar a consulta
            Connection connection = null;
            Statement statement = null;
            ResultSet resultSet = null;

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
                }

                // Cria um statement para executar a consulta
                // O statement é usado para enviar comandos SQL ao banco de dados
                statement = connection.createStatement();

                // Prepara a consulta SQL para verificar as credenciais do usuário
                // aqui construimos a consulta SQL para buscar o nome de usuário e a senha do banco de dados
                String sql = "SELECT nome_usuario, senha_usuario FROM usuarios WHERE nome_usuario = ?";

                // Usar PreparedStatement para evitar SQL Injection
                // PreparedStatement é usado para executar consultas SQL com parâmetros
                // Isso ajuda a evitar ataques de injeção de SQL e melhora a segurança do aplicativo
                // O método setString substitui o ? pelo valor do usuário fornecido
                // a ideia é evitar injeção do código SQL malicioso pela propria variavel usuario. que poderia acontecer 
                // com algo como usuario = "admin'; DROP TABLE usuarios; --";
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                // Define o valor do parâmetro na consulta SQL
                // aqui ele substitui o ? pelo valor da variável usuario
                // Isso é feito para evitar injeção de SQL e garantir que a consulta seja segura
                preparedStatement.setString(1, usuario); // Substitui o ? pelo valor de usuario

                // Executa a consulta SQL e obtém o resultado
                // O método executeQuery executa a consulta e retorna um ResultSet contendo os resultados
                resultSet = preparedStatement.executeQuery();

                // Exibe a senha criptografada
                // Aqui chamamos o método generateSHA256Hash para gerar o hash da senha fornecida pelo usuário
                String senhaCriptografada = credenciais.generateSHA256Hash(senha); // Gera o hash da senha fornecida
                // Exibe a senha criptografada no console
                // Isso é útil para depuração e verificação de segurança
                // System.out.println("Senha criptografada: " + senhaCriptografada);

                // Itera sobre os resultados da consulta
                // O método next() do ResultSet move o cursor para o próximo registro e retorna true se houver mais registros
                while (resultSet.next()) {

                        // Obtém os valores do resultado da consulta para usuario e senha para validar as credenciais
                        String nomeUsuario = resultSet.getString("nome_usuario");
                        String senhaUsuario = resultSet.getString("senha_usuario");

                        // teste os valores obtidos do banco de dados
                        // System.out.println("Nome de usuário: " + nomeUsuario);
                        // System.out.println("Senha armazenada: " + senhaUsuario);

                        // Compara o hash gerado com o hash armazenado
                        // equals compara duas strings e retorna true se elas forem iguais
                        if (usuario.equals(nomeUsuario) && senhaCriptografada.equals(senhaUsuario)) {
                                System.out.println("Login bem-sucedido!");
                                isLoggedIn = true;
                                break;
                            } else {
                                System.out.println("Credenciais inválidas. Tente novamente.");
                                isLoggedIn = false;
                            } // if (usuario.equals(nomeUsuario) && senhaCriptografada.equals(senhaUsuario)) {
                    } // while (resultSet.next()) {

            // catch é usado quando ocorre uma exceção durante a consulta ao banco de dados
            // exceção é analogo a um erro, um problema que ocorreu durante a execução do código
            // Neste caso, capturamos exceções relacionadas ao banco de dados e à geração do hash da senha        
            } catch (SQLException e) {
                System.err.println("Erro ao consultar o banco de dados: " + e.getMessage());

            // NoSuchAlgorithmException é uma exceção que ocorre quando o algoritmo de hash especificado não está disponível
            // Isso pode acontecer se o algoritmo SHA-256 não estiver implementado na JVM    
            } catch (NoSuchAlgorithmException e) {
                System.err.println("Erro ao gerar hash da senha: " + e.getMessage());
            
            // a diretiva finally é usada para garantir que os recursos sejam fechados, independentemente de ocorrerem exceções ou não
            // Isso é importante para evitar vazamentos de recursos, como conexões de banco de dados não fechadas   
            // o programa não para nos erros, depois ele executa o bloco finally para fechar os recursos
            } finally {

                // Mais um bloco try-catch para fechar os recursos
                // Fechar os recursos utilizados na consulta ao banco de dados
                try {
                    if (resultSet != null) resultSet.close();
                    if (statement != null) statement.close();
                    if (connection != null) connection.close();

                // caso nao tenha sucesso ao fechar os recursos, exibe uma mensagem de erro    
                } catch (SQLException e) { 
                    System.err.println("Erro ao fechar recursos: " + e.getMessage());
                }
            }

            // Remove a simulação de credenciais fixas
            /* if (usuario.equals("admin") && senha.equals("senha123")) {
                System.out.println("Login bem-sucedido!");
                isLoggedIn = true;
            } else {
                System.out.println("Credenciais inválidas. Tente novamente.");
                isLoggedIn = false;
            } */

            return isLoggedIn;
        } // public boolean autenticar() {


} // public class Autenticador
