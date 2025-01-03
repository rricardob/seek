package org.ricardo.examen.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ricardo.examen.application.port.output.ClientPersistencePort;
import org.ricardo.examen.domain.model.Client;
import org.ricardo.examen.domain.model.ClientInfo;
import org.ricardo.examen.domain.model.ClientMetrics;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClientServiceTest {
    @Mock
    private ClientPersistencePort clientPersistencePort;

    @InjectMocks
    private ClientService clientService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateSingleClient() {
        Client client = new Client(1L, "client", "", "", 30, LocalDate.now());

        when(clientPersistencePort.save(client)).thenReturn(client);

        Client result = clientService.create(client);

        assertNotNull(result);
        assertEquals(client, result);

        verify(clientPersistencePort, times(1)).save(client);
    }

    @Test
    void testCreateMultipleClients() {
        List<Client> clients = Arrays.asList(
                new Client(1L, "client1", "", "", 30, LocalDate.now()),
                new Client(2L, "client2", "", "", 30, LocalDate.now())
        );

        doNothing().when(clientPersistencePort).saveAll(clients);

        clientService.create(clients);

        verify(clientPersistencePort, times(1)).saveAll(clients);
    }

    @Test
    void testGetMetrics() {
        List<Client> clients = Arrays.asList(
                new Client(1L, "client1", "", "", 30, LocalDate.now()),
                new Client(2L, "client2", "", "", 30, LocalDate.now()),
                new Client(3L, "client3", "", "", 30, LocalDate.now())
        );

        when(clientPersistencePort.getDataForMetrics()).thenReturn(clients);

        ClientMetrics metrics = clientService.getMetrics();

        assertNotNull(metrics);
        assertEquals(30.0, metrics.getAverage(), 0.01); // Expected average is 30

        verify(clientPersistencePort, times(1)).getDataForMetrics();
    }

    @Test
    void testGetClientsInfo() {
        List<Client> clients = Arrays.asList(
                new Client(1L, "client1", "", "", 30, LocalDate.now()),
                new Client(2L, "client2", "", "", 30, LocalDate.now())
        );

        when(clientPersistencePort.getDataForMetrics()).thenReturn(clients);

        ClientInfo info = clientService.getClientsInfo();

        assertNotNull(info);
        assertEquals(clients, info.getClients());
        assertTrue(info.getLifeExpectancy() > 0);

        verify(clientPersistencePort, times(2)).getDataForMetrics();
    }
}