package org.example.service;

import org.example.model.Validacoes;

import java.sql.*;

import static org.example.connection.Connect.fazerConexao;

public class ClienteService {
    Statement statement;
    Validacoes validacoes=new Validacoes();
    public ClienteService() {
        try {
            statement = fazerConexao().createStatement();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void adicionarCliente(String nome, String cpf, String endereco) {
        if (!validacoes.isValidClienteInfo(nome,cpf,endereco)){
            System.out.println("As informações do cliente não pode estar vazias");
            return;
        }

        String sql = "INSERT INTO cliente (nome, cpf, endereco) VALUES ('" + nome + "', '" + cpf + "', '" + endereco + "')";
        try {
            statement.executeUpdate(sql);
            System.out.println("Cliente adicionado com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletarCliente(int id) {
        try {
            String sqlTrans = "DELETE FROM Transacao WHERE conta_origem_id IN (SELECT id FROM ContaBancaria WHERE cliente_id = ?) OR conta_destino_id IN (SELECT id FROM ContaBancaria WHERE cliente_id = ?)";
            PreparedStatement preparedStatement = fazerConexao().prepareStatement(sqlTrans);
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            String sqlConta = "DELETE FROM ContaBancaria WHERE cliente_id = ?";
            preparedStatement = fazerConexao().prepareStatement(sqlConta);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
            preparedStatement.close();

            String sql = "DELETE FROM cliente WHERE id = " + id;
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
                System.out.println("Cliente com ID " + id + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void listarClientes() {
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
    public void listarClientePorId(int idEspecifico) {
        String sql = "SELECT id, nome, cpf, endereco FROM cliente WHERE id = ?";
        try {
            PreparedStatement preparedStatement = fazerConexao().prepareStatement(sql);
            preparedStatement.setInt(1, idEspecifico);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("id");
                String cpf = resultSet.getString("cpf");
                String nome = resultSet.getString("nome");
                String endereco = resultSet.getString("endereco");
                System.out.println("ID: " + id + " | CPF: " + cpf + " | Nome: " + nome + " | Endereço: " + endereco);
            } else {
                System.out.println("Cliente com ID " + idEspecifico + " não encontrado.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
