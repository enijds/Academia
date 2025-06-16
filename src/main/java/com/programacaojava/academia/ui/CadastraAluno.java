package com.programacaojava.academia.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.programacaojava.academia.util.CPFFormatter;
import com.programacaojava.academia.util.DateFormatter;
import com.programacaojava.academia.util.DateFormatterForConsole;
import com.programacaojava.academia.util.TelefoneFormatter;



// Classe CadastraAluno
public class CadastraAluno {

    // Atributos do aluno
    private String nome            ;
    private String cpf             ;
    private String data_db         ;
    private String data_nascimento ; 
    private String email           ;
    private String telefone        ;

    // Construtor da classe CadastraAluno
    public CadastraAluno() {
            // Inicializa os atributos do aluno, se necessário
            this.nome     = "" ;
            this.cpf      = "" ;
            this.data_db  = "" ;
            this.email    = "" ;
            this.telefone = "" ;
            } // public CadastraAluno() {

    // Scanner para ler a entrada do usuário
    Scanner scanner = new Scanner(System.in);
    
    public String getNome() {
            return nome;
            } // public String getNome() {

    public void setNome(String nome) {
            this.nome = nome;
            } // public void setNome(String nome) {

    public String getCpf() {
            return cpf;
            } // public String getCpf() {

    public void setCpf(String cpf) {
            this.cpf = cpf;
            } // public void setCpf(String cpf) {

    public String getData_db() {
        return data_db;
        } // public String getData_db() {

    public void setData_db(String data_db) {
            this.data_db = data_db;
            } // public void setData_db(String data_db) {
    
    public String getData_nascimento() {
            return data_nascimento;
            } // public String getData_nascimento() {

    public void setData_nascimento(String data_nascimento) {
            this.data_nascimento = data_nascimento;
            } // public void setData_nascimento(String data_nascimento) {
        
        public String getEmail() {
            return email;
            } // public String getEmail() {
            
    public void setEmail(String email) {
            this.email = email;
            } // public void setEmail(String email) {

    public String getTelefone() {
            return telefone;
            } // public String getTelefone() {
            
        public void setTelefone(String telefone) {
            this.telefone = telefone;
            } // public void setTelefone(String telefone) {
            
    // Método para cadastrar um aluno
    public void cadastraAluno() throws Throwable {
        
        // Instancia um objeto da classe DateFormatterForConsole
        DateFormatterForConsole dateFormatterForConsole = new DateFormatterForConsole();

        // Instancia um objeto da classe CPFFormatter
        CPFFormatter cpfformatter = new CPFFormatter();

        // Intancia  um objeto da classe TelefoneFormatter
        TelefoneFormatter telefoneformatter = new TelefoneFormatter();

        // Usuariorio digita os dados do aluno
        System.out.println("Por favor, insira os dados do aluno para continuar.");
        
        // Data no formato iso
        data_db  = dateFormatterForConsole.lerData(); //scanner.nextLine();
        System.out.print("Nome: "              ); nome     = scanner.nextLine();
        cpf = cpfformatter.lerCPF();
        System.out.print("Email: "             ); email    = scanner.nextLine();
        //System.out.print("Telefone: "          ); telefone = scanner.nextLine();
        telefone = telefoneformatter.lerTelefone();

        // Manipulador de string de data
        DateFormatter dateFormatter = new DateFormatter();

        // Formata a data de nascimento para o formato "dd/MM/yyyy"
        if (data_db != null) {
                data_nascimento = dateFormatter.formatDateForDB(data_db);
                } // if (data_db != null) {

        
        // Metodo para inserir o aluno no banco de dados
        InserirAlunoDB();

        // Lógica para cadastrar um aluno
        System.out.println("Cadastro de aluno realizado com sucesso!");
        } // public void cadastraAluno() {
        


        
        
