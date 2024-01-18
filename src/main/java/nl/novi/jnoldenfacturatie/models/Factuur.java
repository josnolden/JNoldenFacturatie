package nl.novi.jnoldenfacturatie.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class Factuur {
    @Id
    @GeneratedValue
    Long factuurId;
    @ManyToOne
    private Klant factuurKlant;
    private Date factuurDatum;
    private Date betaalDatum;
    private Double subTotaal;
    private Double btwTotaal;
    private Integer kortingPercentage;
    private Double korting;
    private Double totaalPrijs;
    @OneToMany(mappedBy = "basisFactuur")
    @OrderBy
    List<OrderRegel> orderRegels;

    public Long getFactuurId() {
        return factuurId;
    }

    public void setFactuurId(Long factuurId) {
        this.factuurId = factuurId;
    }

    public Klant getFactuurKlant() {
        return factuurKlant;
    }

    public void setFactuurKlant(Klant factuurKlant) {
        this.factuurKlant = factuurKlant;
    }

    public Date getFactuurDatum() {
        return factuurDatum;
    }

    public void setFactuurDatum(Date factuurDatum) {
        this.factuurDatum = factuurDatum;
    }

    public Date getBetaalDatum() {
        return betaalDatum;
    }

    public void setBetaalDatum(Date betaalDatum) {
        this.betaalDatum = betaalDatum;
    }

    public Double getSubTotaal() {
        return subTotaal;
    }

    public void setSubTotaal(Double subTotaal) {
        this.subTotaal = Math.round(subTotaal*100.0)/100.0;
    }

    public Double getBtwTotaal() {
        return btwTotaal;
    }

    public void setBtwTotaal(Double btwTotaal) {
        this.btwTotaal = Math.round(btwTotaal*100.0)/100.0;
    }

    public Integer getKortingPercentage() {
        return kortingPercentage;
    }

    public void setKortingPercentage(Integer kortingPercentage) {
        this.kortingPercentage = kortingPercentage;
    }

    public Double getKorting() {
        return korting;
    }

    public void setKorting(Double korting) {
        this.korting = Math.round(korting*100.0)/100.0;
    }

    public Double getTotaalPrijs() {
        return totaalPrijs;
    }

    public void setTotaalPrijs(Double totaalPrijs) {
        this.totaalPrijs = Math.round(totaalPrijs*100.0)/100.0;
    }

    public List<OrderRegel> getOrderRegels() {
        return orderRegels;
    }

    public void setOrderRegels(List<OrderRegel> orderRegels) {
        this.orderRegels = orderRegels;
        for(OrderRegel orderRegel : orderRegels){
            orderRegel.setBasisFactuur(this);
        }
    }
}
