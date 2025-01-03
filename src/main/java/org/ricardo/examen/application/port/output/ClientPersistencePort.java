package org.ricardo.examen.application.port.output;

import org.ricardo.examen.domain.model.Client;

import java.util.List;

public interface ClientPersistencePort {

    Client save(Client client);

    void saveAll(List<Client> clients);

    List<Client> getDataForMetrics();

}
