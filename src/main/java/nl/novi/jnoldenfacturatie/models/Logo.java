package nl.novi.jnoldenfacturatie.models;

import jakarta.persistence.*;

@Entity
public class Logo {
    @Id
    private Long logoId;

    @Lob
    private byte[] afbeeldingData;

    public Logo(){

    }

    public Long getLogoId() {
        return logoId;
    }

    public void setLogoId(Long logoId) {
        this.logoId = logoId;
    }

    public byte[] getAfbeeldingData() {
        return afbeeldingData;
    }

    public void setAfbeeldingData(byte[] afbeeldingData) {
        this.afbeeldingData = afbeeldingData;
    }
}
