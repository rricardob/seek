package org.ricardo.examen.domain.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.ricardo.examen.infraestructure.input.rest.data.request.AuthenticationRequest;
import org.ricardo.examen.infraestructure.input.rest.data.response.AuthenticationResponse;
import org.ricardo.examen.infraestructure.output.persistence.entity.UserEntity;
import org.ricardo.examen.infraestructure.output.persistence.repository.UserRepository;
import org.ricardo.examen.infraestructure.util.JwtHelper;
import org.ricardo.examen.infraestructure.util.enums.Role;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyMap;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class AuthenticationServiceTest {

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtHelper jwtService;

    @InjectMocks
    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testLoginSuccessful() {
        AuthenticationRequest authRequest = new AuthenticationRequest("username", "password");

        UserEntity user = new UserEntity();
        user.setUsername("username");
        user.setName("John Doe");
        user.setRole(Role.CLIENT);

        when(userRepository.findByUsername(authRequest.getUsername())).thenReturn(Optional.of(user));
        when(jwtService.generateToken(eq(user), anyMap())).thenReturn("mock-jwt-token");

        AuthenticationResponse response = authenticationService.login(authRequest);

        assertNotNull(response);
        assertEquals("mock-jwt-token", response.getJwt());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByUsername(authRequest.getUsername());
        verify(jwtService, times(1)).generateToken(eq(user), anyMap());
    }

    @Test
    void testLoginInvalidCredentials() {
        AuthenticationRequest authRequest = new AuthenticationRequest("username", "wrong-password");

        doThrow(new RuntimeException("Authentication failed"))
                .when(authenticationManager)
                .authenticate(any(UsernamePasswordAuthenticationToken.class));

        Exception exception = assertThrows(RuntimeException.class, () -> authenticationService.login(authRequest));
        assertEquals("Authentication failed", exception.getMessage());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, never()).findByUsername(anyString());
        verify(jwtService, never()).generateToken(any(UserEntity.class), anyMap());
    }

    @Test
    void testLoginUserNotFound() {
        AuthenticationRequest authRequest = new AuthenticationRequest("unknownUser", "password");

        when(userRepository.findByUsername(authRequest.getUsername())).thenReturn(Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> authenticationService.login(authRequest));
        assertEquals("No value present", exception.getMessage());

        verify(authenticationManager, times(1)).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(userRepository, times(1)).findByUsername(authRequest.getUsername());
        verify(jwtService, never()).generateToken(any(UserEntity.class), anyMap());
    }

}