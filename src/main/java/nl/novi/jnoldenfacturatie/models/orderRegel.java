package nl.novi.jnoldenfacturatie.models;

import jakarta.persistence.*;

@Entity
public class orderRegel {
    @Id
    @GeneratedValue
    Long orderRegelId;
    @ManyToOne
    @MapsId("factuurId")
    private factuur basisFactuur;
    @ManyToOne
    private artikel orderArtikel;
    private Double prijs; //incl. btw
    private Double btw;
}
