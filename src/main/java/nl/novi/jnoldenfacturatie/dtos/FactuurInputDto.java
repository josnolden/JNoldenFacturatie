package nl.novi.jnoldenfacturatie.dtos;

import jakarta.validation.constraints.*;
import java.util.Date;
import java.util.List;

public class FactuurInputDto extends FactuurBaseDto {
    @NotNull(message = "Klant is verplicht")
    private Long klantId;
    @Max(100) // korting mag niet hoger dan 100% zijn
    @Min(0) // korting mag niet negatief zijn
    private Integer kortingPercentage;
    @Size(message = "Factuur moet minstens een orderregel bevatten", min = 1)
    private List<OrderRegelInputDto> orderRegels;

    public FactuurInputDto(Date factuurDatum, Date betaalDatum, Long klantId, Integer kortingPercentage, List<OrderRegelInputDto> orderRegels){
        super(factuurDatum, betaalDatum);
        this.kortingPercentage = kortingPercentage;
        this.klantId = klantId;
        this.orderRegels = orderRegels;
    }

    public FactuurInputDto(){
        super();
    }

    public Long getKlantId() {
        return klantId;
    }

    public void setKlantId(Long klantId) {
        this.klantId = klantId;
    }

    public Integer getKortingPercentage() {
        return kortingPercentage;
    }

    public void setKortingPercentage(Integer kortingPercentage) {
        this.kortingPercentage = kortingPercentage;
    }

    public List<OrderRegelInputDto> getOrderRegels() {
        return orderRegels;
    }

    public void setOrderRegels(List<OrderRegelInputDto> orderRegels) {
        this.orderRegels = orderRegels;
    }
}
