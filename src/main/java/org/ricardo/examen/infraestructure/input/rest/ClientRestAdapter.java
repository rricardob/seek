package org.ricardo.examen.infraestructure.input.rest;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ricardo.examen.application.port.input.CreateClientUseCase;
import org.ricardo.examen.application.port.input.CreateClientsUsecase;
import org.ricardo.examen.application.port.input.GetClientsInfoUseCase;
import org.ricardo.examen.application.port.input.GetClientsMetricsUseCase;
import org.ricardo.examen.domain.model.Client;
import org.ricardo.examen.infraestructure.input.rest.data.request.ClientRequestDto;
import org.ricardo.examen.infraestructure.input.rest.data.request.ClientsRequestDto;
import org.ricardo.examen.infraestructure.input.rest.data.response.ClientInfoResponse;
import org.ricardo.examen.infraestructure.input.rest.data.response.ClientMetricsResponse;
import org.ricardo.examen.infraestructure.input.rest.data.response.CreateClientResponse;
import org.ricardo.examen.infraestructure.input.rest.mapper.ClientRestMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/client")
@RequiredArgsConstructor
public class ClientRestAdapter {

    private final CreateClientUseCase createClientUseCase;
    private final CreateClientsUsecase createClientsUsecase;
    private final GetClientsMetricsUseCase getClientsMetricsUseCase;
    private final GetClientsInfoUseCase getClientsInfoUseCase;
    private final ClientRestMapper mapper;

    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping("/create")
    public ResponseEntity<CreateClientResponse> createClient(@Valid @RequestBody ClientRequestDto request) {
        Client clientToSave = this.mapper.toModel(request);
        Client clientSave = this.createClientUseCase.create(clientToSave);
        return new ResponseEntity<>(this.mapper.toCreateClientResponse(clientSave), HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('WRITE')")
    @PostMapping("/batch")
    public ResponseEntity<Void> createClients(@Valid @RequestBody ClientsRequestDto request) {
        List<Client> clientsToSave = this.mapper.toModels(request.getClients());
        this.createClientsUsecase.create(clientsToSave);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/metrics")
    public ResponseEntity<ClientMetricsResponse> getClientsMetrics() {
        return new ResponseEntity<>(this.mapper.toClientMetricsResponse(this.getClientsMetricsUseCase.getMetrics()), HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('READ')")
    @GetMapping("/info")
    public ResponseEntity<ClientInfoResponse> getClientsInfo() {
        return new ResponseEntity<>(this.mapper.toClientInfoResponse(this.getClientsInfoUseCase.getClientsInfo()), HttpStatus.OK);
    }
}
