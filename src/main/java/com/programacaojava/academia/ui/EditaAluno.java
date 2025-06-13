package com.programacaojava.academia.ui;

import java.io.Console;
import java.io.IOException;
import java.io.Reader;

public class EditaAluno {


    public static void PesquisaAluno() throws IOException{

            // Verifica se o console está disponível
            Console console = System.console();

            // Cria um objeto TerminalTexto para manipular o terminal
            TerminalTexto terminal = new TerminalTexto();
            
            // Carrega classe no objeto consultaAlunos
            ConsultaAlunos consultaAlunos = new ConsultaAlunos();
        
            // Cria um StringBuilder para armazenar a data digitada
            StringBuilder stringBuilder = new StringBuilder();
            
            // vaidador de entrada de dados
            consultaAlunos.Consulta(stringBuilder.toString());

            System.out.print( 
                "\nQuantidade de Resultados: " + consultaAlunos.getiContagemResultados() 
              + "\nId Unico para pesquisa: "   + consultaAlunos.getIdAluno() 
              + "\nDigite para pesquisar: "    + stringBuilder.toString() 
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

                    // Se for um dígito e o tamanho do StringBuilder for menor que 6, adiciona o dígito ao StringBuilder
                    //else if ( Character.isDigit(cchar) && stringBuilder.length() < 6 ) stringBuilder.append(cchar);
                    
                    // Para qualquer outra situação, "aplica" o Continue e retorna ao inicio do while 
                    // else continue;
                    else stringBuilder.append(cchar);
                    
                    // vaidador de entrada de dados
                    consultaAlunos.Consulta(stringBuilder.toString());
                    



                    
                    System.out.print( 
                      "\nQuantidade de Resultados: " + consultaAlunos.getiContagemResultados() 
                    + "\nId Unico para pesquisa: " + consultaAlunos.getIdAluno() 
                    + "\nDigite para pesquisar: " + stringBuilder.toString() 
                                      );

                    
                    } // while ((ichar = reader.read()) != -1)

            } // public void PesquisaAluno(){

        // main para teste de execução dentro da classe
        public static void main(String[] args) throws IOException {
            
           // EditaAluno editaAluno = new EditaAluno();
            EditaAluno.PesquisaAluno();
            
        } // public static void main(String[] args) {
    
    














} // public class EditaAluno
