package org.lena.noerenberg.repository;

import org.lena.noerenberg.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TestRepository extends JpaRepository<Test, Long> {
}