    public boolean InserirAlunoDB() {
    
            // Declaração de variáveis para conexão, statement e resultSet
            // Essas variáveis serão usadas para conectar ao banco de dados e executar a consulta
            Connection connection = null;
            Statement statement   = null;
            ResultSet resultSet   = null;
        
            // Exibe uma mensagem informando que os treinos estão sendo listados
            System.out.println("Listando Treinos...");
        
            try {

                // Conecta ao banco de dados usando o método conecta da classe UsuarioDao
                connection = com.programacaojava.academia.dao.UsuarioDao.conecta();

                // Verifica se a conexão foi bem-sucedida
                if (connection == null) {
                        System.err.println("Falha ao conectar ao banco de dados. Encerrando o aplicativo.");
                        System.exit(1);
                        } // if (connection == null) {
                        
                // Cria um statement para executar a consulta
                statement = connection.createStatement();
                
                // Prepara a consulta SQL inserir os dados do aluno protegendo de SQL Injection
                // O método setString substitui os ? pelos valores fornecidos    
                // O statement.executeQuery executa a consulta e retorna um ResultSet
                String sql = "INSERT INTO alunos (nome, cpf, data_nascimento, telefone, email) VALUES (?, ?, ?, ?, ?)";
                
                // Cria um PreparedStatement para executar a consulta SQL
                // O PreparedStatement é usado para executar consultas SQL com parâmetros
                statement = connection.prepareStatement(sql);
                
                // Substitui os ? pelos valores do aluno
                // O método setString é usado para definir os valores dos parâmetros na consulta SQL
                PreparedStatement preparedStatement = connection.prepareStatement(sql);        
                
                // Substitui os ? pelos valores do aluno
                preparedStatement.setString(1, getNome()            ); // Substitui o primeiro ? pelo nome do aluno
                preparedStatement.setString(2, getCpf()             ); // Substitui o segundo ? pelo CPF do aluno
                preparedStatement.setString(3, getData_nascimento() ); // Substitui o terceiro ? pela data de nascimento do aluno
                preparedStatement.setString(4, getTelefone()        ); // Substitui o quarto ? pelo telefone do aluno
                preparedStatement.setString(5, getEmail()           ); // Substitui o quinto ? pelo email do aluno
                preparedStatement.executeUpdate();
                
                // Cria um objeto ConsultaAlunos para executar a consulta SQL para verificar se os dados foram inseridos corretamente
                ConsultaAlunos consulta = new ConsultaAlunos();
                
                // Chama o método Consulta() da classe ConsultaAlunos para exibir os dados inseridos
                consulta.Consulta("Nome like '"+getNome()+"'");
                
                return true; // Retorna true se a conexão e consulta forem bem-sucedidas
                
                } catch (SQLException e) {
                        // Exibe uma mensagem de erro se ocorrer uma exceção ao consultar o banco de dados
                        System.err.println("Erro ao consultar o banco de dados: " + e.getMessage());
                } finally {
                        // Fecha os recursos utilizados
                        // O bloco finally é usado para garantir que os recursos sejam fechados, independentemente de ocorrerem exceções
                        try {
                                // Fecha os recursos utilizados
                                // O bloco finally é usado para garantir que os recursos sejam fechados, independentemente de ocorrerem exceções
                                if (resultSet  != null) resultSet.close();
                                if (statement  != null) statement.close();
                                if (connection != null) connection.close();
                            } catch (SQLException e) {
                                // Exibe uma mensagem de erro se ocorrer uma exceção ao fechar os recursos
                                // Isso é importante para evitar vazamentos de recursos
                                System.err.println("Erro ao fechar recursos: " + e.getMessage());
                            } // try
                        } // finally {
        
            return false;
    
    } // InserirAlunoDB()

    // Método main para executar o cadastro de aluno
    public static void main(String[] args) {
        CadastraAluno cadastro = new CadastraAluno();
        
        try {
            cadastro.cadastraAluno();
        } catch (Throwable e) {  }
    } // public static void main(String[] args) {


} // public class CadastraAluno {

        