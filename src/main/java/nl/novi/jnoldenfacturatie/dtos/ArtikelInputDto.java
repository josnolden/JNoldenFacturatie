package nl.novi.jnoldenfacturatie.dtos;

import jakarta.validation.constraints.*;

public class ArtikelInputDto {
    @NotNull(message = "ArtikelNaam is verplicht")
    private String naam;
    @NotNull(message = "Prijs is verplicht") // geen minimum, kan prijs van nul hebben
    private Double prijs; //incl. btw
    @Max(100)
    @Min(0)
    private Integer btwPercentage;
    private String categorie;

    public ArtikelInputDto(String naam, Double prijs, Integer btwPercentage, String categorie){
        this.naam = naam;
        this.prijs = prijs;
        this.btwPercentage = btwPercentage;
        this.categorie = categorie;
    }

    public ArtikelInputDto(){

    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public Double getPrijs() {
        return prijs;
    }

    public void setPrijs(Double prijs) {
        this.prijs = prijs;
    }

    public Integer getBtwPercentage() {
        return btwPercentage;
    }

    public void setBtwPercentage(Integer btwPercentage) {
        this.btwPercentage = btwPercentage;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }
}
