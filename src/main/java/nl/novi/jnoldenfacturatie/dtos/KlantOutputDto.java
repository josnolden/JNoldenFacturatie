package nl.novi.jnoldenfacturatie.dtos;

public class KlantOutputDto extends KlantBaseDto {
    private Long klantId;
    private AdresInputDto adres;

    public KlantOutputDto(Long klantId, String voornaam, String tussenvoegsel, String achternaam, String mailadres, String telefoonnummer, AdresInputDto adres){
        super(voornaam, tussenvoegsel, achternaam, mailadres, telefoonnummer);
        this.klantId = klantId;
        this.adres = adres;
    }

    public KlantOutputDto(){
        super();
    }

    public Long getKlantId() {
        return klantId;
    }

    public void setKlantId(Long klantId) {
        this.klantId = klantId;
    }

    public AdresInputDto getAdres() {
        return adres;
    }

    public void setAdres(AdresInputDto adres) {
        this.adres = adres;
    }
}
