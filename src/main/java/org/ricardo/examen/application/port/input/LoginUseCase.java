package org.ricardo.examen.application.port.input;

import org.ricardo.examen.infraestructure.input.rest.data.request.AuthenticationRequest;
import org.ricardo.examen.infraestructure.input.rest.data.response.AuthenticationResponse;

public interface LoginUseCase {
    AuthenticationResponse login(AuthenticationRequest authenticationRequest);
}
