package nl.novi.jnoldenfacturatie.services;
import nl.novi.jnoldenfacturatie.dtos.GebruikerInputDto;
import nl.novi.jnoldenfacturatie.dtos.GebruikerOutputDto;
import nl.novi.jnoldenfacturatie.exceptions.ExistingUsernameException;
import nl.novi.jnoldenfacturatie.exceptions.NotFoundException;
import nl.novi.jnoldenfacturatie.models.Gebruiker;
import nl.novi.jnoldenfacturatie.repositories.GebruikerRepository;
import nl.novi.jnoldenfacturatie.repositories.GebruikersRolRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class GebruikerService {
    private GebruikerRepository gebruikerRepository;
    private GebruikersRolRepository gebruikersRolRepository;
    private PasswordEncoder passwordEncoder;

    public GebruikerService(GebruikerRepository gebruikerRepository, GebruikersRolRepository gebruikersRolRepository, PasswordEncoder passwordEncoder){
        this.gebruikerRepository = gebruikerRepository;
        this.gebruikersRolRepository = gebruikersRolRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<GebruikerOutputDto> getGebruikers(){
        List<GebruikerOutputDto> gebruikers = new ArrayList<>();
        List<Gebruiker> gebruikerData = gebruikerRepository.findAll();
        for(Gebruiker gebruiker : gebruikerData){
            gebruikers.add(transferGebruikerToDto(gebruiker));
        }
        return gebruikers;
    }

    public GebruikerOutputDto getGebruiker(String gebruikersnaam){
        Optional<Gebruiker> optionalGebruiker = gebruikerRepository.findById(gebruikersnaam);
        if(optionalGebruiker.isPresent()){
            return transferGebruikerToDto(optionalGebruiker.get());
        }
        else{
            throw new NotFoundException("Deze gebruiker bestaat niet");
        }
    }

    public String createGebruiker(GebruikerInputDto gebruikerInput){
        Optional<Gebruiker> optionalGebruiker = gebruikerRepository.findById(gebruikerInput.getGebruikersnaam());
        if(optionalGebruiker.isPresent()){
            throw new ExistingUsernameException("Deze gebruikersnaam is al in gebruik");
        }
        else{
            Gebruiker gebruiker = new Gebruiker();
            gebruiker.setGebruikersnaam(gebruikerInput.getGebruikersnaam());
            gebruiker.setGebruikersRol(gebruikersRolRepository.findById("ROLE_gebruiker").get());
            gebruiker.setWachtwoord(passwordEncoder.encode(gebruikerInput.getWachtwoord()));
            gebruikerRepository.save(gebruiker);
            return gebruiker.getGebruikersnaam();
        }
    }

    public GebruikerOutputDto updateGebruikersRol(String gebruikersnaam, String gebruikersRol){
        Optional<Gebruiker> optionalGebruiker = gebruikerRepository.findById(gebruikersnaam);
        if(optionalGebruiker.isPresent()){
            Gebruiker gebruiker = optionalGebruiker.get();
            if(gebruikersRol.toLowerCase().equals("beheerder")){
                gebruiker.setGebruikersRol(gebruikersRolRepository.findById("ROLE_beheerder").get());
            }
            else if(gebruikersRol.toLowerCase().equals("gebruiker")){
                gebruiker.setGebruikersRol(gebruikersRolRepository.findById("ROLE_gebruiker").get());
            }
            else {
                throw new NotFoundException("Deze gebruikersrol bestaat niet");
            }
            gebruikerRepository.save(gebruiker);
            return transferGebruikerToDto(gebruiker);
        }
        else{
            throw new NotFoundException("Deze gebruiker bestaat niet");
        }
    }

    public void deleteGebruiker(String gebruikersnaam){
        Optional<Gebruiker> optionalGebruiker = gebruikerRepository.findById(gebruikersnaam);
        if(optionalGebruiker.isPresent()){
            gebruikerRepository.delete(optionalGebruiker.get());
        }
        else{
            throw new NotFoundException("Deze gebruiker bestaat niet");
        }
    }

    private GebruikerOutputDto transferGebruikerToDto(Gebruiker gebruiker){
        GebruikerOutputDto gebruikerOutput = new GebruikerOutputDto();
        gebruikerOutput.setGebruikersnaam(gebruiker.getGebruikersnaam());
        String gebruikersRol = gebruiker.getGebruikersRol().getGebruikersRol();
        if(gebruikersRol.equals("ROLE_beheerder")){
            gebruikerOutput.setGebruikersRol("Beheerder");
        }
        else if(gebruikersRol.equals("ROLE_gebruiker")){
            gebruikerOutput.setGebruikersRol("Gebruiker");
        }
        else {
            throw new NotFoundException("Deze gebruikersrol bestaat niet");
        }
        return gebruikerOutput;
    }
}
