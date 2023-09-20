package org.example.service;

import org.example.model.Validacoes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ContaBancariaServiceTest {
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;
    @Mock
    private Connection connection;
    @Mock
    private PreparedStatement preparedStatement;

    @InjectMocks
    private ContaBancariaService contaBancariaService;
    @InjectMocks
    private Validacoes validDataBaseService = Mockito.mock(Validacoes.class);

    @Test
    void adicionarConta() throws SQLException {

        when(validDataBaseService.isValidClienteId(anyInt())).thenReturn(true);
        contaBancariaService.adicionarContaAutomatica(1,"Conta corrente");
    }
    @Test
    void adicionarContaInvalido() {
        when(validDataBaseService.isValidClienteId(anyInt())).thenReturn(false);
        contaBancariaService.adicionarContaAutomatica(1,"Conta corrente");
    }

    @Test
    void deletarConta() throws SQLException {
        when(statement.executeUpdate(anyString())).thenReturn(1);
        contaBancariaService.deletarConta(1);
    }
    @Test
    void deletarContaComIdErrado() throws SQLException {
        when(statement.executeUpdate(anyString())).thenReturn(0);
       contaBancariaService.deletarConta(1);
    }

    @Test
    void listarContaComSucesso() throws SQLException {
        // Configure o comportamento do resultSet
        when(resultSet.next()).thenReturn(true,false); // Simula duas linhas no resultado
        when(resultSet.getInt("id")).thenReturn(1); // Simula valores de ID
        when(resultSet.getString("numero_conta")).thenReturn("1111"); // Simula valores de ID
        when(resultSet.getDouble("saldo")).thenReturn(250.0); // Simula valores de ID
        when(resultSet.getString("tipo")).thenReturn("Conta corrente");
        when(resultSet.getInt("cliente_id")).thenReturn(2);

        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        contaBancariaService.listarConta();

    }
    @Test
    void fazerDepositoComSucesso() throws SQLException {
        int idConta = 1;
        double valorDeposito = 100.0;

        // Simule a validação do ID da conta
        when(contaBancariaService.validacoes.isValidContaId(idConta)).thenReturn(true);

        // Simule uma conexão e um PreparedStatement
        when(connection.prepareStatement(anyString())).thenReturn(preparedStatement);

        // Simule a execução do PreparedStatement e o retorno de linhas atualizadas
        when(preparedStatement.executeUpdate()).thenReturn(1); // Uma linha foi atualizada

        // Chame o método fazerDeposito
        contaBancariaService.fazerDeposito(idConta, valorDeposito);

        // Verifique se o PreparedStatement foi chamado com os valores corretos
//        verify(preparedStatement).setDouble(1, valorDeposito);
//        verify(preparedStatement).setInt(2, idConta);
//
//        // Verifique se o método System.out.println foi chamado com a mensagem adequada
//        verify(System.out).println("Depósito de " + valorDeposito + " realizado com sucesso na conta " + idConta);
    }

}