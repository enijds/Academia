package com.programacaojava.academia.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class UsuarioDao {
    
    public static Connection conecta() {
        
        // Parâmetros de conexão
        // quebrei em variaveis para facilitar a leitura
        String db       = "mariadb";     
        String host     = "localhost";
        String port     = "3306";
        String dbName   = "academia";
        String url      = "jdbc:" + db + "://" + host + ":" + port + "/" + dbName;
    //      String url      = "jdbc:mariadb://localhost:3306/a3"
        
        String user     = "aplicacao";                             
        String password = "idbrNyF4Unn1v6tb";                       

        // Variáveis para conexão e consulta
        Connection connection ;

        // o try-catch é usado para capturar erros de conexão
        // e exibir uma mensagem de erro apropriada
        try {
                // Estabelece a conexão
                // System.out.println("Conectando ao banco de dados...");
                connection = DriverManager.getConnection(url, user, password);
                
                // System.out.println("Conexão estabelecida com sucesso!");
                // System.out.print("Conexão estabelecida com sucesso!");
                // retorna o valor, o objeto connection
                // que representa a conexão com o banco de dados
                return connection; 
                } 
        catch (SQLException e) {
                System.out.println("Erro ao consultar o banco de dados: " + e.getMessage());

                // Retorna null se a conexão falhar
                // falhou a conexão com o banco de dados
                return null;
                } 

        } // public static void conecta() {

        
        
        
        
    public static void main(String[] args) {

        // Só para testar:
        Connection connTeste = conecta();
        if (connTeste != null) {
                try {
                        connTeste.close();
                } catch (SQLException e) {
                        // ignorar
                        System.err.println("Mensagem de erro: " + e.getMessage());}
                }  
            
        } // public static void main(String[] args) {



} // public class UsuarioDao
