package org.example.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

import static org.example.connection.Connect.fazerConexao;

public class TransacaoService {
    Statement statement;
    public TransacaoService() {
        try {
            statement = fazerConexao().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void adicionarTransacao(Date data, double valor, int idContaOrigem, int idContaDestino) {
        String sql = "INSERT INTO transacao (data, valor, conta_origem_id, conta_destino_id) VALUES ('" + data + "', '" + valor + "','" + idContaOrigem + "', " + idContaDestino + ")";
        try {
            statement.executeUpdate(sql);
            System.out.println("Transação adicionado com sucesso!");
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
}
