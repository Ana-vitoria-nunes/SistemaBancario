package org.example.view;

import org.example.model.InputUser;
import org.example.service.ContaBancariaService;
import org.example.service.TransacaoService;

import java.sql.Date;
import java.time.LocalDate;

public class MenuClienteView {
    InputUser inputUser = new InputUser();
    ContaBancariaService contaBancariaService = new ContaBancariaService();
    TransacaoService transacaoService=new TransacaoService();
    public void menuCliente() {
        System.out.println("""
                                
                0. Menu Principal |\s
                1. Criar uma conta |\s
                2. Fazer um saque |\s
                3. Fazer um deposito|\s
                4. Fazer uma transferencia |\s
                """
        );
    }
    public void caseCliente() {
        int opcao;
        do {
            menuCliente();
            opcao = inputUser.readIntFromUser("Qual opção deseja:");

            switch (opcao) {
                case 1 -> criarConta();
                case 2 -> fazerSaque();
                case 3 -> fazerDeposito();
                case 4 -> fazerTransferencia();
                case 0 -> new MenuView();
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
    public void criarConta() {
        int id = inputUser.readIntFromUser("Qual seu id de cliente: ");
        String tipo=inputUser.readStringFromUser("Qual vai ser o tipo da sua conta ex(conta corrente ou conta poupança):");
        contaBancariaService.adicionarContaAutomatica(id,tipo);
    }
    public void fazerDeposito() {
        int id = inputUser.readIntFromUser("Qual seu id da conta: ");
        double saldo = inputUser.readDobleFromUser("Qual o valor do deposito: ");
        contaBancariaService.fazerDeposito(id,saldo);
    }
    public void fazerSaque() {
        int id = inputUser.readIntFromUser("Qual seu id da conta: ");
        double saldo = inputUser.readDobleFromUser("Qual o valor do saque: ");
        contaBancariaService.fazerSaque(id,saldo);
    }
    public void fazerTransferencia() {
        int idOrigem = inputUser.readIntFromUser("Qual o id da sua conta: ");
        int idDestino = inputUser.readIntFromUser("Qual o id da conta de destino: ");
        double saldo = inputUser.readDobleFromUser("Qual o valor da tranferencia: ");
        transacaoService.adicionarTransacao(Date.valueOf(LocalDate.now()),saldo,idOrigem,idDestino);
    }
}
