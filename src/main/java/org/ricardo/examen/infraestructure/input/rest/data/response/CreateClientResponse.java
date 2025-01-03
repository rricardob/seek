package org.ricardo.examen.infraestructure.input.rest.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateClientResponse {

    private UUID id;
    private String name;
    @JsonProperty("created_at")
    private LocalDate createdAt;
}
