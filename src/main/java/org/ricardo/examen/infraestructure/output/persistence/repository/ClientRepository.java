package org.ricardo.examen.infraestructure.output.persistence.repository;

import org.ricardo.examen.infraestructure.output.persistence.entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<ClientEntity, Long> {
}
