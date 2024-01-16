package nl.novi.jnoldenfacturatie.controllers;

import jakarta.validation.Valid;
import nl.novi.jnoldenfacturatie.dtos.ArtikelInputDto;
import nl.novi.jnoldenfacturatie.dtos.ArtikelOutputDto;
import nl.novi.jnoldenfacturatie.services.ArtikelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ArtikelController {
    private final ArtikelService artikelService;

    public ArtikelController(ArtikelService artikelService){
        this.artikelService = artikelService;
    }

    @GetMapping("/artikelen")
    public ResponseEntity<List<ArtikelOutputDto>> getAllArtikelen(){
        List<ArtikelOutputDto> artikelen = artikelService.getAllArtikelen();
        return ResponseEntity.ok().body(artikelen);
    }

    @GetMapping("/artikelen/{id}")
    public ResponseEntity<ArtikelOutputDto> getArtikel(@PathVariable("id")Long id){
        ArtikelOutputDto artikel = artikelService.getArtikel(id);
        return ResponseEntity.ok().body(artikel);
    }

    @PostMapping("/artikelen")
    public ResponseEntity<String> createArtikel(@Valid @RequestBody ArtikelInputDto artikelInput){
        Long id = artikelService.createArtikel(artikelInput);
        return ResponseEntity.created(null).body("Artikelnummer: "+id);
    }

    @PutMapping("/artikelen/{id}")
    public ResponseEntity<ArtikelOutputDto> updateArtikel(@PathVariable("id")Long id, @Valid @RequestBody ArtikelInputDto artikelInput){
        ArtikelOutputDto artikel = artikelService.updateArtikel(id, artikelInput);
        return ResponseEntity.ok().body(artikel);
    }

    @DeleteMapping("/artikelen/{id}")
    public ResponseEntity<String> deleteArtikel(@PathVariable("id")Long id){
        artikelService.deleteArtikel(id);
        return ResponseEntity.ok().body("Artikel verwijderd");
    }
}
