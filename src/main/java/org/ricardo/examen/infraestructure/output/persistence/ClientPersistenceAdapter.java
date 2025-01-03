package org.ricardo.examen.infraestructure.output.persistence;

import lombok.RequiredArgsConstructor;
import org.ricardo.examen.application.port.output.ClientPersistencePort;
import org.ricardo.examen.domain.model.Client;
import org.ricardo.examen.infraestructure.output.persistence.entity.ClientEntity;
import org.ricardo.examen.infraestructure.output.persistence.mapper.ClientDboMapper;
import org.ricardo.examen.infraestructure.output.persistence.repository.ClientRepository;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ClientPersistenceAdapter implements ClientPersistencePort {

    private final ClientRepository clientRepository;
    private final ClientDboMapper mapper;

    @Override
    public Client save(Client client) {
        ClientEntity clientEntity = this.mapper.toEntity(client);
        return this.mapper.toDomain(this.clientRepository.save(clientEntity));
    }

    @Override
    public void saveAll(List<Client> clients) {
        List<ClientEntity> clientEntities = this.mapper.toEntities(clients);
        this.clientRepository.saveAll(clientEntities);
    }

    @Override
    public List<Client> getDataForMetrics() {
        return this.clientRepository.findAll().stream().map(this.mapper::toDomain).toList();
    }
}
