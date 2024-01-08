package nl.novi.jnoldenfacturatie.models;

import jakarta.persistence.*;

@Entity
public class orderRegel {
    @Id
    @GeneratedValue
    Long orderRegelId;
    @ManyToOne
    @MapsId("factuurId")
    private factuur basisFactuur;
    @ManyToOne
    private artikel orderArtikel;
    private Double prijs; //incl. btw
    private Double btw;

    public Long getOrderRegelId() {
        return orderRegelId;
    }

    public void setOrderRegelId(Long orderRegelId) {
        this.orderRegelId = orderRegelId;
    }

    public factuur getBasisFactuur() {
        return basisFactuur;
    }

    public void setBasisFactuur(factuur basisFactuur) {
        this.basisFactuur = basisFactuur;
    }

    public artikel getOrderArtikel() {
        return orderArtikel;
    }

    public void setOrderArtikel(artikel orderArtikel) {
        this.orderArtikel = orderArtikel;
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
