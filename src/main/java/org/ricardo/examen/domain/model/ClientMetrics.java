package org.ricardo.examen.domain.model;

import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class ClientMetrics {

    double average;
    double standardDeviation;
}
