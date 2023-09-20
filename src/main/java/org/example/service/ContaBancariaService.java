package org.example.service;

import org.example.model.Validacoes;

import java.sql.*;
import java.util.Random;

import static org.example.connection.Connect.fazerConexao;

public class ContaBancariaService {
    Statement statement;
    Validacoes validacoes = new Validacoes();

    public ContaBancariaService() {
        try {
            statement = fazerConexao().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void adicionarContaAutomatica(int idCliente, String tipo) {
        if (!validacoes.isValidClienteId(idCliente)) {
            System.out.println("ID de Cliente inválido!");
            return;
        }
        String numeroConta = gerarNumeroContaAleatorio();
        double saldoInicial = 0.0;

        String sql = "INSERT INTO contabancaria (numero_conta, saldo, cliente_id, tipo) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement preparedStatement = fazerConexao().prepareStatement(sql);
            preparedStatement.setString(1, numeroConta);
            preparedStatement.setDouble(2, saldoInicial);
            preparedStatement.setInt(3, idCliente);
            preparedStatement.setString(4, tipo);

            preparedStatement.executeUpdate();
            System.out.println("Conta adicionada com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    private String gerarNumeroContaAleatorio() {
        Random random = new Random();
        int numero = 1000 + random.nextInt(9000); // Gera um número de 4 dígitos
        return "CONTA" + numero; // Você pode ajustar o formato do número de conta conforme necessário
    }
    public void deletarConta(int id) {
        try {
            String sqlTrans = "DELETE FROM Transacao WHERE conta_origem_id IN (SELECT id FROM ContaBancaria WHERE cliente_id = ?) OR conta_destino_id IN (SELECT id FROM ContaBancaria WHERE cliente_id = ?)";
            PreparedStatement preparedStatement = fazerConexao().prepareStatement(sqlTrans);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            String sql = "DELETE FROM contabancaria WHERE id = " + id;
            int rowCount = statement.executeUpdate(sql);
            if (rowCount > 0) {
                System.out.println("Conta deletado com sucesso!");
            } else {
                System.out.println("Conta com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void listarConta() {
        String sql = "SELECT id,numero_conta, saldo, cliente_id, tipo FROM contabancaria";
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String numeroConta = resultSet.getString("numero_conta");
                double saldo = resultSet.getDouble("saldo");
                int idCliente = resultSet.getInt("cliente_id");
                String tipo = resultSet.getString("tipo");
                System.out.println("ID: " + id + " Numero da conta: " + numeroConta + " | Saldo: " + saldo + " | ID Cliente: " + idCliente + " | Tipo: " + tipo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void listarContaPorId(int idCliente) {
        String sql = "SELECT id, numero_conta, saldo, cliente_id, tipo FROM contabancaria WHERE cliente_id = ?";

        try {
            PreparedStatement preparedStatement = fazerConexao().prepareStatement(sql);
            preparedStatement.setInt(1, idCliente);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int contaId = resultSet.getInt("id");
                String numeroConta = resultSet.getString("numero_conta");
                double saldo = resultSet.getDouble("saldo");
                String tipo = resultSet.getString("tipo");

                System.out.println("ID: " + contaId + " | Numero da conta: " + numeroConta + " | Saldo: " + saldo + " | Tipo: " + tipo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void fazerDeposito(int idConta, double valorDeposito) {
        try {
            if (!validacoes.isValidContaId(idConta)) {
                System.out.println("ID da conta inválido!");
                return;
            }

            if (valorDeposito <= 0) {
                System.out.println("O valor do depósito deve ser positivo!");
                return;
            }

            String sql = "UPDATE contabancaria SET saldo = saldo + ? WHERE id = ?";
            PreparedStatement preparedStatement = fazerConexao().prepareStatement(sql);

            // Define os valores dos placeholders
            preparedStatement.setDouble(1, valorDeposito);
            preparedStatement.setInt(2, idConta);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Depósito de " + valorDeposito + " realizado com sucesso na conta " + idConta);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void fazerSaque(int idConta, double valorSaque) {
        try {
            if (!validacoes.isValidContaId(idConta)) {
                System.out.println("ID da conta inválido!");
                return;
            }

            if (valorSaque <= 0) {
                System.out.println("O valor do saque deve ser positivo!");
                return;
            }

            String sql = "UPDATE contabancaria SET saldo = saldo - ? WHERE id = ? AND saldo >= ?";
            PreparedStatement preparedStatement = fazerConexao().prepareStatement(sql);

            // Define os valores dos placeholders
            preparedStatement.setDouble(1, valorSaque);
            preparedStatement.setInt(2, idConta);
            preparedStatement.setDouble(3, valorSaque);

            int rowsUpdated = preparedStatement.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Saque de " + valorSaque + " realizado com sucesso na conta " + idConta);
            } else {
                System.out.println("Saldo insuficiente para realizar o saque de " + valorSaque);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
