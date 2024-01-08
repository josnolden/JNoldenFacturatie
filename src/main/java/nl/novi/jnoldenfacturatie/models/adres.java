package nl.novi.jnoldenfacturatie.models;

import jakarta.persistence.*;

@Entity
public class adres {
    @Id
    @GeneratedValue
    Long adresId;
    private String straat;
    private Integer huisnummer;
    private String huisnummerToevoeging;
    private String woonplaats;
    private String land;
}
