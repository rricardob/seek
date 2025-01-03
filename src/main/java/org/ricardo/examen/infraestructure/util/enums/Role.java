package org.ricardo.examen.infraestructure.util.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;

@Getter
@AllArgsConstructor
public enum Role {
    CLIENT(List.of(Permission.READ)),
    ADMINISTRATOR(Arrays.asList(Permission.WRITE,Permission.READ));

    private final List<Permission> permission;
}
