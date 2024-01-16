package nl.novi.jnoldenfacturatie.dtos;

public class OrderRegelSummaryDto {
    private String artikelNaam;
    private Integer aantal;
    private Double btw;
    private Double regelPrijs;

    public OrderRegelSummaryDto(String artikelNaam, Integer aantal, Double btw, Double regelPrijs){
        this.artikelNaam = artikelNaam;
        this.aantal = aantal;
        this.btw = btw;
        this.regelPrijs = regelPrijs;
    }

    public OrderRegelSummaryDto(){

    }

    public String getArtikelNaam() {
        return artikelNaam;
    }

    public void setArtikelNaam(String artikelNaam) {
        this.artikelNaam = artikelNaam;
    }

    public Integer getAantal() {
        return aantal;
    }

    public void setAantal(Integer aantal) {
        this.aantal = aantal;
    }

    public Double getBtw() {
        return btw;
    }

    public void setBtw(Double btw) {
        this.btw = btw;
    }

    public Double getRegelPrijs() {
        return regelPrijs;
    }

    public void setRegelPrijs(Double regelPrijs) {
        this.regelPrijs = regelPrijs;
    }
}
