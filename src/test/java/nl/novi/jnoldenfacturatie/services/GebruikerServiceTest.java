package nl.novi.jnoldenfacturatie.services;

import nl.novi.jnoldenfacturatie.dtos.GebruikerInputDto;
import nl.novi.jnoldenfacturatie.dtos.GebruikerOutputDto;
import nl.novi.jnoldenfacturatie.exceptions.ExistingUsernameException;
import nl.novi.jnoldenfacturatie.exceptions.NotFoundException;
import nl.novi.jnoldenfacturatie.models.Gebruiker;
import nl.novi.jnoldenfacturatie.models.GebruikersRol;
import nl.novi.jnoldenfacturatie.models.Klant;
import nl.novi.jnoldenfacturatie.repositories.GebruikerRepository;
import nl.novi.jnoldenfacturatie.repositories.GebruikersRolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GebruikerServiceTest {
    Gebruiker testGebruiker = new Gebruiker();
    GebruikersRol testGebruikersRol = new GebruikersRol();
    GebruikersRol testBeheerdersRol = new GebruikersRol();

    @Mock
    GebruikerRepository gebruikerRepositoryMock;

    @Mock
    GebruikersRolRepository gebruikersRolRepositoryMock;

    @Mock
    PasswordEncoder passwordEncoderMock;

    @InjectMocks
    GebruikerService gebruikerService;

    @BeforeEach
    void setUp() {
        testGebruikersRol.setGebruikersRol("ROLE_gebruiker");
        testBeheerdersRol.setGebruikersRol("ROLE_beheerder");

        testGebruiker.setGebruikersnaam("TestGebruiker");
        testGebruiker.setWachtwoord("Geheim");
        testGebruiker.setGebruikersRol(testGebruikersRol);
    }

    @Test
    @DisplayName("Alle gebruikers worden gevonden")
    void getGebruikersReturnsTestGebruiker() {
        // arrange
        List<Gebruiker> testGebruikersList = new ArrayList<>();
        testGebruikersList.add(testGebruiker);
        when(gebruikerRepositoryMock.findAll()).thenReturn(testGebruikersList);

        // act
        List<GebruikerOutputDto> gebruikers = gebruikerService.getGebruikers();

        // assert
        assertEquals(1, gebruikers.size());
        assertEquals("TestGebruiker", gebruikers.get(0).getGebruikersnaam());
    }

    @Test
    @DisplayName("Bestaande gebruiker wordt gevonden")
    void getGebruikerReturnsTestGebruiker() {
        // arrange
        when(gebruikerRepositoryMock.findById(testGebruiker.getGebruikersnaam())).thenReturn(Optional.of(testGebruiker));

        // act
        GebruikerOutputDto gebruiker = gebruikerService.getGebruiker(testGebruiker.getGebruikersnaam());

        // assert
        assertEquals("TestGebruiker", gebruiker.getGebruikersnaam());
    }

    @Test
    @DisplayName("Niet bestaande gebruiker geeft foutmelding")
    void getNonExistingKlantReturnsError() {
        // arrange
        when(gebruikerRepositoryMock.findById("NietBestaandeGebruiker")).thenReturn(Optional.empty());

        // act
        var error =  assertThrows(NotFoundException.class, () -> gebruikerService.getGebruiker("NietBestaandeGebruiker"));

        // assert
        assertEquals("Deze gebruiker bestaat niet", error.getMessage());
    }

    @Test
    @DisplayName("Gebruiker aanmaken werkt")
    void createGebruiker() {
        // arrange
        GebruikerInputDto testGebruikerInput = new GebruikerInputDto();
        testGebruikerInput.setGebruikersnaam("TestGebruikerer");
        testGebruikerInput.setWachtwoord("Geheimer");
        when(gebruikerRepositoryMock.findById("TestGebruikerer")).thenReturn(Optional.empty());
        when(gebruikersRolRepositoryMock.findById("ROLE_gebruiker")).thenReturn(Optional.of(testGebruikersRol));
        when(gebruikerRepositoryMock.save(Mockito.any(Gebruiker.class))).thenReturn(null);
        when(passwordEncoderMock.encode("Geheimer")).thenReturn("Geheimerer");

        // act
        String testGebruikersnaam = gebruikerService.createGebruiker(testGebruikerInput);

        // assert
        assertEquals("TestGebruikerer", testGebruikersnaam);
    }

    @Test
    @DisplayName("Gebruiker aanmaken met bestaande gebruikersnaam geeft foutmelding")
    void createGebruikerWithExistingUsernameThrowsError() {
        // arrange
        GebruikerInputDto testGebruikerInput = new GebruikerInputDto();
        testGebruikerInput.setGebruikersnaam("TestGebruiker");
        testGebruikerInput.setWachtwoord("Geheimerer");
        when(gebruikerRepositoryMock.findById(testGebruiker.getGebruikersnaam())).thenReturn(Optional.of(testGebruiker));

        // act
        var error = assertThrows(ExistingUsernameException.class, () -> gebruikerService.createGebruiker(testGebruikerInput));

        // assert
        assertEquals("Deze gebruikersnaam is al in gebruik", error.getMessage());
    }

    @Test
    @DisplayName("Gebruikersrol updaten werkt de rol bij naar beheerder")
    void updateGebruikersRolAppliesTheBeheerderRole() {
        // arrange
        when(gebruikerRepositoryMock.findById(testGebruiker.getGebruikersnaam())).thenReturn(Optional.of(testGebruiker));
        when(gebruikersRolRepositoryMock.findById("ROLE_beheerder")).thenReturn(Optional.of(testBeheerdersRol));
        when(gebruikerRepositoryMock.save(Mockito.any(Gebruiker.class))).thenReturn(null);

        // act
        GebruikerOutputDto gebruikerOutput = gebruikerService.updateGebruikersRol(testGebruiker.getGebruikersnaam(), "Beheerder");

        // assert
        assertEquals("Beheerder", gebruikerOutput.getGebruikersRol());
    }

    @Test
    @DisplayName("Gebruikersrol updaten werkt de rol bij naar gebruiker")
    void updateGebruikersRolAppliesTheGebruikerRole() {
        // arrange
        when(gebruikerRepositoryMock.findById(testGebruiker.getGebruikersnaam())).thenReturn(Optional.of(testGebruiker));
        when(gebruikersRolRepositoryMock.findById("ROLE_gebruiker")).thenReturn(Optional.of(testGebruikersRol));
        when(gebruikerRepositoryMock.save(Mockito.any(Gebruiker.class))).thenReturn(null);

        // act
        GebruikerOutputDto gebruikerOutput = gebruikerService.updateGebruikersRol(testGebruiker.getGebruikersnaam(), "Gebruiker");

        // assert
        assertEquals("Gebruiker", gebruikerOutput.getGebruikersRol());
    }

    @Test
    @DisplayName("Gebruikersrol updaten geeft foutmelding bij niet bestaande rol")
    void updateGebruikersRolThrowsErrorWhenRoleDoesNotExist() {
        // arrange
        when(gebruikerRepositoryMock.findById(testGebruiker.getGebruikersnaam())).thenReturn(Optional.of(testGebruiker));

        // act
        var error = assertThrows(NotFoundException.class, () -> gebruikerService.updateGebruikersRol(testGebruiker.getGebruikersnaam(), "NietBestaandeRol"));

        // assert
        assertEquals("Deze gebruikersrol bestaat niet", error.getMessage());
    }

    @Test
    @DisplayName("Gebruikersrol updaten geeft foutmelding bij niet bestaande gebruiker")
    void updateGebruikersRolThrowsErrorWhenGebruikerDoesNotExist() {
        // arrange
        when(gebruikerRepositoryMock.findById("NietBestaandeGebruiker")).thenReturn(Optional.empty());

        // act
        var error = assertThrows(NotFoundException.class, () -> gebruikerService.updateGebruikersRol("NietBestaandeGebruiker", "Gebruiker"));

        // assert
        assertEquals("Deze gebruiker bestaat niet", error.getMessage());
    }

    @Test
    @DisplayName("Gebruiker verwijderen werkt")
    void deleteExistingGebruikerDoesNotThrowError() {
        // arrange
        when(gebruikerRepositoryMock.findById(testGebruiker.getGebruikersnaam())).thenReturn(Optional.of(testGebruiker));

        // act
        // gebruikerService.deleteGebruiker(testGebruiker.getGebruikersnaam());

        // assert
        assertDoesNotThrow(() -> gebruikerService.deleteGebruiker(testGebruiker.getGebruikersnaam()));
    }

    @Test
    @DisplayName("Niet bestaande gebruiker verwijderen geeft foutmelding")
    void deleteNonExistingKlantThrowsError(){
        // arrange
        when(gebruikerRepositoryMock.findById("NietBestaandeGebruiker")).thenReturn(Optional.empty());

        // act
        var error = assertThrows(NotFoundException.class, () -> gebruikerService.deleteGebruiker("NietBestaandeGebruiker"));

        // assert
        assertEquals("Deze gebruiker bestaat niet", error.getMessage());
    }

    @Test
    @DisplayName("Gebruiker met niet bestaande rol overzetten geeft foutmelding")
    void transferGebruikerToDtoWithNonExistingRoleThrowsError(){
        // arrange
        GebruikersRol nietBestaandeRol = new GebruikersRol();
        nietBestaandeRol.setGebruikersRol("NietBestaandeRol");
        Gebruiker fouteGebruiker = new Gebruiker();
        fouteGebruiker.setGebruikersRol(nietBestaandeRol);

        // act
        var error = assertThrows(NotFoundException.class, () -> gebruikerService.transferGebruikerToDto(fouteGebruiker));

        // assert
        assertEquals("Deze gebruikersrol bestaat niet", error.getMessage());
    }
}