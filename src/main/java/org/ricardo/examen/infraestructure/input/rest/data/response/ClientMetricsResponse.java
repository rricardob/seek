package org.ricardo.examen.infraestructure.input.rest.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class ClientMetricsResponse {

    double average;
    @JsonProperty("standard_deviation")
    double standardDeviation;
}
