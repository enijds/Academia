package com.programacaojava.academia.util;

import java.io.Console;
import java.io.Reader;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;



public class DateFormatterForConsole {

    // Constantes para facilitar a leitura do codigo
    private static final int DIGITOS_DATA = 6;
    // Atributos da classe
    private String data_nascimento_formatada;
    private String verifica_data_digitada;

    // Método teste para ler uma data do console para essa classe
    public static void main(String[] args) throws Exception {

        // Cria uma instância da classe DateFormatterForConsole
        DateFormatterForConsole formatter = new DateFormatterForConsole();  

        formatter.lerData();
        }


    public String lerData() throws Exception {

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
            while ((ichar = reader.read()) != -1) {

                // Converte o caractere lido para char e verifica se é uma quebra de linha ou retorno de carro
                char cchar = (char) ichar;

                // teste cchar para verificar se é uma quebra de linha ou retorno de carro
                if ( cchar == '\n' || cchar == '\r' ) break;

                // teste cchar para verificar se é um backspace, dígito ou outro caractere
                // Se for backspace, remove o último caractere do StringBuilder
                else if ( cchar == '\b' && stringBuilder.length() > 0 ) stringBuilder.setLength(stringBuilder.length() - 1);

                // Se for um dígito e o tamanho do StringBuilder for menor que 6, adiciona o dígito ao StringBuilder
                else if ( Character.isDigit(cchar) && stringBuilder.length() < 6 ) stringBuilder.append(cchar);

                // Para qualquer outra situação, "aplica" o Continue e retorna ao inicio do while 
                else continue;

                // vaidador de entrada de dados
                strMensagem = validateDayMonthIncremental(stringBuilder);

                // Formata a data enquanto o usuário digita, toString do StringBuilder é usado para obter a string atual
                String formatado = formatar(stringBuilder.toString());

                // Exibe a data formatada no console
                printFormatAs(formatado, strMensagem);
                
            }

            // Formata a data enquanto o usuário digita
             data_nascimento_formatada = CorrigeAno(stringBuilder.toString());
            // data_nascimento_formatada = CorrigeAno(verifica_data_digitada);

            // Exibe a data formatada no console
            System.out.println(data_nascimento_formatada);

            // Retorna a string formatada
            return data_nascimento_formatada;

    }

    // formata dado para apresentação
    private static String formatar(String stringParametro) {

        // Carrega objero StringBuilder para construir a string formatada
        // a classe StringBuilder contem o método append para adicionar caracteres
        StringBuilder stringRetornada = new StringBuilder();

        // Se a string não tiver 8 caracteres, retorna uma string vazia
        for (int i = 0; i < stringParametro.length(); i++) {

            // Verifica se nas posições 2 e 4 deve ser adicionado uma barra
            if (i == 2 || i == 4) stringRetornada.append('/');

            // Adiciona o caractere atual da string original ao StringBuilder
            stringRetornada.append(stringParametro.charAt(i));
        }

        // Retorna a string formatada
        // A string formatada é retornada no formato DD/MM/YYYY
        return stringRetornada.toString();
        //return formato;
    }

    // Método para formatar a data e atualizar a saída do console
    private static void printFormatAs(String stringContruida, String stringMensagem) {

        // Limpa a linha atual do console
        System.out.print("\rData (DD/MM/YY): __/__/__                                                            ");

        // Atualiza a saída do console
        System.out.flush();

        // Print a string formatada
        System.out.print("\rData (DD/MM/YY): " + stringContruida + stringMensagem);

        // Garante que a saída seja atualizada imediatamente
        System.out.flush();

    } // 

