// cleancode
// duckcode    plano vitalicio

package com.programacaojava.academia.ui;

import java.io.Console;
import java.util.Scanner;

import com.programacaojava.academia.security.Autenticador;

public class TelaLogin {
        
    // usuario e senha armazenam as credenciais do usuário
    private String usuario;
    private String senha;
    
    // o método apresentarTelaLogin é responsável por exibir a tela de login
    // e solicitar as credenciais do usuário
    public boolean apresentarTelaLogin(Scanner scanner) {
          
            // Cria um objeto Scanner para ler a entrada do usuário
            // O Scanner é usado para ler a entrada do usuário no terminal
            // Scanner scanner = new Scanner(System.in);

            // Método para apresentar a tela de login
            // Aqui você pode implementar a lógica para exibir a tela de login, como solicitar nome de usuário e senha
            System.out.println("Bem-vindo ao sistema de login!");
            System.out.println("Por favor, insira suas credenciais para continuar.");

            // Solicita o nome de usuário
            System.out.print("Nome de usuário: ");
            usuario = scanner.nextLine();




            // Verifica se o console está disponível
            // O console é usado para ler a senha de forma segura, sem exibir os caracteres digitados
            Console console = System.console();
            if (console == null) {
                    System.err.println("Nenhum console disponível. Rode o programa no terminal, não na IDE.");
                    System.exit(1);
                    } // if (console == null) {

            // System.out.println("console.charset(): " + console.charset());
            // Lê a senha sem mostrar no terminal
            char[] senhaChars = console.readPassword("Digite sua senha: ");
            senha = new String(senhaChars);


            // retorna o resultado do método logar, que verifica as credenciais
            // Se as credenciais forem válidas, o usuário será logado com sucesso
            // Chama a autenticação de outra classe
            Autenticador autenticador = new Autenticador();
            return autenticador.autenticar(usuario, senha);


        } // public void apresentarTelaLogin() {

} // public class TelaLogin 
