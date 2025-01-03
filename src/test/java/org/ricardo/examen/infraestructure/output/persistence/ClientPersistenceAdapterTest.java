package org.ricardo.examen.infraestructure.output.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ricardo.examen.domain.model.Client;
import org.ricardo.examen.infraestructure.output.persistence.entity.ClientEntity;
import org.ricardo.examen.infraestructure.output.persistence.mapper.ClientDboMapper;
import org.ricardo.examen.infraestructure.output.persistence.repository.ClientRepository;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class ClientPersistenceAdapterTest {

    @Mock
    private ClientRepository clientRepository;

    @Mock
    private ClientDboMapper mapper;

    @InjectMocks
    private ClientPersistenceAdapter clientPersistenceAdapter;

    @Test
    void testSave() {
        // Arrange
        Client client = new Client(1L, "Name", "firstname", "lastname",30, LocalDate.now());
        ClientEntity clientEntity = new ClientEntity(1L, "Name", "firstname", "lastname",30, LocalDate.now());
        ClientEntity savedClientEntity = new ClientEntity(1L, "Name", "firstname", "lastname",30, LocalDate.now());

        when(mapper.toEntity(client)).thenReturn(clientEntity);
        when(clientRepository.save(clientEntity)).thenReturn(savedClientEntity);
        when(mapper.toDomain(savedClientEntity)).thenReturn(client);

        // Act
        Client result = clientPersistenceAdapter.save(client);

        // Assert
        assertNotNull(result);
        assertEquals("Name", result.getName());
        assertEquals("firstname", result.getFirstName());

        verify(mapper).toEntity(client);
        verify(clientRepository).save(clientEntity);
        verify(mapper).toDomain(savedClientEntity);
    }

    @Test
    void testSaveAll() {
        // Arrange
        List<Client> clients = List.of(
                new Client(1L, "Name", "firstname", "lastname",30, LocalDate.now()),
                new Client(2L, "Name2", "firstname2", "lastname2",30, LocalDate.now())
        );

        List<ClientEntity> clientEntities = List.of(
                new ClientEntity(1L, "Name", "firstname", "lastname",30, LocalDate.now()),
                new ClientEntity(2L, "Name2", "firstname2", "lastname2",30, LocalDate.now())
        );

        when(mapper.toEntities(clients)).thenReturn(clientEntities);

        // Act
        clientPersistenceAdapter.saveAll(clients);

        // Assert
        verify(mapper).toEntities(clients);
        verify(clientRepository).saveAll(clientEntities);
    }

    @Test
    void testGetDataForMetrics() {
        // Arrange
        List<ClientEntity> clientEntities = List.of(
                new ClientEntity(1L, "Name", "firstname", "lastname",30, LocalDate.now()),
                new ClientEntity(2L, "Name2", "firstname2", "lastname2",30, LocalDate.now())
        );

        List<Client> clients = List.of(
                new Client(1L, "Name", "firstname", "lastname",30, LocalDate.now()),
                new Client(2L, "Name2", "firstname2", "lastname2",30, LocalDate.now())
        );

        when(clientRepository.findAll()).thenReturn(clientEntities);
        when(mapper.toDomain(clientEntities.get(0))).thenReturn(clients.get(0));
        when(mapper.toDomain(clientEntities.get(1))).thenReturn(clients.get(1));

        // Act
        List<Client> result = clientPersistenceAdapter.getDataForMetrics();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Name", result.get(0).getName());
        assertEquals("Name2", result.get(1).getName());

        verify(clientRepository).findAll();
        verify(mapper).toDomain(clientEntities.get(0));
        verify(mapper).toDomain(clientEntities.get(1));
    }

}