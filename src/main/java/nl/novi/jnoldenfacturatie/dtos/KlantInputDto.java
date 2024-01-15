package nl.novi.jnoldenfacturatie.dtos;

import jakarta.validation.constraints.*;

public class KlantInputDto {
    @NotNull(message = "Voornaam is verplicht")
    private String voornaam;
    private String tussenvoegsel;
    @NotNull(message = "Achternaam is verplicht")
    private String achternaam;
    private String mailadres;
    private String telefoonnummer;
    private AdresInputDto adres;

    public KlantInputDto(String voornaam, String tussenvoegsel, String achternaam, String mailadres, String telefoonnummer, AdresInputDto adres){
        this.voornaam = voornaam;
        this.tussenvoegsel = tussenvoegsel;
        this.achternaam = achternaam;
        this.mailadres = mailadres;
        this.telefoonnummer = telefoonnummer;
        this.adres = adres;
    }

    public KlantInputDto(String voornaam, String achternaam, String mailadres, String telefoonnummer, AdresInputDto adres){
        this.voornaam = voornaam;
        this.achternaam = achternaam;
        this.mailadres = mailadres;
        this.telefoonnummer = telefoonnummer;
        this.adres = adres;
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

    public String getMailadres() {
        return mailadres;
    }

    public void setMailadres(String mailadres) {
        this.mailadres = mailadres;
    }

    public String getTelefoonnummer() {
        return telefoonnummer;
    }

    public void setTelefoonnummer(String telefoonnummer) {
        this.telefoonnummer = telefoonnummer;
    }

    public AdresInputDto getAdres() {
        return adres;
    }

    public void setAdres(AdresInputDto adres) {
        this.adres = adres;
    }
}
