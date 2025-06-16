package com.programacaojava.academia.ui;

import java.io.Console;
import java.io.Reader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Scanner;

import com.programacaojava.academia.util.CPFFormatter;
import com.programacaojava.academia.util.DateFormatter;
import com.programacaojava.academia.util.DateFormatterForConsole;
import com.programacaojava.academia.util.TelefoneFormatter;


public class AlteraAluno {

    // Atributos do aluno
    private int    id             ;
    private String nome           ;
    private String cpf            ;
    private String dataNascimento ; 
    private String email          ;
    private String telefone       ;

    private String novoNome       ;
    private String novoCpf        ;
    private String novaData       ; 
    private String novoEmail      ;
    private String novoTelefone   ;

    private int    ichar          ;
    private char   cchar          ;

    private final int    espacos = 80   ; 

    /**
     * @param idAluno
     * @throws Exception
     */
    public void atualizaCadastro(int idAluno) throws Exception {

        // Verifica se o console está disponível
        Console console = System.console();

        // Se o console estiver disponível, lê a data do usuário    
        Reader reader = console.reader();

        // inicia o objeto scanner apartir da classe scanner
        Scanner scanner = new Scanner(System.in);

        // conversor de data "00/00/0000" para iso
        DateFormatter dateFormatter = new DateFormatter();

        // Instancia um objeto da classe DateFormatterForConsole
        DateFormatterForConsole dateFormatterForConsole = new DateFormatterForConsole();

        // Instancia um objeto da classe CPFFormatter
        CPFFormatter cpfformatter = new CPFFormatter();

        // Intancia  um objeto da classe TelefoneFormatter
        TelefoneFormatter telefoneformatter = new TelefoneFormatter();
        
        // Declaração de variáveis para conexão, statement e resultSet
        // Essas variáveis serão usadas para conectar ao banco de dados e executar a consulta
        Connection        connection        = null;
        ResultSet         resultSet         = null;
        PreparedStatement preparedStatement = null;
        PreparedStatement updateStatment    = null;
    

        // Conecta ao banco de dados usando o método conecta da classe UsuarioDao
        try {

            // Estabelece a conexão com o banco
            connection = com.programacaojava.academia.dao.UsuarioDao.conecta();

            // Consulta o registro pelo ID para carregar os dados do aluno
            String selectSQL = "SELECT id, nome, cpf, data_nascimento, email, telefone FROM alunos WHERE id = ?";
            preparedStatement = connection.prepareStatement(selectSQL);
            preparedStatement.setInt(1, idAluno);
            resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {

                // as informações do aluno
                id              =resultSet.getInt("id"                );
                nome            =resultSet.getString("nome"           );
                cpf             =resultSet.getString("cpf"            );
                dataNascimento  =resultSet.getString( "data_nascimento" );
                email           =resultSet.getString("email"          );
                telefone        =resultSet.getString("telefone"       );

                // Formata a data para exibição
                // String dataFormatada = (dataNascimento != null) ? simpleDateFormat.format(dataNascimento) : "00/00/0000";
                String dataFormatada = (dataNascimento != null) ? dateFormatter.formatDate(dataNascimento) : "00/00/0000";
                

                // Nome
                System.out.print("\nNome:     " + nome + " (manter? N|n para alterar) ");
                ichar = reader.read();  // Lê um único caractere
                cchar = (char) ichar;   // retorna um int, pode ser -1 se falhar
                // Verifica se o caractere é 'N' ou 'n'
                if (cchar == 'N' || cchar == 'n') {
                    System.out.print("\rNome:" + " ".repeat(espacos) + "Antigo: " + nome);
                    System.out.print("\rNome:     ");
                    novoNome =  scanner.nextLine();
                    }
                else {
                    novoNome = nome;
                    System.out.println("");
                    }
                

                //String novoCpf
                System.out.print("CPF:      " + cpf + " (manter? N|n para alterar) ");
                ichar = reader.read();  // Lê um único caractere
                cchar = (char) ichar;   // retorna um int, pode ser -1 se falhar
                // Verifica se o caractere é 'N' ou 'n'
                if (cchar == 'N' || cchar == 'n') {
                    System.out.print("\rCpf: " + " ".repeat(espacos) + "Antigo: " + cpf);
                    System.out.print("\rCpf:     ");
                    novoCpf =  cpfformatter.lerCPF();
                    }
                else { 
                    novoCpf = cpf;
                    System.out.println("");
                    }


                System.out.print("Data:     " + dataFormatada + " (manter? N|n para alterar) ");
                ichar = reader.read();  // Lê um único caractere
                cchar = (char) ichar;   // retorna um int, pode ser -1 se falhar
                // Verifica se o caractere é 'N' ou 'n'
                if (cchar == 'N' || cchar == 'n') {
                    System.out.print("\rData: " + " ".repeat(espacos) + "Antigo: " + dataFormatada);
                    System.out.print("\rData:     ");
                    novaData = dateFormatter.formatDateForDB((dateFormatterForConsole.lerData()));
                    }
                else {
                    novaData = dateFormatter.formatDateForDB(dataNascimento);
                    System.out.println("");
                    }
            
                System.out.print("Email:    " + email + " (manter? N|n para alterar) ");
                ichar = reader.read();  // Lê um único caractere
                cchar = (char) ichar;   // retorna um int, pode ser -1 se falhar
                // Verifica se o caractere é 'N' ou 'n'
                if (cchar == 'N' || cchar == 'n') {
                    System.out.print("\rEmail: " + " ".repeat(espacos) + "Antigo: " + email);
                    System.out.print("\rEmail:     ");
                    novoEmail =  scanner.nextLine();
                    }
                else {
                    novoEmail = email;
                    System.out.println("");
                    }


                System.out.print("Telefone: " + telefone + " (manter? N|n para alterar) ");
                ichar = reader.read();  // Lê um único caractere
                cchar = (char) ichar;   // retorna um int, pode ser -1 se falhar
                // Verifica se o caractere é 'N' ou 'n'
                if (cchar == 'N' || cchar == 'n') {
                    System.out.print("\rTelefone: " + " ".repeat(espacos) + "Antigo: " + email);
                    System.out.print("\rTelefone:     ");
                    novoTelefone =  telefoneformatter.lerTelefone();
                    }
                else {
                    novoTelefone = telefone;
                    System.out.println("");
                    }



                // Atualiza o registro
                String updateSQL = "UPDATE alunos SET nome = ?, cpf = ?, data_nascimento = STR_TO_DATE(?, '%d/%m/%Y'), email = ?, telefone = ? WHERE id = ?";
                updateSQL = "UPDATE alunos SET nome = ?, cpf = ?, data_nascimento = ?, email = ?, telefone = ? WHERE id = ?";
                updateStatment = connection.prepareStatement(updateSQL);
                updateStatment.setString(1, novoNome);
                updateStatment.setString(2, novoCpf);
                updateStatment.setString(3, novaData);
                updateStatment.setString(4, novoEmail);
                updateStatment.setString(5, novoTelefone);
                updateStatment.setInt(6, id);

                int rowsAffected = updateStatment.executeUpdate();
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
                if (resultSet         != null) resultSet.close()         ;
                if (preparedStatement != null) preparedStatement.close() ;
                if (updateStatment    != null) updateStatment.close()    ;
                if (connection        != null) connection.close()        ;
            } // try


    } // private void atualizaCadastro(int idAluno) {

}
