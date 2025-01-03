package org.ricardo.examen.infraestructure.input.rest;


import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.ricardo.examen.application.port.input.LoginUseCase;
import org.ricardo.examen.infraestructure.input.rest.data.request.AuthenticationRequest;
import org.ricardo.examen.infraestructure.input.rest.data.response.AuthenticationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationRestAdapter {

    private final LoginUseCase loginUseCase;

    @PreAuthorize("permitAll")
    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody @Valid AuthenticationRequest authRequest) {
        return ResponseEntity.ok(loginUseCase.login(authRequest));
    }
}
