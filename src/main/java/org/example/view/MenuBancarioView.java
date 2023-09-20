package org.example.view;

import org.example.model.InputUser;
import org.example.service.ClienteService;
import org.example.service.ContaBancariaService;
import org.example.service.TransacaoService;

public class MenuBancarioView {
    InputUser inputUser=new InputUser();
    ClienteService clienteService=new ClienteService();
    ContaBancariaService contaBancariaService=new ContaBancariaService();
    TransacaoService transacaoService=new TransacaoService();
    public void menuBancario() {
        System.out.println("""
                
                0. Menu Principal |\s
                1. Menu Cliente |\s
                2. Menu Conta|\s
                3. Menu Transações|\s
                """
        );
    }
    public void caseBancario() {
        int opcao;
        do {
            menuBancario();
            opcao=inputUser.readIntFromUser("Qual opção deseja:");

            switch (opcao) {
                case 1 ->caseCliente();
                case 2 ->caseConta();
                case 3 ->caseTransacao();
                case 0 ->new MenuView();
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
    public void menuCliente() {
        System.out.println("""
                
                0. Menu Bancario |\s
                1. Deletar um Cliente |\s
                2. Atualizar informações cliente|\s
                3. Listar Clientes|\s
                """
        );
    }
    public void caseCliente() {
        int opcao;
        do {
            menuCliente();
            opcao = inputUser.readIntFromUser("Qual opção deseja:");

            switch (opcao) {
                case 1 -> {
                    clienteService.listarClientes();
                    int id =inputUser.readIntFromUser("Qual o id do cliente que deseja deletar:");
                    clienteService.deletarCliente(id);
                }
                case 2 -> {
                    clienteService.listarClientes();
                    int id =inputUser.readIntFromUser("Qual o id do cliente que deseja atualizar:");
                    String endereco =inputUser.readStringFromUser("Qual o novo endereço do cliente:");
                    clienteService.atualizarCliente(id,endereco);
                }
                case 3 -> clienteService.listarClientes();
                case 0 -> menuBancario();
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
    public void menuConta() {
        System.out.println("""
                
                0. Menu Bancario |\s
                1. Deletar um Conta |\s
                2. Listar Conta especifica|\s
                3. Listar Contas|\s
                """
        );
    }
    public void caseConta() {
        int opcao;
        do {
            menuConta();
            opcao = inputUser.readIntFromUser("Qual opção deseja:");

            switch (opcao) {
                case 1 -> {
                    contaBancariaService.listarConta();
                    int id =inputUser.readIntFromUser("Qual o id da conta que deseja deletar:");
                    contaBancariaService.deletarConta(id);
                }
                case 2 -> {
                    clienteService.listarClientes();
                    int id =inputUser.readIntFromUser("Qual o id do cliente que deeja ver a conta:");
                    contaBancariaService.listarContaPorId(id);
                }
                case 3 -> contaBancariaService.listarConta();
                case 0 -> menuBancario();
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
    public void menuTransacao() {
        System.out.println("""
                
                0. Menu Bancario |\s
                1. Listar Transações de uma conta especifica |\s
                2. Listar Contas de um cliente especifico|\s
                3. Listar Transações|\s
                """
        );
    }
    public void caseTransacao() {
        int opcao;
        do {
            menuTransacao();
            opcao = inputUser.readIntFromUser("Qual opção deseja:");

            switch (opcao) {
                case 1 ->{
                    contaBancariaService.listarConta();
                    int id =inputUser.readIntFromUser("Qual o id da conta que deseja ver as transações:");
                    transacaoService.listarTransacaoPorId(id);
                }
                case 2 -> {
                    clienteService.listarClientes();
                    int id =inputUser.readIntFromUser("Qual o id do cliente que deseja ver as contas:");
                    contaBancariaService.listarContaPorId(id);
                }
                case 3 -> transacaoService.listarTransacao();
                case 0 -> menuBancario();
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
}
