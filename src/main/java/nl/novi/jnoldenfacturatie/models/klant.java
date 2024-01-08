package nl.novi.jnoldenfacturatie.models;

import jakarta.persistence.*;

@Entity
public class klant {
    @Id
    @GeneratedValue
    Long klantId;
    private String voornaam;
    private String tussenvoegsel;
    private String achternaam;
    private String mailAdres;
    private String telefoonnummer;
    @OneToOne
    private adres klantAdres;
}
