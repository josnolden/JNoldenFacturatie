package nl.novi.jnoldenfacturatie.services;

import nl.novi.jnoldenfacturatie.dtos.AdresInputDto;
import nl.novi.jnoldenfacturatie.dtos.KlantInputDto;
import nl.novi.jnoldenfacturatie.models.Adres;
import nl.novi.jnoldenfacturatie.models.Klant;
import nl.novi.jnoldenfacturatie.repositories.AdresRepository;
import nl.novi.jnoldenfacturatie.repositories.KlantRepository;
import org.springframework.stereotype.Service;

@Service
public class KlantService {
    private AdresRepository adresRepository;
    private KlantRepository klantRepository;

    public KlantService(AdresRepository adresRepository, KlantRepository klantRepository){
        this.adresRepository = adresRepository;
        this.klantRepository = klantRepository;
    }

    public Long createKlant(KlantInputDto klantInput, AdresInputDto adresInput){
        var klant = new Klant();
        klant.setVoornaam(klantInput.getVoornaam());
        klant.setTussenvoegsel(klantInput.getTussenvoegsel());
        klant.setAchternaam(klantInput.getAchternaam());
        klant.setMailAdres(klantInput.getMailadres());
        klant.setTelefoonnummer(klantInput.getTelefoonnummer());
        var adres = new Adres();
        adres.setStraat(adresInput.getStraat());
        adres.setHuisnummer(adresInput.getHuisnummer());
        adres.setHuisnummerToevoeging(adresInput.getHuisnummerToevoeging());
        adres.setWoonplaats(adres.getWoonplaats());
        adres.setLand(adresInput.getLand());
        klant.setKlantAdres(adres);
        this.klantRepository.save(klant);
        //this.adresRepository.save(klant.getKlantAdres());
        return klant.getKlantId();
    }
}
