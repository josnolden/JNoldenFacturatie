package nl.novi.jnoldenfacturatie.services;

import nl.novi.jnoldenfacturatie.dtos.AdresInputDto;
import nl.novi.jnoldenfacturatie.dtos.KlantInputDto;
import nl.novi.jnoldenfacturatie.dtos.KlantOutputDto;
import nl.novi.jnoldenfacturatie.models.Adres;
import nl.novi.jnoldenfacturatie.models.Klant;
import nl.novi.jnoldenfacturatie.repositories.AdresRepository;
import nl.novi.jnoldenfacturatie.repositories.KlantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class KlantService {
    private AdresRepository adresRepository;
    private KlantRepository klantRepository;

    public KlantService(AdresRepository adresRepository, KlantRepository klantRepository){
        this.adresRepository = adresRepository;
        this.klantRepository = klantRepository;
    }

    public List<KlantOutputDto> getAllKlanten(){
        List<Klant> klantData = klantRepository.findAll();
        List<KlantOutputDto> klanten = new ArrayList<>();
        for(Klant klant : klantData){
            klanten.add(transferKlantToDto(klant));
        }
        return klanten;
    }

    public Long createKlant(KlantInputDto klantInput){
        Klant klant = new Klant();
        klant.setVoornaam(klantInput.getVoornaam());
        klant.setTussenvoegsel(klantInput.getTussenvoegsel());
        klant.setAchternaam(klantInput.getAchternaam());
        klant.setMailAdres(klantInput.getMailadres());
        klant.setTelefoonnummer(klantInput.getTelefoonnummer());
        AdresInputDto adresInput = klantInput.getAdres();
        Adres adres = new Adres();
        adres.setStraat(adresInput.getStraat());
        adres.setHuisnummer(adresInput.getHuisnummer());
        adres.setHuisnummerToevoeging(adresInput.getHuisnummerToevoeging());
        adres.setWoonplaats(adresInput.getWoonplaats());
        adres.setLand(adresInput.getLand());
        klant.setKlantAdres(adres);
        this.klantRepository.save(klant);
        //this.adresRepository.save(adres);
        return klant.getKlantId();
    }

    public KlantOutputDto transferKlantToDto(Klant klantData){
        KlantOutputDto klantDto = new KlantOutputDto();
        klantDto.setKlantId(klantData.getKlantId());
        klantDto.setVoornaam(klantData.getVoornaam());
        klantDto.setTussenvoegsel(klantData.getTussenvoegsel());
        klantDto.setAchternaam(klantData.getAchternaam());
        klantDto.setMailadres(klantData.getMailAdres());
        klantDto.setTelefoonnummer(klantData.getTelefoonnummer());
        AdresInputDto adresInput = transferAdresToDto(klantData.getKlantAdres());
        klantDto.setAdres(adresInput);
        return klantDto;
    }

    public AdresInputDto transferAdresToDto(Adres adresData){
        AdresInputDto adresDto = new AdresInputDto();
        adresDto.setStraat(adresData.getStraat());
        adresDto.setHuisnummer(adresData.getHuisnummer());
        adresDto.setHuisnummerToevoeging(adresData.getHuisnummerToevoeging());
        adresDto.setWoonplaats(adresData.getWoonplaats());
        adresDto.setLand(adresData.getLand());
        return adresDto;
    }
}
