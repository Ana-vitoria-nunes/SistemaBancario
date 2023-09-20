package org.example.service;

import org.example.model.Validacoes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {
    @Mock
    private Statement statement;
    @Mock
    private ResultSet resultSet;

    @InjectMocks
    private ClienteService clienteService;
    @InjectMocks
    private Validacoes validDataBaseService = Mockito.mock(Validacoes.class);

    @Test
    void adicionarCliente() {
        String nome="ana";
        String cpf="1234";
        String endecp="Rua A";
        when(validDataBaseService.isValidClienteInfo(anyString(),anyString(),anyString())).thenReturn(true);
        // Chama o método com um ID de usuário inválido
        clienteService.adicionarCliente(nome, cpf,endecp);
    }
    @Test
    void adicionarClienteInvalido() {
        String nome="ana";
        String cpf="1234";
        String endecp="Rua A";
        when(validDataBaseService.isValidClienteInfo(anyString(),anyString(),anyString())).thenReturn(false);
        // Chama o método com um ID de usuário inválido
        clienteService.adicionarCliente(nome, cpf,endecp);
    }

    @Test
    void deletarCliente() throws SQLException {
        when(statement.executeUpdate(anyString())).thenReturn(1);
        clienteService.deletarCliente(1);
    }
    @Test
    void deletarClienteComIdErrado() throws SQLException {
        when(statement.executeUpdate(anyString())).thenReturn(0);
        clienteService.deletarCliente(1);
    }

    @Test
    public void AtualizarClienteComSucesso() throws SQLException {

        when(statement.executeUpdate(anyString())).thenReturn(1);
        clienteService.atualizarCliente(1, "Rua B");
    }
    @Test
    public void AtualizarClienteErrado() throws SQLException {
        when(statement.executeUpdate(anyString())).thenReturn(0);
        clienteService.atualizarCliente(1, "Rua 9");
    }
    @Test
    void listarClienteComSucesso() throws SQLException {
        // Configure o comportamento do resultSet
        when(resultSet.next()).thenReturn(true,false); // Simula duas linhas no resultado
        when(resultSet.getInt("id")).thenReturn(1); // Simula valores de ID
        when(resultSet.getString("cpf")).thenReturn("1111"); // Simula valores de ID
        when(resultSet.getString("nome")).thenReturn("leo"); // Simula valores de ID
        when(resultSet.getString("endereco")).thenReturn("Rua 89");

        // Configure o statement para retornar o resultSet mockado
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        // Chama o método listarAutores
        clienteService.listarClientes();

    }

}