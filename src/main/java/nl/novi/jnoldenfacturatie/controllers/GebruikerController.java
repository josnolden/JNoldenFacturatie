package nl.novi.jnoldenfacturatie.controllers;

import jakarta.validation.Valid;
import nl.novi.jnoldenfacturatie.dtos.GebruikerInputDto;
import nl.novi.jnoldenfacturatie.dtos.GebruikerOutputDto;
import nl.novi.jnoldenfacturatie.services.GebruikerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class GebruikerController {
    private final GebruikerService gebruikerService;

    public GebruikerController(GebruikerService gebruikerService){
        this.gebruikerService = gebruikerService;
    }

    @GetMapping("/gebruikers")
    public ResponseEntity<List<GebruikerOutputDto>> getAllKlanten(){
        List<GebruikerOutputDto> gebruikers = gebruikerService.getGebruikers();
        return ResponseEntity.ok().body(gebruikers);
    }

    @GetMapping("/gebruikers/{gebruikersnaam}")
    public ResponseEntity<GebruikerOutputDto> getKlant(@PathVariable("gebruikersnaam")String gebruikersnaam){
        GebruikerOutputDto gebruiker = gebruikerService.getGebruiker(gebruikersnaam);
        return ResponseEntity.ok().body(gebruiker);
    }

    @PostMapping("/gebruikers")
    public ResponseEntity<String> createKlant(@Valid @RequestBody GebruikerInputDto gebruikerAanmaken){
        String gebruikersNaam = gebruikerService.createGebruiker(gebruikerAanmaken);
        return ResponseEntity.created(null).body("Gebruiker aangemaakt met gebruikersnaam: "+gebruikersNaam);
    }

    @PutMapping("/gebruikers/{gebruikersnaam}")
    public ResponseEntity<GebruikerOutputDto> updateKlant(@PathVariable("gebruikersnaam")String gebruikersnaam, @RequestBody String gebruikersRol){
        GebruikerOutputDto gebruiker = gebruikerService.updateGebruikersRol(gebruikersnaam, gebruikersRol);
        return ResponseEntity.ok().body(gebruiker);
    }

    @DeleteMapping("/gebruikers/{gebruikersnaam}")
    public ResponseEntity<String> deleteKlant(@PathVariable("gebruikersnaam")String gebruikersnaam){
        gebruikerService.deleteGebruiker(gebruikersnaam);
        return ResponseEntity.ok().body("Gebruiker verwijderd");
    }
}
