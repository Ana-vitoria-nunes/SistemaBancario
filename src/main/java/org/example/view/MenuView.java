package org.example.view;

import org.example.model.ClienteModel;
import org.example.model.InputUser;
import org.example.model.Validacoes;
import org.example.service.ClienteService;
import org.example.service.ContaBancariaService;

import java.sql.SQLException;
import java.util.Scanner;

public class MenuView {
    InputUser inputUser=new InputUser();
    MenuBancarioView bancario=new MenuBancarioView();
    MenuClienteView cliente=new MenuClienteView();
    ClienteModel clienteModel=new ClienteModel();
    ClienteService clienteService= new ClienteService();
    Validacoes validacoes=new Validacoes();
    public void menu() {
        int opcao;
        do {
            System.out.println("\n0. Sair | 1. Login Cliente | 2. Login Bancario| 3. Cadastrar");
            opcao=inputUser.readIntFromUser("Qual opção deseja:");

            switch (opcao) {
                case 1 ->{
                    String nome = inputUser.readStringFromUser("Digite seu nome: ");
                    String cpf = inputUser.readStringFromUser("Digite seu cpf: ");
                    if (validacoes.isValidUserCredentials(nome, cpf)) {
                        System.out.println("\n========================== Bem-Vindo"+ nome+" ==========================");
                        int id=validacoes.getClienteIdPorNome(nome);
                        clienteService.listarClientePorId(id);
                        cliente.caseCliente();
                    }
                }
                case 2 -> {
                    String nome = inputUser.readStringFromUser("Digite seu nome: ");
                    String cpf = inputUser.readStringFromUser("Digite seu cpf: ");
                    if (validacoes.isValidLibrarianCredentials(nome, cpf)) {
                        System.out.println("\n========================== Bem-Vindo" + nome + " ==========================");
                        int id = validacoes.getClienteIdPorNome(nome);
                        clienteService.listarClientePorId(id);
                        bancario.caseBancario();
                    }
                }
                case 3 ->addCliente();
                case 0 -> System.out.println("Encerrando o programa...");
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 0);
    }
    public void addCliente() {
        String name = inputUser.readStringFromUser("Qual seu nome: ");
        String cpf = inputUser.readStringFromUser("Digite seu CPF: ");
        String endereco = inputUser.readStringFromUser("Qual seu endereço: ");
        clienteModel.setNome(name);
        clienteModel.setCpf(cpf);
        clienteModel.setEndereco(endereco);
        clienteService.adicionarCliente(clienteModel.getNome(), clienteModel.getCpf(), clienteModel.getEndereco());
    }

}
