package nl.novi.jnoldenfacturatie.controllers;

import jakarta.validation.Valid;
import nl.novi.jnoldenfacturatie.dtos.FactuurInputDto;
import nl.novi.jnoldenfacturatie.dtos.FactuurOutputDto;
import nl.novi.jnoldenfacturatie.services.FactuurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController

public class FactuurController {
    private final FactuurService factuurService;

    public FactuurController(FactuurService factuurService){
        this.factuurService = factuurService;
    }

    @GetMapping("/facturen")
    public ResponseEntity<List<FactuurOutputDto>> getAllFacturen(){
        List<FactuurOutputDto> facturen = factuurService.getAllFacturen();
        return ResponseEntity.ok().body(facturen);
    }

    @GetMapping("/facturen/{id}")
    public ResponseEntity<FactuurOutputDto> getFactuur(@PathVariable("id")Long id){
        FactuurOutputDto factuur = factuurService.getFactuur(id);
        return ResponseEntity.ok().body(factuur);
    }

    @PostMapping("/facturen")
    public ResponseEntity<String> createFactuur(@Valid @RequestBody FactuurInputDto factuurInput){
        Long factuurNummer = factuurService.createFactuur(factuurInput);
        return ResponseEntity.created(null).body("Factuurnummer: "+factuurNummer);
    }
}
