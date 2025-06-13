
package com.programacaojava.academia.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateFormatter {
    public String formatDate(String dateStr) {
        try {
            // Define o formato de entrada
            DateTimeFormatter inputFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
            // Define o formato de saída
            DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            // Converte a string para LocalDate
            LocalDate date = LocalDate.parse(dateStr, inputFormatter);
            // Formata para o novo padrão
            return outputFormatter.format(date);
        } catch (Exception e) {
            return dateStr; // Retorna original em caso de erro
        }
    }

    public String formatDateForDB(String dateStr) {
        try {
            // Define o formato de entrada
            DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            // Define o formato de saída
            DateTimeFormatter outputFormatter = DateTimeFormatter.ISO_LOCAL_DATE;

            // Converte a string para LocalDate
            LocalDate date = LocalDate.parse(dateStr, inputFormatter);
            // Formata para o novo padrão
            return outputFormatter.format(date);
        } catch (Exception e) {
            return dateStr; // Retorna original em caso de erro
        }
    }

    // Exemplo de uso com ResultSet
    public static void main(String[] args) {

        DateFormatter dateFormatter = new DateFormatter();
        
        String dataNascimento = "1990-01-01";
        String dataFormatada = dateFormatter.formatDate(dataNascimento);
        System.out.println(dataFormatada); // Saída: 01/01/1990

        dataNascimento = "03/08/2023";
        dataFormatada = dateFormatter.formatDateForDB(dataNascimento);
        System.out.println(dataFormatada); // Saída: 01/01/1990
    }
}
