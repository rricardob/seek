package org.ricardo.examen.infraestructure.input.rest.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientRequestDto {
    @NotEmpty(message = "Debe Ingresar un nombre")
    private String name;
    @JsonProperty("first_name")
    @NotEmpty(message = "Debe ingresar apellido paterno")
    private String firstName;
    @JsonProperty("last_name")
    @NotEmpty(message = "Debe ingresar apellido materno")
    private String lastName;
    @JsonProperty("birth_date")
    @NotNull(message = "la fecha de nacimiento no puede ser nulo")
    private LocalDate birthDate;

}
