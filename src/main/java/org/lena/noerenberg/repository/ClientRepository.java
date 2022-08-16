package org.lena.noerenberg.repository;

import org.lena.noerenberg.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClientRepository extends JpaRepository<Client, Long> {
}
