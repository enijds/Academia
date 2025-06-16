/*

Sincronicação com o GitHub:


Convenções de nomenclatura em Java (nomes de variáveis, métodos, classes, constantes, pacotes e arquivos):
Arquivos (.java, PascalCase = classe pública)                       : PedidoVenda.java; ClienteDAO.java; ValidadorCpfTest.java
Booleanos (camelCase, prefixados com "is", "has", "can" ou "should"): boolean isAtivo; boolean hasPermissao; boolean canProcessar; boolean shouldAtualizar; boolean isConectado;
Classes de teste (PascalCase, terminam em "Test")                   : class ValidadorCpfTest {...}; class ProcessadorPagamentoTest {...}
Classes/Interfaces/Enums (PascalCase)                               : class PedidoVenda {...}; interface ClienteDAO {...}; enum StatusPedido {...}; class GestorFinanceiro {...}
Constantes (SCREAMING_SNAKE_CASE)                                   : public static final int MAX_RETRY_COUNT = 5; public static final String API_KEY = "xyz123"; public static final double GRAVIDADE_TERRA = 9.81
Exceções (PascalCase, terminam em "Exception")                      : class DadosInvalidosException {...}; class RedeIndisponivelException {...}
Genéricos (letras maiúsculas)                                       : class Fila<T> {...}; Map<K, V> mapearDados() {...}; class Repositorio<T, ID> {...}; interface Comparador<T> {...}; List<E> listaUsuarios; Map<String, List<Produto>> produtosPorCategoria; Optional<Double> resultadoProcessamento
Métodos (camelCase, verbos descritivos)                             : void calcularTotal() {...}; String buscarCliente(int id) {...}; int gerarRelatorioAnual() {...}; void atualizarEstoque(Produto item) {...}
Pacotes (lowercase, sem underscore)                                 : package com.empresa.produto.submodulo; package org.sistema.banco.servicos
Variáveis de instância (camelCase)                                  : private long idTransacao; protected double saldoAtual
Variáveis locais e parâmetros (camelCase)                           : int totalPreco; String nomeUsuario; float mediaNotas; boolean pedidoConfirmado


*/

package com.programacaojava.academia;

import java.util.Scanner;

import com.programacaojava.academia.ui.MenuPrincipal;
import com.programacaojava.academia.ui.TelaLogin;
import com.programacaojava.academia.ui.TerminalTexto;

// O nome do arquivo é Academia.java, então a classe também deve ser Academia.
public class Academia {
    
    // Classe principal do aplicativo, onde a execução começa.
    // Contém o método main, que é o ponto de entrada do programa.
    public static void main(String[] args) throws Exception, Throwable {
        
        // Instancia um objeto da classe Scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);

        // Cria uma instância da classe TerminalTexto
        TerminalTexto terminal = new TerminalTexto();
        terminal.codePageUTF8Console();
        terminal.LimpaConsole();

        // instancia um objeto da classe MenuPrincipal
        // que contém o método exibirMenu para exibir o menu principal
        MenuPrincipal menuPrincipal = new MenuPrincipal();

        // Cria uma instancia da classe Login
        // Chama o metodo apresentarTelaLogin
        TelaLogin login = new TelaLogin();

        // Apresenta a tela de login
        if (login.apresentarTelaLogin(scanner)) {
            
                // Se o login for bem-sucedido, exibe o menu principal
                menuPrincipal.controleMenu();
                }
            else {

                // Se o login falhar, exibe uma mensagem e encerra o aplicativo
                // Aqui você pode implementar a lógica para lidar com o login falho, como exibir uma mensagem de erro
                System.exit(0);
                } // if (login.apresentarTelaLogin()) {

        // Fecha o Scanner
        scanner.close();

    } // public static void main(String[] args) {
    

} // public class Academia {
