package nl.novi.jnoldenfacturatie.dtos;

public class ArtikelOutputDto extends ArtikelInputDto {
    private Long id;

    public ArtikelOutputDto(Long id, String naam, Double prijs, Integer btwPercentage, String categorie){
        super(naam, prijs, btwPercentage, categorie);
        this.id = id;
    }

    public ArtikelOutputDto(){
        super();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
