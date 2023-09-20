package org.example.service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.example.connection.Connect.fazerConexao;

public class ClienteService {
    Statement statement;
    public ClienteService() {
        try {
            statement = fazerConexao().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void adicionarCliente(String nome, String cpf, String endereco) {
        String sql = "INSERT INTO cliente (nome, cpf, endereco) VALUES ('" + nome + "', '" + cpf + "', " + endereco + ")";
        try {
            statement.executeUpdate(sql);
            System.out.println("Cliente adicionado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarCliente(int id) {
        String sql = "DELETE FROM cliente WHERE id = " + id;
        try {
            int rowCount = statement.executeUpdate(sql);
            if (rowCount > 0) {
                System.out.println("Cliente deletado com sucesso!");
            } else {
                System.out.println("Cliente com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizarCliente(int id, String endereco) {
        String sql = "UPDATE cliente SET  endereco = '" + endereco + "' WHERE id = " + id;
        try {
            int rowCount = statement.executeUpdate(sql);
            if (rowCount > 0) {
                System.out.println("Endereço atualizado com sucesso.");
            } else {
                System.out.println("Usuário com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void listarUsuarios() {
        String sql = "SELECT id,nome, cpf, endereco FROM cliente";
        try {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id=resultSet.getInt("id");
                String cpf = resultSet.getString("cpf");
                String nome = resultSet.getString("nome");
                String endereco = resultSet.getString("endereco");
                System.out.println("ID: "+ id +" CPF: " + cpf + " | Nome: " + nome + " | Endereço: " + endereco);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
