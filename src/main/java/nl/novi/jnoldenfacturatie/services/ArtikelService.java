package nl.novi.jnoldenfacturatie.services;
import nl.novi.jnoldenfacturatie.dtos.ArtikelInputDto;
import nl.novi.jnoldenfacturatie.models.Artikel;
import nl.novi.jnoldenfacturatie.repositories.ArtikelRepository;
import org.springframework.stereotype.Service;

@Service
public class ArtikelService {
    private ArtikelRepository artikelRepository;

    public ArtikelService(ArtikelRepository repository){
        this.artikelRepository = repository;
    }

    public Long createArtikel(ArtikelInputDto artikelInput){
        var artikel = new Artikel();
        artikel.setNaam(artikelInput.getNaam());
        artikel.setCategorie(artikelInput.getCategorie());
        artikel.setPrijs(artikelInput.getPrijs());
        artikel.setBtwPercentage(artikelInput.getBtwPercentage());
        this.artikelRepository.save(artikel);
        return artikel.getArtikelId();
    }
}
