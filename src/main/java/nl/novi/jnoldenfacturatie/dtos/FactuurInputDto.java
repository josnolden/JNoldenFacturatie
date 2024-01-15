package nl.novi.jnoldenfacturatie.dtos;

import jakarta.validation.constraints.*;
import java.util.Date;

public class FactuurInputDto {

    @NotNull(message = "Datum is verplicht")
    private Date factuurDatum;
    private Date betaalDatum;
    @NotNull(message = "Klant is verplicht")
    private Long factuurKlant;
    @Max(100) // korting mag niet hoger dan 100% zijn
    @Min(0) // korting mag niet negatief zijn
    private Integer kortingPercentage;
    @Min(1)
    private OrderRegelInputDto[] orderRegels;

    public FactuurInputDto(Date factuurDatum, Date betaalDatum, Long klantId, Integer kortingPercentage, OrderRegelInputDto[] orderRegels){
        this.factuurDatum = factuurDatum;
        this.betaalDatum = betaalDatum;
        this.factuurKlant = klantId;
        this.kortingPercentage = kortingPercentage;
        this.orderRegels = orderRegels;
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

    public Long getFactuurKlant() {
        return factuurKlant;
    }

    public void setFactuurKlant(Long factuurKlant) {
        this.factuurKlant = factuurKlant;
    }

    public Integer getKortingPercentage() {
        return kortingPercentage;
    }

    public void setKortingPercentage(Integer kortingPercentage) {
        this.kortingPercentage = kortingPercentage;
    }

    public OrderRegelInputDto[] getOrderRegels() {
        return orderRegels;
    }

    public void setOrderRegels(OrderRegelInputDto[] orderRegels) {
        this.orderRegels = orderRegels;
    }
}
