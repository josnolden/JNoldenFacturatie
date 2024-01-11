package nl.novi.jnoldenfacturatie.repositories;

import nl.novi.jnoldenfacturatie.models.Artikel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArtikelRepository extends JpaRepository<Artikel, Long> {
}
