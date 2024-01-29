package nl.novi.jnoldenfacturatie.controllers;

import nl.novi.jnoldenfacturatie.dtos.GebruikerInputDto;
import nl.novi.jnoldenfacturatie.services.GebruikerDataService;
import nl.novi.jnoldenfacturatie.utilities.JwtUtility;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@CrossOrigin
@RestController
public class LoginController {
    private final AuthenticationManager authenticationManager;
    private final GebruikerDataService gebruikerDataService;
    private final JwtUtility jwtUtility;

    public LoginController(AuthenticationManager authenticationManager, GebruikerDataService gebruikerDataService, JwtUtility jwtUtility){
        this.authenticationManager = authenticationManager;
        this.gebruikerDataService = gebruikerDataService;
        this.jwtUtility = jwtUtility;
    }

    @PostMapping(value = "/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody GebruikerInputDto gebruikerInput) throws Exception {

        String gebruikersnaam = gebruikerInput.getGebruikersnaam();
        String wachtwoord = gebruikerInput.getWachtwoord();

        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(gebruikersnaam, wachtwoord)
            );
        }
        catch (BadCredentialsException ex) {
            throw new Exception("Onjuiste gebruikersnaam of wachtwoord", ex);
        }

        final UserDetails userDetails = gebruikerDataService
                .loadUserByUsername(gebruikersnaam);

        final String jwt = jwtUtility.generateToken(userDetails);

        return ResponseEntity.ok(jwt);
    }

}
