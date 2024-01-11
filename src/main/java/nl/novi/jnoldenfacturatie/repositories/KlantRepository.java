package nl.novi.jnoldenfacturatie.repositories;

import nl.novi.jnoldenfacturatie.models.Klant;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KlantRepository extends JpaRepository<Klant, Long> {
}
