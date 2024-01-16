package nl.novi.jnoldenfacturatie.dtos;

public class OrderRegelSummaryDto {
    private Integer regelNummer;
    private String artikelNaam;
    private Integer aantal;
    private Double btw;
    private Double regelPrijs;

    public OrderRegelSummaryDto(Integer regelNummer, String artikelNaam, Integer aantal, Double btw, Double regelPrijs){
        this.regelNummer = regelNummer;
        this.artikelNaam = artikelNaam;
        this.aantal = aantal;
        this.btw = btw;
        this.regelPrijs = regelPrijs;
    }

    public Integer getRegelNummer() {
        return regelNummer;
    }

    public void setRegelNummer(Integer regelNummer) {
        this.regelNummer = regelNummer;
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
