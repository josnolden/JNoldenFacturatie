package nl.novi.jnoldenfacturatie.repositories;

import nl.novi.jnoldenfacturatie.models.Gebruiker;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GebruikerRepository extends JpaRepository<Gebruiker, String> {
}
