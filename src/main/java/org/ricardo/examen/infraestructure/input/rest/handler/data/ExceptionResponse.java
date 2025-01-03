package org.ricardo.examen.infraestructure.input.rest.handler.data;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class ExceptionResponse {
    String message;
}
