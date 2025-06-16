package com.programacaojava.academia.ui;

import java.io.Console;
import java.io.Reader;
import java.util.Scanner;

public class EditaTreino {

    /**
     * Pesquisa dinamicamente na tabela treinos conforme o usuário digita,
     * e, quando restar apenas um resultado, oferece opções para
     * alterar ou excluir.
     */
    public static void pesquisaTreino() throws Exception {
        Console console = System.console();
        Reader reader = (console != null ? console.reader() : null);
        Scanner scanner = new Scanner(System.in);
        TerminalTexto terminal = new TerminalTexto();
        ConsultaTreinos consulta = new ConsultaTreinos();
        AlteraTreino  altera = new AlteraTreino();
        ExcluiTreino  exclui = new ExcluiTreino();

        StringBuilder filtro = new StringBuilder();

        // inicializa primeira consulta (tudo)
        consulta.Consulta(filtro.toString());
        System.out.print(
            "\nQuantidade de resultados: " + consulta.getCount() +
            "\nDigite para filtrar até restar 1(Enter para sair): " + filtro.toString()
        );

        int ichar;
        // vai lendo até Enter/Retorno de carro
        while ((ichar = (reader != null ? reader.read() : scanner.next().charAt(0))) != -1) {
            terminal.LimpaConsole();
            char cchar = (char) ichar;

            if (cchar == '\n' || cchar == '\r') {
                break;
            } else if (cchar == '\b' && filtro.length() > 0) {
                filtro.setLength(filtro.length() - 1);
            } else {
                filtro.append(cchar);
            }

            // reconsulta com novo filtro
            consulta.Consulta(filtro.toString());

            // se sobrar apenas 1, apresenta menu de ação
            if (consulta.getCount() == 1) {
                System.out.print(
                    "\nID único: " + consulta.getIdTreino() +
                    "\nEscolha: " +
                    "\n 0 - Reiniciar pesquisa" +
                    "\n 1 - Alterar treino" +
                    "\n 2 - Excluir treino" +
                    "\n 3 - Voltar ao menu" +
                    "\nOpção: "
                );

                // lê opção
                int opc;
                while ((ichar = reader.read()) != -1) {
                    cchar = (char) ichar;
                    if (cchar == '\n' || cchar == '\r') continue;

                    opc = Character.getNumericValue(cchar);
                    switch (opc) {
                        case 0:
                            filtro.setLength(0);
                            terminal.LimpaConsole();
                            consulta.Consulta(filtro.toString());
                            System.out.print(
                                "\nQuantidade de resultados: " + consulta.getCount() +
                                "\nDigite para filtrar até restar 1(Enter para sair): " + filtro.toString()
                            );
                            break;
                        case 1:
                            altera.atualizaTreino(consulta.getIdTreino());
                            terminal.LimpaConsole();
                            consulta.Consulta(filtro.toString());
                            System.out.print(
                                "\nQuantidade de resultados: " + consulta.getCount() +
                                "\nDigite para filtrar até restar 1(Enter para sair): " + filtro.toString()
                            );
                            break;
                        case 2:
                            exclui.excluirTreinoPorId(consulta.getIdTreino());
                            terminal.LimpaConsole();
                            consulta.Consulta(filtro.toString());
                            System.out.print(
                                "\nQuantidade de resultados: " + consulta.getCount() +
                                "\nDigite para filtrar até restar 1(Enter para sair): " + filtro.toString()
                            );
                            break;
                        case 3:
                            return;
                        default:
                            System.out.print("\rOpção inválida, tente novamente: ");
                    }
                    break;  // volta ao while principal
                }
            } else {
                // exibe contagem e prompt de filtro
                System.out.print(
                    "\nQuantidade de resultados: " + consulta.getCount() +
                    "\nDigite para filtrar até restar 1(Enter para sair): " + filtro.toString()
                );
            }
        }
    }

    public static void main(String[] args) throws Exception {
        EditaTreino.pesquisaTreino();
    }
}
