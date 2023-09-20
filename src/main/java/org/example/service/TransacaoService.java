package org.example.service;
import org.example.model.Validacoes;

import java.sql.*;
import java.util.Date;

import static org.example.connection.Connect.fazerConexao;

public class TransacaoService {
    Statement statement;
    Validacoes validacoes=new Validacoes();
    public TransacaoService() {
        try {
            statement = fazerConexao().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void adicionarTransacao(Date data, double valor, int idContaOrigem, int idContaDestino) {
        if (!validacoes.isValidContaId(idContaOrigem)){
            System.out.println("ID da conta de origem inválido!");
            return;
        }
        if (!validacoes.isValidContaId(idContaDestino)){
            System.out.println("ID da conta de destino inválido!");
            return;
        }
        try {
            // Verifique se a conta de origem possui saldo suficiente para a transferência
            String sqlSaldoOrigem = "SELECT saldo FROM contabancaria WHERE id = ?";
            PreparedStatement preparedStatementSaldoOrigem = fazerConexao().prepareStatement(sqlSaldoOrigem);
            preparedStatementSaldoOrigem.setInt(1, idContaOrigem);
            ResultSet resultSetSaldoOrigem = preparedStatementSaldoOrigem.executeQuery();

            if (resultSetSaldoOrigem.next()) {
                double saldoOrigem = resultSetSaldoOrigem.getDouble("saldo");
                if (saldoOrigem >= valor) {
                    // Atualize o saldo da conta de origem (subtraindo o valor)
                    String sqlAtualizarOrigem = "UPDATE contabancaria SET saldo = saldo - ? WHERE id = ?";
                    PreparedStatement preparedStatementAtualizarOrigem = fazerConexao().prepareStatement(sqlAtualizarOrigem);
                    preparedStatementAtualizarOrigem.setDouble(1, valor);
                    preparedStatementAtualizarOrigem.setInt(2, idContaOrigem);
                    preparedStatementAtualizarOrigem.executeUpdate();

                    // Atualize o saldo da conta de destino (adicionando o valor)
                    String sqlAtualizarDestino = "UPDATE contabancaria SET saldo = saldo + ? WHERE id = ?";
                    PreparedStatement preparedStatementAtualizarDestino = fazerConexao().prepareStatement(sqlAtualizarDestino);
                    preparedStatementAtualizarDestino.setDouble(1, valor);
                    preparedStatementAtualizarDestino.setInt(2, idContaDestino);
                    preparedStatementAtualizarDestino.executeUpdate();

                    // Insira a transação
                    String sqlInserirTransacao = "INSERT INTO transacao (data, valor, conta_origem_id, conta_destino_id) VALUES (?, ?, ?, ?)";
                    PreparedStatement preparedStatementInserirTransacao = fazerConexao().prepareStatement(sqlInserirTransacao);
                    preparedStatementInserirTransacao.setDate(1, new java.sql.Date(data.getTime()));
                    preparedStatementInserirTransacao.setDouble(2, valor);
                    preparedStatementInserirTransacao.setInt(3, idContaOrigem);
                    preparedStatementInserirTransacao.setInt(4, idContaDestino);
                    preparedStatementInserirTransacao.executeUpdate();

                    System.out.println("Transferência de " + valor + " realizada com sucesso da conta " + idContaOrigem + " para a conta " + idContaDestino);
                } else {
                    System.out.println("Saldo insuficiente na conta " + idContaOrigem + " para realizar a transferência de " + valor);
                }
            } else {
                System.out.println("Conta de origem não encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void listarTransacao() {
        String sql = "SELECT id, data, valor, conta_origem_id, conta_destino_id FROM transacao";
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id=resultSet.getInt("id");
                String data = resultSet.getString("data");
                double valor = resultSet.getDouble("valor");
                int ContaOrigem = resultSet.getInt("conta_origem_id");
                int ContaDestino = resultSet.getInt("conta_destino_id");
                System.out.println("ID: "+ id +" Data transação: " + data + " | Valor: " + valor + " | Conta de origem: " + ContaOrigem +" | Conta de destino: " + ContaDestino);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void listarTransacaoPorId(int id) {
        String sql = "SELECT id, data, valor, conta_origem_id, conta_destino_id FROM transacao WHERE  conta_origem_id = ?";

        try {
            PreparedStatement preparedStatement = fazerConexao().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int idT=resultSet.getInt("id");
                String data = resultSet.getString("data");
                double valor = resultSet.getDouble("valor");
                int ContaOrigem = resultSet.getInt("conta_origem_id");
                int ContaDestino = resultSet.getInt("conta_destino_id");
                System.out.println("ID: "+ idT +" Data transação: " + data + " | Valor: " + valor + " | Conta de origem: " + ContaOrigem +" | Conta de destino: " + ContaDestino);
            } else {
                System.out.println("Conta com ID " + id + " não encontrada.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
