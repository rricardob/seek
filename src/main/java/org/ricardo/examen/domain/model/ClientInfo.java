package org.ricardo.examen.domain.model;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Builder
public class ClientInfo {

    private List<Client> clients;
    private double lifeExpectancy;
}
