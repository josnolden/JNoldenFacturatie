package nl.novi.jnoldenfacturatie.repositories;

import nl.novi.jnoldenfacturatie.models.Factuur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactuurRepository extends JpaRepository<Factuur, Long> {
}
