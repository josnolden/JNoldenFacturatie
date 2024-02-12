package nl.novi.jnoldenfacturatie.controllers;

import nl.novi.jnoldenfacturatie.services.LogoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
public class LogoController {
    private final LogoService logoService;

    public LogoController(LogoService logoService){
        this.logoService = logoService;
    }

    @PostMapping("/logo")
    public ResponseEntity<String> uploadLogo(@RequestBody MultipartFile file) throws IOException {
        logoService.uploadLogo(file);
        return ResponseEntity.ok().body("Logo geüpload");
    }

    @DeleteMapping("/logo")
    public ResponseEntity<String> deleteLogo() {
        logoService.removeLogo();
        return ResponseEntity.ok().body("Logo verwijderd");
    }
}
