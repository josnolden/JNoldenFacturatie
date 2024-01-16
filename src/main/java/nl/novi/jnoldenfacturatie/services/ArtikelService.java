package nl.novi.jnoldenfacturatie.services;
import nl.novi.jnoldenfacturatie.dtos.ArtikelInputDto;
import nl.novi.jnoldenfacturatie.dtos.ArtikelOutputDto;
import nl.novi.jnoldenfacturatie.exceptions.NotFoundException;
import nl.novi.jnoldenfacturatie.models.Artikel;
import nl.novi.jnoldenfacturatie.repositories.ArtikelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ArtikelService {
    private ArtikelRepository artikelRepository;

    public ArtikelService(ArtikelRepository repository){
        this.artikelRepository = repository;
    }

    public List<ArtikelOutputDto> getAllArtikelen(){
        List<Artikel> artikelData = artikelRepository.findAll();
        List<ArtikelOutputDto> artikelen = new ArrayList<>();
        for(Artikel artikel : artikelData){
            artikelen.add(transferArtikelToDto(artikel));
        }
        return artikelen;
    }

    public ArtikelOutputDto getArtikel(Long id){
        Optional<Artikel> optionalArtikel = artikelRepository.findById(id);
        if(optionalArtikel.isPresent()){
            return transferArtikelToDto(optionalArtikel.get());
        }
        else {
            throw new NotFoundException("Dit artikel bestaat niet");
        }
    }

    public Long createArtikel(ArtikelInputDto artikelInput){
        var artikel = new Artikel();
        return saveArtikel(artikelInput, artikel).getArtikelId();
    }

    public ArtikelOutputDto updateArtikel(Long id, ArtikelInputDto artikelInput){
        Optional<Artikel> optionalArtikel = artikelRepository.findById(id);
        if(optionalArtikel.isPresent()){
            Artikel artikel = optionalArtikel.get();
            Artikel geupdateArtikel = saveArtikel(artikelInput, artikel);
            return transferArtikelToDto(geupdateArtikel);
        }
        else {
            throw new NotFoundException("Dit artikel bestaat niet");
        }
    }

    public void deleteArtikel(Long id){
        Optional<Artikel> optionalArtikel = artikelRepository.findById(id);
        if(optionalArtikel.isPresent()){
            artikelRepository.deleteById(id);
        }
        else {
            throw new NotFoundException("Dit artikel bestaat niet");
        }
    }

    public Artikel saveArtikel(ArtikelInputDto artikelInput, Artikel artikel){
        artikel.setNaam(artikelInput.getNaam());
        artikel.setCategorie(artikelInput.getCategorie());
        artikel.setPrijs(artikelInput.getPrijs());
        artikel.setBtwPercentage(artikelInput.getBtwPercentage());
        this.artikelRepository.save(artikel);
        return artikel;
    }

    public ArtikelOutputDto transferArtikelToDto(Artikel artikelData){
        ArtikelOutputDto artikelDto = new ArtikelOutputDto();
        artikelDto.setId(artikelData.getArtikelId());
        artikelDto.setNaam(artikelData.getNaam());
        artikelDto.setCategorie(artikelData.getCategorie());
        artikelDto.setPrijs(artikelData.getPrijs());
        artikelDto.setBtwPercentage(artikelData.getBtwPercentage());
        return artikelDto;
    }
}
