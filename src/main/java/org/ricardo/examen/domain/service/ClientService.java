package org.ricardo.examen.domain.service;

import lombok.RequiredArgsConstructor;
import org.ricardo.examen.application.port.input.CreateClientUseCase;
import org.ricardo.examen.application.port.input.CreateClientsUsecase;
import org.ricardo.examen.application.port.input.GetClientsInfoUseCase;
import org.ricardo.examen.application.port.input.GetClientsMetricsUseCase;
import org.ricardo.examen.application.port.output.ClientPersistencePort;
import org.ricardo.examen.domain.model.Client;
import org.ricardo.examen.domain.model.ClientInfo;
import org.ricardo.examen.domain.model.ClientMetrics;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

import static org.ricardo.examen.infraestructure.util.MetricsHelper.*;

@Service
@RequiredArgsConstructor
public class ClientService implements CreateClientUseCase, CreateClientsUsecase, GetClientsMetricsUseCase, GetClientsInfoUseCase {

    private final ClientPersistencePort clientPersistencePort;
    private static final Logger logger = LoggerFactory.getLogger(ClientService.class);

    @Override
    public Client create(Client client) {
        logger.info("Create client {}", client);
        return this.clientPersistencePort.save(client);
    }

    @Override
    public void create(List<Client> client) {
        logger.info("Create clients {}", client);
        this.clientPersistencePort.saveAll(client);
    }

    @Override
    public ClientMetrics getMetrics() {
        logger.info("Get metrics");
        List<Integer> ages = getAges(this.clientPersistencePort.getDataForMetrics());
        double average = calculateAverage(ages);
        return ClientMetrics.builder()
                .average(average)
                .standardDeviation(calculateStandardDeviation(ages, average)).build();
    }

    @Override
    public ClientInfo getClientsInfo() {
        logger.info("Get clients info");
        List<Integer> ages = getAges(this.clientPersistencePort.getDataForMetrics());
        return ClientInfo.builder()
                .clients(this.clientPersistencePort.getDataForMetrics())
                .lifeExpectancy(calculateLifeExpectancy(calculateSurvivalRates(ages))).build();
    }
}
