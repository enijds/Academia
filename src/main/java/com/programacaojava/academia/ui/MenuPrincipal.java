
package com.programacaojava.academia.ui;

import java.util.Scanner;

//import com.programacaojava.academia.App;




public class MenuPrincipal {

    
    // Construtor da classe MenuPrincipal
    public MenuPrincipal() {}

    // Método para controlar o menu principal
    public void controleMenu() throws Throwable {

                // Cria uma instancia da classe Scanner
                // cria um Scanner para ler a entrada do usuário
                // O Scanner é usado para ler a entrada do usuário no terminal
                Scanner scanner = new Scanner(System.in);

                // Método para controlar o menu principal
                MenuPrincipal menuPrincipal = new MenuPrincipal();

                // Cria um objeto TerminalTexto para manipular o terminal
                TerminalTexto terminal = new TerminalTexto();

                // laço para repetir o menu principal
                // do-while para manter o menu ativo até que o usuário escolha sair
                do { 
                        
                                // Cria um objeto TerminalTexto e limpa o console
                                terminal.LimpaConsole();
                                        
                                // Exibe o menu
                                menuPrincipal.exibirMenu();
                                
                                //Aguarda a opção do usuário	
                                int opcao = scanner.nextInt();

                                // define a escolha do menu principal
                                menuPrincipal.opcoes(opcao);
                                
                                

                                // Verifica se a opção escolhida é 9 (Sair)
                                // Se for, exibe uma mensagem de saída e encerra o loop
                                if (opcao == 9) {
                                        System.out.println("Saindo do programa...");
                                        break;
                                } // if (opcao == 9) {
                                
                        } while (true);    // Fim do laço do-while  

                // Fecha o Scanner para liberar os recursos
                scanner.close();
                
                } // public void controleMenu() {


        public void exibirMenu() {
                
                // Mensagens do menu principal
                System.out.println("Menu Principal"                     );
                System.out.println("0. Trabalhar pela interface Grafica");
                System.out.println("1. Cadastrar Aluno"                 );
                System.out.println("2. Listar Alunos"                   );
                System.out.println("3. Editar Aluno"                    );
                System.out.println("4. Excluir Aluno"                   );
                System.out.println("5. Cadastrar Treinos"               );
                System.out.println("6. Listar Treinos"                  );
                System.out.println("7. Editar Treinos"                  );
                System.out.println("8. Excluir Treinos"                 );
                System.out.println("9. Sair"                            );
                System.out.println("Escolha uma opção: "                );

                } // public void exibirMenu() {


    public void opcoes(int opcao) throws Throwable {

        // 3) Lê a opção escolhida pelo usuário   
        switch (opcao) {

                // 
                case 2  -> { ConsultaAlunos consultaAlunos   = new ConsultaAlunos()  ;consultaAlunos.Consulta();       }// Chama o método de consulta para listar as alunos}
                case 6  -> { ConsultaTreinos consultaTreinos = new ConsultaTreinos() ;consultaTreinos.Consulta();      }// Chama o método de consulta para listar os treinos}
                case 1  -> { CadastraAluno cadastraAluno     = new CadastraAluno()   ;cadastraAluno.cadastraAluno();   }
                case 5  -> { CadastraTreino cadastraTreino   = new CadastraTreino()  ;cadastraTreino.cadastraTreino(); }
                case 0  -> { System.out.println("Trabalhando pela interface Gráfica..."          );}
                case 3  -> { System.out.println("Editando Aluno..."                              );}
                case 4  -> { System.out.println("Excluindo Aluno..."                             );}
                case 7  -> { System.out.println("Editando Treinos..."                            );}
                case 8  -> { System.out.println("Excluindo Treinos..."                           );}
                case 9  -> { System.out.println("Saindo do programa..."); System.exit(0);   }
                default ->   System.out.println("Opção inválida. Tente novamente."               );

                        
                } // switch (opcao) {
        // Pausa o programa para que o usuário possa ver a saída
        System.out.println("Pressione Enter para continuar...");

        // Cria um Scanner para ler a entrada do usuário
        Scanner scanner = new Scanner(System.in);
        
        // usando o scanner para pausar
        String pausa = scanner.nextLine();

    } // public void opcoes(int opcao) {
    
} // public class MenuPrincipal {



    // CadastrarAluno
    // ListarAlunos
    // EditarAluno
    // ExcluirAluno
    // CadastrarTreinos
    // ListarTreinos
    // EditarTreinos
    // ExcluirTreinos