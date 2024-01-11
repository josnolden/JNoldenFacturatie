package nl.novi.jnoldenfacturatie.repositories;

import nl.novi.jnoldenfacturatie.models.OrderRegel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRegelRepository extends JpaRepository<OrderRegel, Long> {
}
