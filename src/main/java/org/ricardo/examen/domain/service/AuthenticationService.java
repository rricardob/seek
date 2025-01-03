package org.ricardo.examen.domain.service;


import lombok.RequiredArgsConstructor;
import org.ricardo.examen.application.port.input.LoginUseCase;
import org.ricardo.examen.infraestructure.input.rest.data.request.AuthenticationRequest;
import org.ricardo.examen.infraestructure.input.rest.data.response.AuthenticationResponse;
import org.ricardo.examen.infraestructure.output.persistence.entity.UserEntity;
import org.ricardo.examen.infraestructure.output.persistence.repository.UserRepository;
import org.ricardo.examen.infraestructure.util.JwtHelper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AuthenticationService implements LoginUseCase {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final JwtHelper jwtService;

    private Map<String, Object> generateExtraClaims(UserEntity user) {
        Map<String, Object> extractClaims = new HashMap<>();
        extractClaims.put("name", user.getName());
        extractClaims.put("role", user.getRole().name());
        extractClaims.put("permission", user.getAuthorities());
        return extractClaims;
    }

    @Override
    public AuthenticationResponse login(AuthenticationRequest authRequest) {
        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                authRequest.getUsername(), authRequest.getPassword()
        );
        authenticationManager.authenticate(authToken);

        UserEntity user = userRepository.findByUsername(authRequest.getUsername()).get();

        String jwt = jwtService.generateToken(user, generateExtraClaims(user));
        return new AuthenticationResponse(jwt);
    }
}
