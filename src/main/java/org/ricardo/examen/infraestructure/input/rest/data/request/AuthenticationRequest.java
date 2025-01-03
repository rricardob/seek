package org.ricardo.examen.infraestructure.input.rest.data.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationRequest {
    String username;
    String password;
}
