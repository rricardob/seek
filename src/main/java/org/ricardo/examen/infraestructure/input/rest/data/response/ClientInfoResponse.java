package org.ricardo.examen.infraestructure.input.rest.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.ricardo.examen.domain.model.Client;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class ClientInfoResponse {

    private List<Client> clients;
    @JsonProperty("life_expectancy")
    private double lifeExpectancy;
}
