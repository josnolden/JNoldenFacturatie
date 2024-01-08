package nl.novi.jnoldenfacturatie.models;

import jakarta.persistence.*;

import java.util.Date;
import java.util.List;

@Entity
public class factuur {
    @Id
    @GeneratedValue
    Long factuurId;
    @ManyToOne
    private klant factuurKlant;
    private Date factuurDatum;
    private Date betaalDatum;
    private Double subTotaal;
    private Double btwTotaal;
    private Double korting;
    private Double totaalPrijs;
    @OneToMany(mappedBy = "basisFactuur")
    List<orderRegel> orderRegels;

    public Long getFactuurId() {
        return factuurId;
    }

    public void setFactuurId(Long factuurId) {
        this.factuurId = factuurId;
    }

    public klant getFactuurKlant() {
        return factuurKlant;
    }

    public void setFactuurKlant(klant factuurKlant) {
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

    public List<orderRegel> getOrderRegels() {
        return orderRegels;
    }

    public void setOrderRegels(List<orderRegel> orderRegels) {
        this.orderRegels = orderRegels;
    }
}
