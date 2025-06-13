package com.programacaojava.academia.security;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class GerenciadorCredenciais {

    // Método para gerar o hash SHA-256 da senha
    // Este método recebe uma senha como entrada e retorna o hash SHA-256 correspondente
    public String generateSHA256Hash(String password) throws NoSuchAlgorithmException {

        // MessageDigest é usado para calcular o hash SHA-256 da senha
        // O método getInstance("SHA-256") cria uma instância do MessageDigest para o algoritmo SHA-256 
        MessageDigest digest = MessageDigest.getInstance("SHA-256");

        // O método digest.digest() calcula o hash da senha
        // A senha é convertida para bytes usando a codificação UTF-8
        // byte[] é como um array de bytes que representa o hash da senha
        // O resultado é um array de bytes que representa o hash SHA-256 da senha
        byte[] hashBytes = digest.digest(password.getBytes(StandardCharsets.UTF_8));

        // Converte o array de bytes em uma string hexadecimal
        // A string hexadecimal é uma representação legível do hash SHA-256
        // O StringBuilder é usado para construir a string hexadecimal de forma eficiente
        StringBuilder hexString = new StringBuilder();
        for (byte b : hashBytes) {
            String hex = Integer.toHexString(0xff & b);
            if (hex.length() == 1) hexString.append('0');
            hexString.append(hex);
        }
        return hexString.toString();
    } // public String generateSHA256Hash(String password) throws NoSuchAlgorithmException {
} // public class GerenciadorCredenciais
