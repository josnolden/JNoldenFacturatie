package nl.novi.jnoldenfacturatie.services;

import nl.novi.jnoldenfacturatie.dtos.AdresInputDto;
import nl.novi.jnoldenfacturatie.dtos.KlantInputDto;
import nl.novi.jnoldenfacturatie.dtos.KlantOutputDto;
import nl.novi.jnoldenfacturatie.exceptions.InExistingFactuurException;
import nl.novi.jnoldenfacturatie.exceptions.NotFoundException;
import nl.novi.jnoldenfacturatie.models.Adres;
import nl.novi.jnoldenfacturatie.models.Factuur;
import nl.novi.jnoldenfacturatie.models.Klant;
import nl.novi.jnoldenfacturatie.repositories.AdresRepository;
import nl.novi.jnoldenfacturatie.repositories.FactuurRepository;
import nl.novi.jnoldenfacturatie.repositories.KlantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class KlantService {
    private KlantRepository klantRepository;
    private FactuurRepository factuurRepository;

    public KlantService(KlantRepository klantRepository, FactuurRepository factuurRepository){
        this.klantRepository = klantRepository;
        this.factuurRepository = factuurRepository;
    }

    public List<KlantOutputDto> getAllKlanten(){
        List<Klant> klantData = klantRepository.findAll();
        List<KlantOutputDto> klanten = new ArrayList<>();
        for(Klant klant : klantData){
            klanten.add(transferKlantToDto(klant));
        }
        return klanten;
    }

    public KlantOutputDto getKlant(Long id){
        Optional<Klant> optionalKlant = klantRepository.findById(id);
        if(optionalKlant.isPresent()){
            return transferKlantToDto(optionalKlant.get());
        }
        else {
            throw new NotFoundException("Deze klant bestaat niet");
        }
    }

    public Long createKlant(KlantInputDto klantInput){
        Klant klant = new Klant();
        return saveKlant(klantInput, klant).getKlantId();
    }

    public KlantOutputDto updateKlant(Long id, KlantInputDto klantInput){
        Optional<Klant> optionalKlant = klantRepository.findById(id);
        if(optionalKlant.isPresent()){
            Klant klant = optionalKlant.get();
            Klant geupdateKlant = saveKlant(klantInput, klant);
            return transferKlantToDto(geupdateKlant);
        }
        else {
            throw new NotFoundException("Deze klant bestaat niet");
        }
    }

    public void deleteKlant(Long id){
        Optional<Klant> optionalKlant = klantRepository.findById(id);
        if(optionalKlant.isPresent()){
            List<Factuur> bestaandeFacturen = factuurRepository.findAll();
            for(Factuur factuur : bestaandeFacturen){
                if(factuur.getFactuurKlant().getKlantId().equals(id)){
                    throw new InExistingFactuurException("Deze klant is in gebruik door een of meer facturen");
                }
            }
            klantRepository.deleteById(id);
        }
        else {
            throw new NotFoundException("Deze klant bestaat niet");
        }
    }

    public Klant saveKlant(KlantInputDto klantInput, Klant klant){
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
        return klant;
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
