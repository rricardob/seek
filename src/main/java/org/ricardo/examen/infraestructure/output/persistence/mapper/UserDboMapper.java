package org.ricardo.examen.infraestructure.output.persistence.mapper;

import org.mapstruct.Mapper;
import org.ricardo.examen.domain.model.User;
import org.ricardo.examen.infraestructure.output.persistence.entity.UserEntity;

@Mapper(componentModel = "spring")
public interface UserDboMapper {

    User toDomain(UserEntity userEntity);

    UserEntity toEntity(User user);
}