    private static String CorrigeAno(String stringParametro) {

        // Limpa a linha atual do console
        System.out.print("\r                                                       ");
        System.out.print("\rData: ");

        // Carrega objero StringBuilder para construir a string formatada
        // a classe StringBuilder contem o método append para adicionar caracteres
        StringBuilder stringRetornada = new StringBuilder();
        int length = stringParametro.length();


        // Obtem os ultimos dois digitos do ano corrente
        LocalDate currentDate    = LocalDate.now(); // Data atual do sistema
        String currentYear       = String.valueOf(currentDate.getYear()); // Ex.: "2025"
        String lastTwoDigitsYear = currentYear.substring(currentYear.length() - 2); 
        
        // Caso de 6 caracteres: DDMMAA -> DD/MM/20AA
        if (length == 6) {

                // constroi a substring, insere os dias e /
                stringRetornada.append(stringParametro.substring(0, 2)); // Dia
                stringRetornada.append('/');

                // insere o mes e barra
                stringRetornada.append(stringParametro.substring(2, 4)); // Mês
                stringRetornada.append('/');
                                
                // Converte os últimos dois dígitos para inteiros para comparação
                int inputYearDigits   = Integer.parseInt(stringParametro.substring(4, 6)); // Ex.: 05
                int currentYearDigits = Integer.parseInt(lastTwoDigitsYear); // Ex.: 25

                // Testa se o ano é maior que o ano corrente
                if (inputYearDigits > currentYearDigits) {
                        stringRetornada.append("19"); // Prefixo do ano       
                    } else {
                        stringRetornada.append("20"); // Prefixo do ano
                    }
                
                stringRetornada.append(stringParametro.substring(4, 6)); // Ano (AA)
            }
        else {
            
            // Caso não tenha sido digitado 6

            // Padrão: dd = dia (2 dígitos), MM = mês (2 dígitos!), yy = ano (2 dígitos)
            DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yy");

            // Formata a Localdate hoje conforme a DateTimeFormatter fmt
            stringRetornada.append( currentDate.format(fmt) );

        }

        // Retorna a string formatada. A string formatada é retornada no formato DD/MM/YYYY
        return stringRetornada.toString();
        //return formato;
        }

    //
    //Realiza a validação incremental de dia e mês conforme o usuário digita,
    //removendo o último dígito caso seja inválido e retornando mensagem de erro apropriada.
    //    //Formato parcial esperado: ddMM (dia e mês). 
    //Ex.:  
    //  - len == 1: primeiro dígito do dia (1–3)  
    //  - len == 2: dia completo (01–31)  
    //  - len == 3: primeiro dígito do mês (0–1)  
    //  - len == 4: mês completo (01–12)  
    //    //@param stringBuilder StringBuilder contendo apenas dígitos já digitados (parcial ou completo)
    //@return mensagem de erro; string vazia se não houver erro
    //


    private static String validateDayMonthIncremental(StringBuilder stringBuilder) {
        int len = stringBuilder.length();
        int daysPart ;
        int monthsPart ;
        String errorMessage = "";

        // ———————————————————————————————————————————————————————————
        // 1) Validação do primeiro dígito do dia (len == 1):
        //    somente '1', '2' ou '3' são permitidos como primeiro caractere
        // ———————————————————————————————————————————————————————————
        if (len == 1) {
            char first = stringBuilder.charAt(0);
            if ( first > '3') {
                stringBuilder.deleteCharAt(0);                       // remove dígito inválido
                return "__/__/__ Dígito inicial de dia inválido!";
            }
        }

        // ———————————————————————————————————————————————————————————
        // 2) Validação do dia completo (len == 2): 01–31
        // ———————————————————————————————————————————————————————————
        if (len == 2) {
            daysPart = Integer.parseInt(stringBuilder.substring(0, 2));
            if (daysPart < 1 || daysPart > 31) {
                stringBuilder.deleteCharAt(1);                       // remove segundo dígito inválido
                return "_/__/__ Dia inválido!";
            }
        }

        // ———————————————————————————————————————————————————————————
        // 3) Validação do primeiro dígito do mês (len == 3):
        //    somente '0' ou '1' são permitidos como primeiro caractere de mês
        // ———————————————————————————————————————————————————————————
        if (len == 3) {
            char monthFirst = stringBuilder.charAt(2);
            if (monthFirst != '0' && monthFirst != '1') {
                stringBuilder.deleteCharAt(2);                       // remove dígito inválido
                return "/__/__ Dígito inicial de mês inválido!";
            }
        }

        // ———————————————————————————————————————————————————————————
        // 4) Validação do mês completo (len == 4): 01–12
        // ———————————————————————————————————————————————————————————
        if (len == 4) {
            monthsPart = Integer.parseInt(stringBuilder.substring(2, 4));
            if (monthsPart < 1 || monthsPart > 12) {
                stringBuilder.deleteCharAt(3);                       // remove segundo dígito inválido
                return "_/__ Mês inválido!";
            }
        }

        // ———————————————————————————————————————————————————————————
        // 5) Em qualquer outro caso (len ≠ 1,2,3,4) não há validação adicional
        //    — por exemplo, para formato mais longo ou quando já se lida com ano
        // ———————————————————————————————————————————————————————————

        return errorMessage;
    }




} // public class DateFormatterForConsole {


























