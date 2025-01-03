package org.ricardo.examen.application.port.output;

import org.ricardo.examen.domain.model.User;

public interface UserPersistencePort {

    User create(User user);

    User findByUsername(String username);
}
