package nl.novi.jnoldenfacturatie.dtos;

import jakarta.validation.constraints.*;

public class AdresInputDto {
    @NotNull(message = "Straatnaam is verplicht")
    private String straat;
    @Min(0)
    private Integer huisnummer;
    private String huisnummerToevoeging;
    @NotNull(message = "Woonplaats is verplicht")
    private String woonplaats;
    @NotNull(message = "Land is verplicht")
    private String land;

    public AdresInputDto(String straat, Integer huisnummer, String huisnummerToevoeging, String woonplaats, String land){
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.huisnummerToevoeging = huisnummerToevoeging;
        this.woonplaats = woonplaats;
        this.land = land;
    }

    public AdresInputDto(String straat, Integer huisnummer, String woonplaats, String land){
        this.straat = straat;
        this.huisnummer = huisnummer;
        this.woonplaats = woonplaats;
        this.land = land;
    }

    public AdresInputDto(){

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
}