/*







// if (stringParametro.length() == 2 AND Integer.parseInt(stringParametro.substring(1, 2) > 31)

private static String previsaeeeeeo(String stringParametro) {
    
    // Verifica se a entrada é nula ou inválida
    if (stringParametro == null ) {
        return ""; // Retorna vazio se não tem 2, 6 ou 8 dígitos
        }

    // Carrega StringBuilder para construir a string formatada
    StringBuilder stringRetornada = new StringBuilder();
    int length = stringParametro.length();

    // Obtém a data atual para mês e ano (ex.: 11/06/2025)
    //LocalDate currentDate = LocalDate.of(2025, 6, 11); // Data fixa para consistência
    //String currentMonth = String.format("%02d", currentDate.getMonthValue()); // Ex.: "06"
    //String currentYear = String.valueOf(currentDate.getYear()); // Ex.: "2025"

    LocalDate currentDate    = LocalDate.now(); // Data atual do sistema
    String currentYear       = String.valueOf(currentDate.getYear()); // Ex.: "2025"
    String lastTwoDigitsYear = currentYear.substring(currentYear.length() - 2); 

    // Caso de 2 caracteres: DD -> DD/MM/AAAA (mês e ano atuais)
    if (length == 1) {
            stringRetornada.append('0'            );          
            stringRetornada.append(stringParametro  ); // Dia 
            stringRetornada.append('/'            );           
            stringRetornada.append(currentMonth     ); // Mês atual
            stringRetornada.append('/'            );         
            stringRetornada.append(currentYear      ); // Ano atual
    }

    // Caso de 2 caracteres: DD -> DD/MM/AAAA (mês e ano atuais)
    if (length == 2) {
        // Primeiro caractere menor ou igual que 3 significa dias de 01 a até 31
        if (stringParametro.charAt(0) <= '3') {
            stringRetornada.append(stringParametro); // Dias 01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31
            stringRetornada.append('/'          );
            stringRetornada.append(currentMonth   ); // Mês atual
            stringRetornada.append('/'          );
            stringRetornada.append(currentYear    ); // Ano atual
            } 
        // Primeiro Caracter > 3, segundo > 2: significa dia 4,5,6,7,8,9 e o mes pode ser 1,2,3,4,5,6,7,8,9
        else if ( stringParametro.charAt(0) > '3') {
            stringRetornada.append('0'                          ); 
            stringRetornada.append(stringParametro.charAt(0)); // Dia
            stringRetornada.append('/'                          );
            stringRetornada.append('0'                          ); 
            stringRetornada.append(stringParametro.charAt(1)); // Mês atual
            stringRetornada.append('/'                          );
            stringRetornada.append(currentYear                    );
            }
        else {   
            return ""; // Dia inválido (ex.: 39)
        }

    }


// Caso de 2 caracteres: DD -> DD/MM/AAAA (mês e ano atuais)
    if (length == 3) {
        // Primeiro caractere menor ou igual que 3 significa dias de 01 a até 31
        if (stringParametro.charAt(0) <= '3') {
            stringRetornada.append(stringParametro); // Dias 01,02,03,04,05,06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,25,26,27,28,29,30,31
            stringRetornada.append('/'          );
            stringRetornada.append(currentMonth   ); // Mês atual
            stringRetornada.append('/'          );
            stringRetornada.append(currentYear    ); // Ano atual
            } 
        // Primeiro Caracter > 3, segundo > 2: significa dia 4,5,6,7,8,9 e o mes pode ser 1,2,3,4,5,6,7,8,9
        else if ( stringParametro.charAt(0) > '3') {
            stringRetornada.append('0'                          ); 
            stringRetornada.append(stringParametro.charAt(0)); // Dia
            stringRetornada.append('/'                          );
            stringRetornada.append('0'                          ); 
            stringRetornada.append(stringParametro.charAt(1)); // Mês atual
            stringRetornada.append('/'                          );
            stringRetornada.append(currentYear                    );
            }
        else {   
            return ""; // Dia inválido (ex.: 39)
        }
    }

    // Caso de 6 caracteres: DDMMAA -> DD/MM/20AA
    else if (length == 6) {
        stringRetornada.append(stringParametro.substring(0, 2)); // Dia
        stringRetornada.append('/');
        stringRetornada.append(stringParametro.substring(2, 4)); // Mês
        stringRetornada.append('/');
        

        if (stringParametro.substring(4, 6) > lastTwoDigitsYear ) {
                stringRetornada.append("19"); // Prefixo do ano       
            } else {
                stringRetornada.append("20"); // Prefixo do ano
                }
        
        stringRetornada.append(stringParametro.substring(4, 6)); // Ano (AA)
    }
    // Caso de 8 caracteres: DDMMAAAA -> DD/MM/AAAA
    else if (length == 8) {
        stringRetornada.append(stringParametro.substring(0, 2)); // Dia
        stringRetornada.append('/');
        stringRetornada.append(stringParametro.substring(2, 4)); // Mês
        stringRetornada.append('/');
        stringRetornada.append(stringParametro.substring(4, 8)); // Ano (AAAA)
    }

    // Retorna a string formatada no formato DD/MM/YYYY
    return stringRetornada.toString();



           
                // Comprimento do texto
                //int comprimentoStringBuilder = stringBuilder.length();

                // Verifica se o tamanho do StringBuilder é maior que 2
                int idias   = 0;
                int imeses  = 0;

                // Se o tamanho do StringBuilder for maior que 2, obtem os dois primeiros dígitos
                if ( stringBuilder.length() == 1 ) idias  = Integer.parseInt(stringBuilder.substring(0, 1)) ;
                if ( stringBuilder.length() == 2 ) { 
                                if ( stringBuilder.charAt(0) <= '3' ) { idias = Integer.parseInt(stringBuilder.substring(0, 2)) ; } 
                                else {idias = Integer.parseInt(stringBuilder.substring(0, 1)) ; stringBuilder.setLength(stringBuilder.length() - 1) ;}
                                }
                
                
                if ( stringBuilder.length() == 3 ) { 
                                idias = Integer.parseInt(stringBuilder.substring(0, 2));
                                if ( idias <= 31 ) { 
                                        imeses = Integer.parseInt(stringBuilder.substring(2, 3)) ; 
                                } else { 
                                        idias = Integer.parseInt(stringBuilder.substring(0, 2)) ; 
                                        stringBuilder.setLength(stringBuilder.length() - 1) ;
                                        }
                                }
                if ( stringBuilder.length() == 4 ) { 
                                if ( stringBuilder.charAt(2) <= '1' ) { imeses = Integer.parseInt(stringBuilder.substring(2, 4)) ; } 
                                else {imeses = Integer.parseInt(stringBuilder.substring(2, 3)) ; stringBuilder.setLength(stringBuilder.length() - 1) ;}
                                }
                if ( stringBuilder.length() == 5 ) imeses = Integer.parseInt(stringBuilder.substring(2, 4)) ; 
                
                     if ( stringBuilder.length() == 1 && idias  > 3  ) { strMensagem = "_/__/__ Dia inválido!"; }
                else if ( stringBuilder.length() == 2 && idias  > 31 ) { strMensagem = "/__/__ Dia inválido!" ; }
                else if ( stringBuilder.length()  > 2 && idias  > 31 ) { strMensagem = "/__/__ Dia inválido!" ; stringBuilder.setLength(stringBuilder.length() - 1) ; } 
                else if ( stringBuilder.length() == 3 && imeses > 1  ) { strMensagem = "_/__ Mês inválido!"   ; }   
                else if ( stringBuilder.length() == 4 && imeses > 12 ) { strMensagem = "/__ Mês inválido!"    ; }
                else if ( stringBuilder.length()  > 4 && imeses > 12 ) { strMensagem = "/__ Mês inválido!"    ; stringBuilder.setLength(stringBuilder.length() - 1) ; }                              
                else                                                   { strMensagem = ""                     ; }

                












*/