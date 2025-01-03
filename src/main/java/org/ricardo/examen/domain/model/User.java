package org.ricardo.examen.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.ricardo.examen.infraestructure.util.enums.Role;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class User {
    private Long id;
    private String name;
    private String username;
    private String password;
    private Role role;
}
