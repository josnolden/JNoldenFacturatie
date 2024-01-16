package nl.novi.jnoldenfacturatie.dtos;

import jakarta.validation.constraints.*;
import java.util.Date;

public class FactuurBaseDto {
    @NotNull(message = "Datum is verplicht")
    private Date factuurDatum;
    private Date betaalDatum;

    public FactuurBaseDto(Date factuurDatum, Date betaalDatum){
        this.factuurDatum = factuurDatum;
        this.betaalDatum = betaalDatum;
    }

    public FactuurBaseDto(){

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
}
