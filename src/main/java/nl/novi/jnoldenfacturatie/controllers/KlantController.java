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

    @PostMapping("/klanten")
    public ResponseEntity<Long> createKlant(@Valid @RequestBody KlantInputDto klantInput){
        Long id = klantService.createKlant(klantInput);
        return ResponseEntity.created(null).body(id);
    }
}
