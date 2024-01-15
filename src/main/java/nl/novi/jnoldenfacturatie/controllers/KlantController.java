package nl.novi.jnoldenfacturatie.controllers;

import jakarta.validation.Valid;
import nl.novi.jnoldenfacturatie.dtos.KlantInputDto;
import nl.novi.jnoldenfacturatie.dtos.KlantOutputDto;
import nl.novi.jnoldenfacturatie.models.Klant;
import nl.novi.jnoldenfacturatie.services.KlantService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
public class KlantController {
    private final KlantService klantService;

    public KlantController(KlantService klantService){
        this.klantService = klantService;
    }

    @GetMapping("/klanten")
    public ResponseEntity<List<KlantOutputDto>> getAllKlanten(){
        List<KlantOutputDto> klanten = klantService.getAllKlanten();
        return ResponseEntity.ok().body(klanten);
    }

    @GetMapping("/klanten/{id}")
    public ResponseEntity<KlantOutputDto> getKlant(@PathVariable("id")Long id){
        KlantOutputDto klant = klantService.getKlant(id);
        return ResponseEntity.ok().body(klant);
    }

    @PostMapping("/klanten")
    public ResponseEntity<String> createKlant(@Valid @RequestBody KlantInputDto klantInput){
        Long id = klantService.createKlant(klantInput);
        return ResponseEntity.created(null).body("Klantnummer: "+id);
    }

    @PutMapping("/klanten/{id}")
    public ResponseEntity<KlantOutputDto> updateKlant(@PathVariable("id")Long id, @Valid @RequestBody KlantInputDto klantInput){
        KlantOutputDto klant = klantService.updateKlant(id, klantInput);
        return ResponseEntity.ok().body(klant);
    }

    @DeleteMapping("/klanten/{id}")
    public ResponseEntity<String> deleteKlant(@PathVariable("id")Long id){
        klantService.deleteKlant(id);
        return ResponseEntity.ok().body("Klant verwijderd");
    }
}
