package org.ricardo.examen.infraestructure.input.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.ricardo.examen.domain.model.Client;
import org.ricardo.examen.domain.model.ClientInfo;
import org.ricardo.examen.domain.model.ClientMetrics;
import org.ricardo.examen.infraestructure.input.rest.data.request.ClientRequestDto;
import org.ricardo.examen.infraestructure.input.rest.data.response.ClientInfoResponse;
import org.ricardo.examen.infraestructure.input.rest.data.response.ClientMetricsResponse;
import org.ricardo.examen.infraestructure.input.rest.data.response.CreateClientResponse;
import org.ricardo.examen.infraestructure.util.DateHelper;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Mapper(componentModel = "spring", uses = DateHelper.class)
public interface ClientRestMapper {

    @Mapping(source = "birthDate", target = "age", qualifiedByName = "getYearsOld")
    Client toModel(ClientRequestDto clientRequestDto);

    List<Client> toModels(List<ClientRequestDto> clientsRequestDto);

    default CreateClientResponse toCreateClientResponse(Client client) {
        return CreateClientResponse.builder().id(UUID.randomUUID()).name(client.getName()).createdAt(LocalDate.now()).build();
    }

    ClientMetricsResponse toClientMetricsResponse(ClientMetrics clientMetrics);

    ClientInfoResponse toClientInfoResponse(ClientInfo clientInfo);
}
