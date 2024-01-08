package nl.novi.jnoldenfacturatie.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class factuur {
    @Id
    @GeneratedValue
    Long factuurId;
    @ManyToOne
    private klant factuurKlant;
    private Date factuurDatum;
    private Date betaalDatum;
    private Double subTotaal;
    private Double btwTotaal;
    private Double korting;
    private Double totaalPrijs;
    @OneToMany(mappedBy = "basisFactuur")
    List<orderRegel> orderRegels;
}
