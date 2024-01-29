package nl.novi.jnoldenfacturatie.services;
import nl.novi.jnoldenfacturatie.exceptions.NotFoundException;
import nl.novi.jnoldenfacturatie.models.Gebruiker;
import nl.novi.jnoldenfacturatie.repositories.GebruikerRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GebruikerDataService implements UserDetailsService {
    private final GebruikerRepository gebruikerRepository;

    public GebruikerDataService(GebruikerRepository gebruikerRepository){
        this.gebruikerRepository = gebruikerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) {
        Optional<Gebruiker> optionalGebruiker = gebruikerRepository.findById(username);
        if (optionalGebruiker.isPresent()) {
            Gebruiker gebruiker = optionalGebruiker.get();
            GrantedAuthority gebruikersRol = new SimpleGrantedAuthority(gebruiker.getGebruikersRol().getGebruikersRol());
            List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
            grantedAuthorities.add(gebruikersRol);
            return new User(gebruiker.getGebruikersnaam(), gebruiker.getWachtwoord(), grantedAuthorities);
        } else {
            throw new NotFoundException("Deze gebruiker bestaat niet");
        }
    }
}
