package org.example.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.connection.Connect.fazerConexao;

public class ContaBancariaService {
    Statement statement;
    public ContaBancariaService() {
        try {
            statement = fazerConexao().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void adicionarConta(String numeroConta, double saldo, int idCliente) {
        String sql = "INSERT INTO contabancaria (numero_conta, saldo, cliente_id) VALUES ('" + numeroConta + "', '" + saldo + "', " + idCliente + ")";
        try {
            statement.executeUpdate(sql);
            System.out.println("Conta adicionado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarConta(int id) {
        String sql = "DELETE FROM contabancaria WHERE id = " + id;
        try {
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
        String sql = "SELECT id,numero_conta, saldo, cliente_id FROM contabancaria";
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id=resultSet.getInt("id");
                String numeroConta = resultSet.getString("numero_conta");
                double saldo = resultSet.getDouble("saldo");
                int idCliente = resultSet.getInt("cliente_id");
                System.out.println("ID: "+ id +" Numero da conta: " + numeroConta + " | Saldo: " + saldo + " | ID Cliente: " + idCliente);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
