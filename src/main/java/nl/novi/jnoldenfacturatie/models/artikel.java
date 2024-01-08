package nl.novi.jnoldenfacturatie.models;

import jakarta.persistence.*;

@Entity
public class artikel {
    @Id
    @GeneratedValue
    Long artikelId;
    private String naam;
    private Double prijs; //incl. btw
    private Integer btwPercentage;
    private String categorie;

    public Long getArtikelId() {
        return artikelId;
    }

    public void setArtikelId(Long artikelId) {
        this.artikelId = artikelId;
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
