package nl.novi.jnoldenfacturatie.models;

import jakarta.persistence.*;

import java.io.Serializable;

@Entity
public class GebruikersRol implements Serializable {
    @Id
    private String gebruikersRol;

    public GebruikersRol(){

    }
    public GebruikersRol(String gebruikersRol){
        this.gebruikersRol = gebruikersRol;
    }

    public String getGebruikersRol() {
        return gebruikersRol;
    }

    public void setGebruikersRol(String gebruikersRol) {
        this.gebruikersRol = gebruikersRol;
    }
}
