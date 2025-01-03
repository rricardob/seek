package org.ricardo.examen.domain.model;

import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@ToString
public class Client {
    private Long id;
    private String name;
    private String firstName;
    private String lastName;
    private int age;
    private LocalDate birthDate;
}
