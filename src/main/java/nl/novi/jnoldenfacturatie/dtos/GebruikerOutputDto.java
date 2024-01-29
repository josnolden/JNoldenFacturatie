package nl.novi.jnoldenfacturatie.dtos;

public class GebruikerOutputDto extends GebruikerBaseDto {
    private String gebruikersRol;
    public GebruikerOutputDto(String gebuikersnaam, String gebruikersRol){
        super(gebuikersnaam);
        this.gebruikersRol = gebruikersRol;
    }
    public GebruikerOutputDto(){
        super();
    }

    public String getGebruikersRol() {
        return gebruikersRol;
    }

    public void setGebruikersRol(String gebruikersRol) {
        this.gebruikersRol = gebruikersRol;
    }
}
