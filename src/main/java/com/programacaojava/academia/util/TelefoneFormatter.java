package com.programacaojava.academia.util;

import java.io.Console;
import java.io.Reader;


public class TelefoneFormatter {

    // Atributos da classe
    private String strTelefoneFormatter;

    // Constantes para facilitar a leitura
    private static final char KEY_ENTER     = '\n';
    private static final char KEY_BACKSPACE = '\b';
    private static final char KEY_RETURN    = '\r';
    private static final int  DIGITOS_NUMERO_TELEFONE = 11;

    // Método teste para ler uma data do console para essa classe
    public static void main(String[] args) throws Exception {

        // Cria uma instância da classe DateFormatterForConsole
        TelefoneFormatter formatter = new TelefoneFormatter();  

        // Chama o método lerTelefone
        formatter.lerTelefone();
        }


    public String lerTelefone() throws Exception {

            // Verifica se o console está disponível
            Console console = System.console();

            // Se o console não estiver disponível, exibe uma mensagem de erro
            if (console == null) {
                System.err.println("Console não disponível!");
                return null;
                } // if (console == null) {

            // Se o console estiver disponível, lê a data do usuário    
            Reader reader = console.reader();

            // Cria um StringBuilder para armazenar a data digitada
            StringBuilder stringBuilder = new StringBuilder();

            // Exibe uma mensagem solicitando a data no formato DDMMYYYY
            printFormatAs("","");

            // ichar interiro para armazenar o caractere lido
            int ichar;
            String strMensagem;

            // Lê os caracteres do console até encontrar uma quebra de linha ou atingir o tamanho máximo
            // A condição do while diferente -1 desfaz o loop quando o caractere lido for uma quebra de linha
            while ((ichar = reader.read()) != -1) {

                // Converte o caractere lido para char
                char cchar = (char) ichar;

                // teste cchar para verificar se é uma quebra de linha ou retorno de carro, caso seja verdadeiro, encerra o loop
                if ( cchar == KEY_RETURN || cchar == KEY_ENTER ) break;

                // teste cchar para verificar se é um backspace, dígito ou outro caractere, se for backspace, remove o último caractere do StringBuilder
                else if ( cchar == KEY_BACKSPACE && stringBuilder.length() > 0 ) stringBuilder.setLength(stringBuilder.length() - 1);

                // Se for um dígito e o tamanho do StringBuilder for menor que 11, adiciona o dígito ao StringBuilder
                else if ( Character.isDigit(cchar) && stringBuilder.length() < DIGITOS_NUMERO_TELEFONE ) stringBuilder.append(cchar);

                // Para qualquer outra situação, "aplica" o Continue e retorna ao inicio do while 
                else continue;
               
                // strMensagem = validateDayMonthIncremental(stringBuilder);
                strMensagem = "";

                // Formata a data enquanto o usuário digita, toString do StringBuilder é usado para obter a string atual
                strTelefoneFormatter = formatar(stringBuilder.toString());

                // Exibe a data formatada no console
                printFormatAs(strTelefoneFormatter, strMensagem);
                
            }

            // Formata a data enquanto o usuário digita
            //TelefoneFormatter = formatado ; //CorrigeAno(stringBuilder.toString());

            // Exibe a data formatada no console
            System.out.print("\rTelefone: " + strTelefoneFormatter + "\n");

            // Retorna a string formatada
            return strTelefoneFormatter;

    }

    private static String formatar(String stringParametro) {

        // Normaliza a entrada para 11 dígitos, preenchendo com '0' à direita
        // faz uma cópia da string original
        StringBuilder normalizedInput = new StringBuilder(stringParametro);

        // teste o comprimento da string se for menor que 11, preenche com '0'
        while (normalizedInput.length() < 11) {
            normalizedInput.append('0'); // Preenche com zeros até 11 dígitos
            }

        // Carrega objero StringBuilder para construir a string formatada
        // a classe StringBuilder contem o método append para adicionar caracteres
        StringBuilder stringRetornada = new StringBuilder();

        // Se a string não tiver 11 caracteres, retorna uma string vazia
        for (int i = 0; i < normalizedInput.length(); i++) {

            // Verifica se nas posições 2 e 4 deve ser adicionado uma barra
            if (i == 0 ) stringRetornada.append( "("  ) ;
            if (i == 2 ) stringRetornada.append( ") " ) ;
            if (i == 7 ) stringRetornada.append( "-"  ) ;

            // Adiciona o caractere atual da string original ao StringBuilder
            stringRetornada.append(normalizedInput.charAt(i));
        }

        // Retorna a string formatada
        // A string formatada é retornada no formato DD/MM/YYYY
        return stringRetornada.toString();
        //return formato;
    }

    // Método para formatar a data e atualizar a saída do console
    private static void printFormatAs(String stringContruida, String stringMensagem) {

        // Limpa a linha atual do console
        System.out.print("\rTelefone: (00) 00000-0000                                                            ");
        // System.out.print("\rTelefone:                                                           ");

        // Atualiza a saída do console
        System.out.flush();

        // Print a string formatada
        System.out.print("\rTelefone: " + stringContruida + stringMensagem);

        // Garante que a saída seja atualizada imediatamente
        System.out.flush();

    } 

}