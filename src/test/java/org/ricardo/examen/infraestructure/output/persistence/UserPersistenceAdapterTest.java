package org.ricardo.examen.infraestructure.output.persistence;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.ricardo.examen.domain.model.User;
import org.ricardo.examen.infraestructure.output.persistence.entity.UserEntity;
import org.ricardo.examen.infraestructure.output.persistence.mapper.UserDboMapper;
import org.ricardo.examen.infraestructure.output.persistence.repository.UserRepository;
import org.ricardo.examen.infraestructure.util.enums.Role;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserPersistenceAdapterTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserDboMapper userDboMapper;

    @InjectMocks
    private UserPersistenceAdapter userPersistenceAdapter;

    @Test
    void testCreate() {
        // Arrange
        User user = new User(1L, "ricardo", "username", "email@example.com", Role.CLIENT);
        UserEntity userEntity = new UserEntity(1L, "username", "username", "password", Role.CLIENT);
        UserEntity savedUserEntity = new UserEntity(1L, "username", "username", "password", Role.CLIENT);

        when(userDboMapper.toEntity(user)).thenReturn(userEntity);
        when(userRepository.save(userEntity)).thenReturn(savedUserEntity);
        when(userDboMapper.toDomain(savedUserEntity)).thenReturn(user);

        // Act
        User result = userPersistenceAdapter.create(user);

        // Assert
        assertNotNull(result);
        assertEquals("username", result.getUsername());

        verify(userDboMapper).toEntity(user);
        verify(userRepository).save(userEntity);
        verify(userDboMapper).toDomain(savedUserEntity);
    }

    @Test
    void testFindByUsername() {
        // Arrange
        String username = "username";
        UserEntity userEntity = new UserEntity(1L, "ricardo", "username", "password", Role.CLIENT);
        User user = new User(1L, "ricardo", "username", "password", Role.CLIENT);

        when(userRepository.findByUsername(username)).thenReturn(Optional.of(userEntity));
        when(userDboMapper.toDomain(userEntity)).thenReturn(user);

        // Act
        User result = userPersistenceAdapter.findByUsername(username);

        // Assert
        assertNotNull(result);
        assertEquals("username", result.getUsername());

        verify(userRepository).findByUsername(username);
        verify(userDboMapper).toDomain(userEntity);
    }

    @Test
    void testFindByUsername_NotFound() {
        // Arrange
        String username = "nonexistent";

        when(userRepository.findByUsername(username)).thenReturn(Optional.empty());

        // Act
        User result = userPersistenceAdapter.findByUsername(username);

        // Assert
        assertNull(result);

        verify(userRepository).findByUsername(username);
    }

}