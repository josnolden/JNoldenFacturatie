package nl.novi.jnoldenfacturatie.dtos;

import jakarta.validation.constraints.*;

public class OrderRegelInputDto {
    @NotNull(message = "Artikel is verplicht")
    private Long artikelId;
    @Min(1)
    private Integer aantal;

    public OrderRegelInputDto(Long artikelId, Integer aantal){
        this.artikelId = artikelId;
        this.aantal = aantal;
    }

    public Long getArtikelId() {
        return artikelId;
    }

    public void setArtikelId(Long artikelId) {
        this.artikelId = artikelId;
    }

    public Integer getAantal() {
        return aantal;
    }

    public void setAantal(Integer aantal) {
        this.aantal = aantal;
    }
}
