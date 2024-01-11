package nl.novi.jnoldenfacturatie.models;

import jakarta.persistence.*;

@Entity
public class Adres {
    @Id
    @GeneratedValue
    Long adresId;
    private String straat;
    private Integer huisnummer;
    private String huisnummerToevoeging;
    private String woonplaats;
    private String land;

    @OneToOne(mappedBy = "klantAdres")
    Klant klant;

    public Long getAdresId() {
        return adresId;
    }

    public void setAdresId(Long adresId) {
        this.adresId = adresId;
    }

    public String getStraat() {
        return straat;
    }

    public void setStraat(String straat) {
        this.straat = straat;
    }

    public Integer getHuisnummer() {
        return huisnummer;
    }

    public void setHuisnummer(Integer huisnummer) {
        this.huisnummer = huisnummer;
    }

    public String getHuisnummerToevoeging() {
        return huisnummerToevoeging;
    }

    public void setHuisnummerToevoeging(String huisnummerToevoeging) {
        this.huisnummerToevoeging = huisnummerToevoeging;
    }

    public String getWoonplaats() {
        return woonplaats;
    }

    public void setWoonplaats(String woonplaats) {
        this.woonplaats = woonplaats;
    }

    public String getLand() {
        return land;
    }

    public void setLand(String land) {
        this.land = land;
    }

    public Klant getKlant(){
        return klant;
    }

    public void setKlant(Klant klant) {
        this.klant = klant;
    }
}
