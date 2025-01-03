package org.ricardo.examen.infraestructure.output.persistence.mapper;

import org.mapstruct.Mapper;
import org.ricardo.examen.domain.model.Client;
import org.ricardo.examen.infraestructure.output.persistence.entity.ClientEntity;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ClientDboMapper {

    Client toDomain(ClientEntity entity);

    ClientEntity toEntity(Client client);

    List<ClientEntity> toEntities(List<Client> clients);
}
