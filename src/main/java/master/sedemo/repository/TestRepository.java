package master.sedemo.repository;

import master.sedemo.model.Test;
import org.springframework.data.jpa.repository.JpaRepository;

//Erstellen einer Test Repository Java Persistence API Instanz
public interface TestRepository extends JpaRepository<Test, Long> {
}
