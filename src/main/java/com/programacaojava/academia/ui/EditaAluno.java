package com.programacaojava.academia.ui;

import java.io.Console;
import java.io.Reader;
import java.util.Scanner;

public class EditaAluno {


    /**
     * Aprendendo a utilizar o javadocs
     * @throws Exception 
     */
    public static void PesquisaAluno() throws Exception{

            // Verifica se o console está disponível
            Console console = System.console();

            // Cria um objeto TerminalTexto para manipular o terminal
            TerminalTexto terminal = new TerminalTexto();
            
            // Carrega classe no objeto consultaAlunos
            ConsultaAlunos consultaAlunos = new ConsultaAlunos();
        
            // Cria um StringBuilder para armazenar a data digitada
            StringBuilder stringBuilder = new StringBuilder();

            // Carrega a leitura do console para scanner
            Scanner scanner = new Scanner(System.in);

            // Carrega classes para alterar e exluir
            AlteraAluno alteraAluno = new AlteraAluno();
            ExcluiAluno excluiAluno = new ExcluiAluno();
            
            // vaidador de entrada de dados
            consultaAlunos.Consulta(stringBuilder.toString());

            System.out.print( 
                  "\nQuantidade de Resultados: " + consultaAlunos.getiContagemResultados() 
                + "\nDigite para pesquisar até filtrar um unico registro na tabela(Enter para sair): " + stringBuilder.toString() 
                );
            
            // Se o console estiver disponível, lê a data do usuário    
            Reader reader = console.reader();

            // ichar interiro para armazenar o caractere lido
            int ichar;
            
            // Lê os caracteres do console até encontrar uma quebra de linha ou atingir o tamanho máximo
            while ((ichar = reader.read()) != -1) {

                    // Cria um objeto TerminalTexto e limpa o console
                    terminal.LimpaConsole();
                    
                    // Converte o caractere lido para char e verifica se é uma quebra de linha ou retorno de carro
                    char cchar = (char) ichar;
                    
                    // teste cchar para verificar se é uma quebra de linha ou retorno de carro
                    if ( cchar == '\n' || cchar == '\r' ) break;

                    // teste cchar para verificar se é um backspace, dígito ou outro caractere
                    // Se for backspace, remove o último caractere do StringBuilder
                    else if ( cchar == '\b' && stringBuilder.length() > 0 ) stringBuilder.setLength(stringBuilder.length() - 1);

                    // Para qualquer outra situação, "aplica" o Continue e retorna ao inicio do while 
                    // else continue;
                    else stringBuilder.append(cchar);
                    
                    // vaidador de entrada de dados
                    consultaAlunos.Consulta(stringBuilder.toString());

                    // testa se resta apenas um registro consultado
                    if (consultaAlunos.getiContagemResultados() == 1 ){

                            System.out.print( 
                              "\nId Unico encontrado: " + consultaAlunos.getIdAluno() 
                            + "\nEscolha a opção abaixo: " 
                            + "\n   0 - Reinicia Consulta"
                            + "\n   1 - Para alterar aluno"
                            + "\n   2 - Para excluir aluno"
                            + "\n   3 - Retorna Menu"
                            + "\n"  );
                              

                            //ichar = reader.read();  // Lê um único caractere
                            
                            while ((ichar = reader.read()) != -1) {

                                    // Converte o caractere lido para char e verifica se é uma quebra de linha ou retorno
                                    cchar = (char) ichar;  
                                      
                                    if (cchar == '0') {
                                            // Se a opção for 0, limpa o console e reinicia a consulta
                                            stringBuilder.setLength(0);

                                            // Cria um objeto TerminalTexto e limpa o console
                                            terminal.LimpaConsole();

                                            // vaidador de entrada de dados
                                            consultaAlunos.Consulta(stringBuilder.toString());

                                            System.out.print( 
                                            "\nQuantidade de Resultados: " + consultaAlunos.getiContagemResultados() 
                                          + "\nDigite para pesquisar até filtrar um unico registro na tabela(Enter para sair): " + stringBuilder.toString() 
                                                      );
                                            break;
                                            } // if (cchar == '0') {
                                    else if ( cchar == '1' ) {
                                            System.out.println("getIdAluno" + consultaAlunos.getIdAluno());

                                            alteraAluno.atualizaCadastro(consultaAlunos.getIdAluno());

                                                                                         // Cria um objeto TerminalTexto e limpa o console
                                            terminal.LimpaConsole();

                                            // vaidador de entrada de dados
                                            consultaAlunos.Consulta(stringBuilder.toString());

                                            System.out.print( 
                                            "\nQuantidade de Resultados: " + consultaAlunos.getiContagemResultados() 
                                          + "\nDigite para pesquisar até filtrar um unico registro na tabela(Enter para sair): " + stringBuilder.toString() 
                                                      );

                                            break;
                                            } // if ( cchar == 1 ) {
                                    else if ( cchar == '2' ) {
                                            excluiAluno.excluirAlunoPorId(consultaAlunos.getIdAluno());

                                         
                                            
                                            // Se a opção for 0, limpa o console e reinicia a consulta
                                            stringBuilder.setLength(0);

                                             // Cria um objeto TerminalTexto e limpa o console
                                            terminal.LimpaConsole();

                                            // vaidador de entrada de dados
                                            consultaAlunos.Consulta(stringBuilder.toString());

                                            System.out.print( 
                                            "\nQuantidade de Resultados: " + consultaAlunos.getiContagemResultados() 
                                          + "\nDigite para pesquisar até filtrar um unico registro na tabela(Enter para sair): " + stringBuilder.toString() 
                                                      );

                                                      

                                            break;

                                            } // else if ( cchar == 2 ) {
                                    else if ( cchar == '3' ) {
                                            System.out.println("Retornando ao Menu Principal");
                                            return;
                                            } // else if ( opcao == 3 ) {
                                    else {
                                            System.out.print("\rOpção Inválida, tente novamente");
                                            }
                                    } // while ((ichar = reader.read()) != -1) {

                        } // if (consultaAlunos.getiContagemResultados() == 1 ){
                    else {
                          System.out.print( 
                            "\nQuantidade de Resultados: " + consultaAlunos.getiContagemResultados() 
                          + "\nDigite para pesquisar até filtrar um unico registro na tabela(Enter para sair): " + stringBuilder.toString() 
                                      );
                         } // else {

                    } // while ((ichar = reader.read()) != -1)

            } // public void PesquisaAluno(){




        // main para teste de execução dentro da classe
        public static void main(String[] args) throws Exception {
            
           // EditaAluno editaAluno = new EditaAluno();
            EditaAluno.PesquisaAluno();
            
        } // public static void main(String[] args) {
    
    














} // public class EditaAluno
