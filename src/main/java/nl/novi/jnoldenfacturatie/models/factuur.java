package nl.novi.jnoldenfacturatie.models;

import jakarta.persistence.*;

@Entity
public class factuur {
    @Id
    @GeneratedValue
    Long id;
    private String name;
}
