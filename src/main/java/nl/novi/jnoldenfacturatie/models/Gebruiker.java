package nl.novi.jnoldenfacturatie.models;

import jakarta.persistence.*;

@Entity
public class Gebruiker {
    @Id
    private String gebruikersnaam;

    private String wachtwoord;

    @ManyToOne
    private GebruikersRol gebruikersRol;

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public GebruikersRol getGebruikersRol() {
        return gebruikersRol;
    }

    public void setGebruikersRol(GebruikersRol gebruikersRol) {
        this.gebruikersRol = gebruikersRol;
    }
}
