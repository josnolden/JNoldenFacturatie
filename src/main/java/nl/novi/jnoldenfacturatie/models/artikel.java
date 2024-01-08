package nl.novi.jnoldenfacturatie.models;

import jakarta.persistence.*;

@Entity
public class artikel {
    @Id
    @GeneratedValue
    Long artikelId;
    private String naam;
    private Double prijs; //incl. btw
    private Integer btwPercentage;
    private String categorie;
}
