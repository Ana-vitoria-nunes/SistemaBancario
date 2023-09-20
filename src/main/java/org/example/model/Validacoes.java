package org.example.model;

import org.example.connection.Connect;

import java.sql.*;
import java.util.Date;

public class Validacoes {
        private Connection connection = Connect.fazerConexao();
    public boolean isValidContaId(int id) {
        String sql = "SELECT COUNT(*) FROM contabancaria WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            resultSet.close();
            preparedStatement.close();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean isValidClienteId(int id) {
        String sql = "SELECT COUNT(*) FROM cliente WHERE id=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            resultSet.close();
            preparedStatement.close();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    //validações de null
    public boolean isValidClienteInfo(String nome, String cpf, String endereco) {
        return nome != null && !nome.isEmpty() &&
                cpf != null && !cpf.isEmpty() &&
                endereco != null && !endereco.isEmpty();
    }

    private boolean isAdmin(String alias) {
        return alias.equals("Mario") || alias.equals("Marta");
    }

    public boolean isValidLibrarianCredentials(String nome, String cpf) {
        if (nome == null || nome.isEmpty() || cpf == null || cpf.isEmpty()) {
            System.out.println("O nome de Bancario e a CPF não podem estar vazios.");
            return false;
        }
        if (isAdmin(nome)) {
            String sql = "SELECT COUNT(*) FROM cliente WHERE nome=? AND cpf=?";
            try {
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                preparedStatement.setString(1, nome);
                preparedStatement.setString(2, cpf);
                ResultSet resultSet = preparedStatement.executeQuery();
                resultSet.next();
                int count = resultSet.getInt(1);

                resultSet.close();
                preparedStatement.close();

                if (count > 0) {
                    return true;
                } else {
                    System.out.println("Credenciais de bancario inválidas.");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Erro ao verificar as credenciais do usuário.");
            }
        } else {
            System.out.println("Acesso não autorizado. Você não é um bancario.");
        }
        return false;
    }

    public boolean isValidUserCredentials(String nome, String cpf) {
        if (nome == null || nome.isEmpty() || cpf == null || cpf.isEmpty()) {
            System.out.println("O nome de CLiente e o  CPF não podem estar vazios.");
            return false;
        }

        String sql = "SELECT COUNT(*) FROM cliente WHERE nome=? AND cpf=?";

        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, cpf);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            resultSet.close();
            preparedStatement.close();

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public int getClienteIdPorNome(String nome) {
        try {
            String sql = "SELECT id FROM cliente WHERE nome = ?";

            // Use PreparedStatement para consultas parametrizadas
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nome); // Defina o valor do parâmetro

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                return resultSet.getInt("id");
            } else {
                return 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
