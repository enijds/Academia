package com.programacaojava.academia.ui;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

import com.programacaojava.academia.dao.UsuarioDao;
import com.programacaojava.academia.util.DateFormatter;
import com.programacaojava.academia.util.DateFormatterForConsole;


public class CadastraTreino {

    public int idGerado;

    /**
     * testando o uso do javadoc
     */
    public static void main(String[] args) {

        CadastraTreino app = new CadastraTreino();
        try {
            app.cadastraTreino();
        } catch (Exception e) {
            // TODO Auto-generated catch block

        }
    }
    


    
    /**
     * Solicita ao usuário os dados de um novo treino e insere no banco.
     * @throws Exception 
     */
    public void cadastraTreino() throws Exception {
        
       // System.out.println(".(q?)");
        // Para digitção vamos utilizar o objeto scnner da classe Scanner
        Scanner scanner = new Scanner(System.in);
        Connection        connection       = null;
        PreparedStatement prepareStatement = null;
        ResultSet         resultSetKeys    = null;
        
        try {
            // Leitura dos dados pelo console
            System.out.print("ID do aluno (aluno_id): ");
            int alunoId = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Tipo de treino (ex: Musculação, Aeróbico): ");
            String tipoTreino = scanner.nextLine().trim();

            System.out.print("Descrição do treino: ");
            String descricao = scanner.nextLine().trim();  // trim depois de nextline é usado para remover espaços em branco

            System.out.print("Duração em minutos: ");
            int duracaoMin = Integer.parseInt(scanner.nextLine().trim());

            System.out.print("Data de início (dd/MM/yyyy): ");

            DateFormatterForConsole dateFormatterForConsole = new DateFormatterForConsole();
            String dataDigitada = dateFormatterForConsole.lerData();

            // Manipulador de string de data
            DateFormatter dateFormatter = new DateFormatter();

            String dataTreino="";
            // Formata a data de nascimento para o formato "dd/MM/yyyy"
            if (dataDigitada != null) {
                    dataTreino = dateFormatter.formatDateForDB(dataDigitada);
                    } // if (data_db != null) {
                    
            // Montagem do SQL de inserção
            String strSQL = ""
                + "INSERT INTO treinos "
                + "(aluno_id, tipo_treino, descricao, duracao_minutos, data_inicio) "
                + "VALUES (?, ?, ?, ?, ?)";

                // Conecta ao banco de dados usando o método conecta da classe UsuarioDao
                connection = UsuarioDao.conecta();


            // Cria um objeto PreparedStatement para executar o SQL de inserção
            prepareStatement = connection.prepareStatement(strSQL, Statement.RETURN_GENERATED_KEYS);

            // Substitui os "?" da string SQL pelos valores dos parâmetros
            prepareStatement.setInt   (1, alunoId);
            prepareStatement.setString(2, tipoTreino);
            prepareStatement.setString(3, descricao);
            prepareStatement.setInt   (4, duracaoMin);
            prepareStatement.setString(5, dataTreino);

            // 4) Execução e verificação de linhas afetadas
            int linhas = prepareStatement.executeUpdate();
            if (linhas == 0) {
                System.err.println("Falha ao inserir treino: nenhuma linha afetada.");
                return;
            }

            
            // Recupera o ID gerado pelo banco (auto-increment)
            resultSetKeys = prepareStatement.getGeneratedKeys();
            if (resultSetKeys.next()) {
                idGerado = resultSetKeys.getInt(1);
                System.out.println("Treino cadastrado com sucesso! ID = " + idGerado);
            }

            // Cria um objeto ConsultaAlunos para executar a consulta SQL para verificar se os dados foram inseridos corretamente
            ConsultaTreinos consulta = new ConsultaTreinos();
            
            // Chama o método Consulta() da classe ConsultaAlunos para exibir os dados inseridos
            consulta.Consulta("id = "+idGerado);



        } catch (SQLException sqle) {
            System.err.println("Erro de banco ao cadastrar treino: " + sqle.getMessage());
        } catch (NumberFormatException nfe) {
            System.err.println("Entrada numérica inválida: " + nfe.getMessage());
        } finally {
            // 6) Fecha todos os recursos em ordem reversa de abertura
            try { if (resultSetKeys    != null) resultSetKeys.close();    } catch (SQLException ignore ) {}
            try { if (prepareStatement != null) prepareStatement.close(); } catch (SQLException ignore ) {}
            try { if (connection       != null) connection.close();       } catch (SQLException ignore ) {}
        }
    }
}

