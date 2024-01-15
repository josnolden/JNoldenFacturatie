package nl.novi.jnoldenfacturatie.models;

import jakarta.persistence.*;

@Entity
public class OrderRegel {
    @Id
    @GeneratedValue
    Long orderRegelId;
    @ManyToOne
    private Factuur basisFactuur;
    @ManyToOne
    private Artikel orderArtikel;
    private Integer aantal;
    private Double prijs; //incl. btw
    private Double btw;

    public Long getOrderRegelId() {
        return orderRegelId;
    }

    public void setOrderRegelId(Long orderRegelId) {
        this.orderRegelId = orderRegelId;
    }

    public Factuur getBasisFactuur() {
        return basisFactuur;
    }

    public void setBasisFactuur(Factuur basisFactuur) {
        this.basisFactuur = basisFactuur;
    }

    public Artikel getOrderArtikel() {
        return orderArtikel;
    }

    public void setOrderArtikel(Artikel orderArtikel) {
        this.orderArtikel = orderArtikel;
    }

    public Integer getAantal() {
        return aantal;
    }

    public void setAantal(Integer aantal) {
        this.aantal = aantal;
    }

    public Double getPrijs() {
        return prijs;
    }

    public void setPrijs(Double prijs) {
        this.prijs = prijs;
    }

    public Double getBtw() {
        return btw;
    }

    public void setBtw(Double btw) {
        this.btw = btw;
    }
}
