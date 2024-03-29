package nl.novi.jnoldenfacturatie.models;

import jakarta.persistence.*;

@Entity
public class Klant {
    @Id
    @GeneratedValue
    Long klantId;
    private String voornaam;
    private String tussenvoegsel;
    private String achternaam;
    private String mailAdres;
    private String telefoonnummer;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    private Adres klantAdres;

    public Long getKlantId() {
        return klantId;
    }

    public void setKlantId(Long klantId) {
        this.klantId = klantId;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getTussenvoegsel() {
        return tussenvoegsel;
    }

    public void setTussenvoegsel(String tussenvoegsel) {
        this.tussenvoegsel = tussenvoegsel;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getMailAdres() {
        return mailAdres;
    }

    public void setMailAdres(String mailAdres) {
        this.mailAdres = mailAdres;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public Adres getKlantAdres() {
        return klantAdres;
    }

    public void setKlantAdres(Adres klantAdres) {
        this.klantAdres = klantAdres;
    }
}
