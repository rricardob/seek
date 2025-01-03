package org.ricardo.examen.infraestructure.output.persistence;

import lombok.RequiredArgsConstructor;
import org.ricardo.examen.application.port.output.UserPersistencePort;
import org.ricardo.examen.domain.model.User;
import org.ricardo.examen.infraestructure.output.persistence.entity.UserEntity;
import org.ricardo.examen.infraestructure.output.persistence.mapper.UserDboMapper;
import org.ricardo.examen.infraestructure.output.persistence.repository.UserRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserPersistenceAdapter implements UserPersistencePort {

    private final UserRepository userRepository;
    private final UserDboMapper userDboMapper;

    @Override
    public User create(User user) {
        UserEntity userToEntity = this.userDboMapper.toEntity(user);
        userToEntity = userRepository.save(userToEntity);
        return this.userDboMapper.toDomain(userToEntity);
    }

    @Override
    public User findByUsername(String username) {
        UserEntity userEntity = userRepository.findByUsername(username).orElse(null);
        return this.userDboMapper.toDomain(userEntity);
    }
}
