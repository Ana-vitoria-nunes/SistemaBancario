package org.example.service;

import org.example.model.Validacoes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransacaoServiceTest {
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private TransacaoService transacaoService;
    @InjectMocks
    private Validacoes validDataBaseService = Mockito.mock(Validacoes.class);

    @Test
    void adicionartransacao() {
        when(validDataBaseService.isValidContaId(anyInt())).thenReturn(true);
        when(validDataBaseService.isValidContaId(anyInt())).thenReturn(true);
        // Chama o método com um ID de usuário inválido
        transacaoService.adicionarTransacao(Date.valueOf(LocalDate.now()),20.0,1,2);
    }
    @Test
    void adicionarTransacaoIdOrigemInvalido() {
        when(validDataBaseService.isValidClienteInfo(anyString(),anyString(),anyString())).thenReturn(false);
        when(validDataBaseService.isValidClienteInfo(anyString(),anyString(),anyString())).thenReturn(true);
        // Chama o método com um ID de usuário inválido
        transacaoService.adicionarTransacao(Date.valueOf(LocalDate.now()),20.0,1,2);
    }
    @Test
    void adicionarTransacaoIdDestinoInvalido() {
        when(validDataBaseService.isValidClienteInfo(anyString(),anyString(),anyString())).thenReturn(true);
        when(validDataBaseService.isValidClienteInfo(anyString(),anyString(),anyString())).thenReturn(false);
        // Chama o método com um ID de usuário inválido
        transacaoService.adicionarTransacao(Date.valueOf(LocalDate.now()),20.0,1,2);
    }
    @Test
    void listarTransacaoComSucesso() throws SQLException {
        // Configure o comportamento do resultSet
        when(resultSet.next()).thenReturn(true,false); // Simula duas linhas no resultado
        when(resultSet.getInt("id")).thenReturn(1); // Simula valores de ID
        when(resultSet.getString("data")).thenReturn("2023-09-20"); // Simula valores de ID
        when(resultSet.getDouble("valor")).thenReturn(25.60); // Simula valores de ID
        when(resultSet.getInt("conta_origem_id")).thenReturn(1); // Simula valores de ID
        when(resultSet.getInt("conta_destino_id")).thenReturn(3);

        // Configure o statement para retornar o resultSet mockado
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        // Chama o método listarAutores
        transacaoService.listarTransacao();

    }


}