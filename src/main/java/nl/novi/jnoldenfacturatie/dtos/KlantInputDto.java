package nl.novi.jnoldenfacturatie.dtos;

import jakarta.validation.constraints.*;

public class KlantInputDto extends KlantBaseDto {
    private AdresInputDto adres;

    public KlantInputDto(String voornaam, String tussenvoegsel, String achternaam, String mailadres, String telefoonnummer, AdresInputDto adres){
        super(voornaam, tussenvoegsel, achternaam, mailadres, telefoonnummer);
        this.adres = adres;
    }

    public KlantInputDto(String voornaam, String achternaam, String mailadres, String telefoonnummer, AdresInputDto adres){
        super(voornaam, achternaam, mailadres, telefoonnummer);
        this.adres = adres;
    }

    public KlantInputDto(){
        super();
    }

    public AdresInputDto getAdres() {
        return adres;
    }

    public void setAdres(AdresInputDto adres) {
        this.adres = adres;
    }
}
