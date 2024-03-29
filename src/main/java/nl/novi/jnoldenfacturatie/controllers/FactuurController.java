package nl.novi.jnoldenfacturatie.controllers;

import jakarta.validation.Valid;
import nl.novi.jnoldenfacturatie.dtos.FactuurInputDto;
import nl.novi.jnoldenfacturatie.dtos.FactuurOutputDto;
import nl.novi.jnoldenfacturatie.dtos.OrderRegelInputDto;
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

    @DeleteMapping("/facturen/{id}")
    public ResponseEntity<String> deleteFactuur(@PathVariable("id")Long id){
        factuurService.deleteFactuur(id);
        return ResponseEntity.ok().body("Factuur en orderregels verwijderd");
    }

    @PutMapping("/facturen/{id}/addOrderRegel")
    public ResponseEntity<FactuurOutputDto> addOrderRegelToFactuur(@PathVariable("id")Long id, @Valid @RequestBody OrderRegelInputDto orderRegelInput){
        FactuurOutputDto factuur = factuurService.addOrderRegelToFactuur(id, orderRegelInput);
        return ResponseEntity.ok().body(factuur);
    }

    @DeleteMapping("/facturen/{id}/removeOrderRegel/{regelNummer}")
    public ResponseEntity<FactuurOutputDto> removeOrderRegelFromFactuur(@PathVariable("id")Long id, @PathVariable("regelNummer")Integer regelNummer){
        FactuurOutputDto factuur = factuurService.removeOrderRegelFromFactuur(id, regelNummer);
        return ResponseEntity.ok().body(factuur);
    }
}
