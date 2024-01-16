package nl.novi.jnoldenfacturatie.dtos;

import java.util.Date;
import java.util.List;

public class FactuurOutputDto extends FactuurBaseDto{
    Long factuurNummer;
    KlantOutputDto factuurKlant;
    List<OrderRegelSummaryDto> orderRegels;
    private Double subTotaal;
    private Double btwTotaal;
    private Double korting;
    private Double totaalPrijs;

    public FactuurOutputDto(Long id, Date factuurDatum, Date betaalDatum, KlantOutputDto factuurKlant, List<OrderRegelSummaryDto> orderRegels, Double subTotaal, Double btwTotaal, Double korting, Double totaalPrijs){
        super(factuurDatum, betaalDatum);
        this.factuurNummer = id;
        this.factuurKlant = factuurKlant;
        this.orderRegels = orderRegels;
        this.subTotaal = subTotaal;
        this.btwTotaal = btwTotaal;
        this.korting = korting;
        this.totaalPrijs = totaalPrijs;
    }

    public FactuurOutputDto(){
        super();
    }

    public Long getFactuurNummer() {
        return factuurNummer;
    }

    public void setFactuurNummer(Long factuurNummer) {
        this.factuurNummer = factuurNummer;
    }

    public KlantOutputDto getFactuurKlant() {
        return factuurKlant;
    }

    public void setFactuurKlant(KlantOutputDto factuurKlant) {
        this.factuurKlant = factuurKlant;
    }

    public List<OrderRegelSummaryDto> getOrderRegels() {
        return orderRegels;
    }

    public void setOrderRegels(List<OrderRegelSummaryDto> orderRegels) {
        this.orderRegels = orderRegels;
    }

    public Double getSubTotaal() {
        return subTotaal;
    }

    public void setSubTotaal(Double subTotaal) {
        this.subTotaal = subTotaal;
    }

    public Double getBtwTotaal() {
        return btwTotaal;
    }

    public void setBtwTotaal(Double btwTotaal) {
        this.btwTotaal = btwTotaal;
    }

    public Double getKorting() {
        return korting;
    }

    public void setKorting(Double korting) {
        this.korting = korting;
    }

    public Double getTotaalPrijs() {
        return totaalPrijs;
    }

    public void setTotaalPrijs(Double totaalPrijs) {
        this.totaalPrijs = totaalPrijs;
    }
}
