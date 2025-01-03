package org.ricardo.examen.application.port.input;

import org.ricardo.examen.domain.model.Client;

public interface CreateClientUseCase {
    Client create(Client client);
}
