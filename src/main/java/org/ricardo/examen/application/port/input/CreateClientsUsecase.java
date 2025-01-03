package org.ricardo.examen.application.port.input;

import org.ricardo.examen.domain.model.Client;

import java.util.List;

public interface CreateClientsUsecase {
    void create(List<Client> client);
}
