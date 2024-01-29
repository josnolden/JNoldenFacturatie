package nl.novi.jnoldenfacturatie.dtos;

public class GebruikerInputDto extends GebruikerBaseDto {
    private String wachtwoord;

    public GebruikerInputDto(String gebruikersnaam, String wachtwoord){
        super(gebruikersnaam);
        this.wachtwoord = wachtwoord;
    }

    public GebruikerInputDto(){
        super();
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }
}
