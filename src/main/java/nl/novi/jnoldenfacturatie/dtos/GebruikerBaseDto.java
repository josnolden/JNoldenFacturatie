package nl.novi.jnoldenfacturatie.dtos;

public abstract class GebruikerBaseDto {
    private String gebruikersnaam;

    public GebruikerBaseDto(String gebruikersnaam){
        this.gebruikersnaam = gebruikersnaam;
    }

    public GebruikerBaseDto(){

    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }
}
