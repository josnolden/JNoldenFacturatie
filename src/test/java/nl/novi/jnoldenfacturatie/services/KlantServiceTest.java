package nl.novi.jnoldenfacturatie.services;

import nl.novi.jnoldenfacturatie.dtos.AdresInputDto;
import nl.novi.jnoldenfacturatie.dtos.KlantInputDto;
import nl.novi.jnoldenfacturatie.dtos.KlantOutputDto;
import nl.novi.jnoldenfacturatie.exceptions.InExistingFactuurException;
import nl.novi.jnoldenfacturatie.exceptions.NotFoundException;
import nl.novi.jnoldenfacturatie.models.Adres;
import nl.novi.jnoldenfacturatie.models.Factuur;
import nl.novi.jnoldenfacturatie.models.Klant;
import nl.novi.jnoldenfacturatie.repositories.FactuurRepository;
import nl.novi.jnoldenfacturatie.repositories.KlantRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.util.Optional.empty;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class KlantServiceTest {
    Klant testKlant = new Klant();
    KlantInputDto testKlantInput = new KlantInputDto();
    Adres testAdres = new Adres();
    AdresInputDto testAdresInput = new AdresInputDto();

    @Mock
    KlantRepository klantRepositoryMock;
    @Mock
    FactuurRepository factuurRepositoryMock;

    @InjectMocks
    KlantService klantService;

    @BeforeEach
    void setUp() {
        testAdres.setAdresId(1L);
        testAdres.setStraat("Teststraat");
        testAdres.setHuisnummer(13);
        testAdres.setWoonplaats("Boek op kortedijk");
        testAdres.setLand("België");

        testKlant.setKlantId(1L);
        testKlant.setVoornaam("Jan");
        testKlant.setTussenvoegsel("van de");
        testKlant.setAchternaam("Lange");
        testKlant.setKlantAdres(testAdres);
        testKlant.setMailAdres("dinges@blabla.be");

        testAdresInput.setStraat("Testerslaan");
        testAdresInput.setHuisnummer(11);
        testAdresInput.setWoonplaats("Utrecht");
        testAdresInput.setLand("Nederland");

        testKlantInput.setVoornaam("Piet");
        testKlantInput.setAchternaam("Puk");
        testKlantInput.setMailadres("blabla@dinges.nl");
        testKlantInput.setAdres(testAdresInput);
    }

    @Test
    @DisplayName("Alle klanten worden gevonden")
    void getAllKlantenReturnsTestKlant() {
        // arrange
        List<Klant> klantList = new ArrayList<>();
        klantList.add(testKlant);
        when(klantRepositoryMock.findAll()).thenReturn(klantList);

        // act
        List<KlantOutputDto> resultList = klantService.getAllKlanten();
        KlantOutputDto klant1 = resultList.get(0);

        // assert
        assertEquals(1, resultList.size());
        assertEquals("Jan", klant1.getVoornaam());
        assertEquals("Lange", klant1.getAchternaam());
        assertEquals(13, klant1.getAdres().getHuisnummer());
    }

    @Test
    @DisplayName("Bestaande klant wordt gevonden")
    void getKlantReturnsTestKlant() {
        // arrange
        when(klantRepositoryMock.findById(1L)).thenReturn(Optional.of(testKlant));

        // act
        KlantOutputDto klant1 = klantService.getKlant(1L);

        // assert
        assertEquals("Jan", klant1.getVoornaam());
        assertEquals("Lange", klant1.getAchternaam());
        assertEquals(13, klant1.getAdres().getHuisnummer());
    }

    @Test
    @DisplayName("Niet bestaande klant geeft foutmelding")
    void getNonExistingKlantReturnsError() {
        // arrange
        when(klantRepositoryMock.findById(2L)).thenReturn(Optional.empty());

        // act
        var error =  assertThrows(NotFoundException.class, () -> klantService.getKlant(2L));

        // assert
        assertEquals("Deze klant bestaat niet", error.getMessage());
    }

    @Test
    @DisplayName("Klant aanmaken werkt")
    void createKlantDoesNotThrow() {
        // arrange
        when(klantRepositoryMock.save(Mockito.any(Klant.class))).thenReturn(null);

        // act
        //Long createdKlantId = klantService.createKlant(testKlantInput);

        // assert
        assertDoesNotThrow(() -> klantService.createKlant(testKlantInput));
    }

    @Test
    @DisplayName("Klant updaten werkt de eigenschappen bij")
    void updateKlantOverwritesTheProperties() {
        // arrange
        when(klantRepositoryMock.findById(anyLong())).thenReturn(Optional.of(testKlant));
        when(klantRepositoryMock.save(Mockito.any(Klant.class))).thenReturn(null);

        // act
        KlantOutputDto updatedKlant = klantService.updateKlant(testKlant.getKlantId(), testKlantInput);

        // assert
        assertEquals("Piet", updatedKlant.getVoornaam());
        assertEquals("Puk", updatedKlant.getAchternaam());
        assertEquals(11, updatedKlant.getAdres().getHuisnummer());
    }

    @Test
    @DisplayName("Niet bestaande klant updaten geeft foutmelding")
    void updateNonExistingKlantThrowsError(){
        // arrange
        when(klantRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // act
        var error = assertThrows(NotFoundException.class, () -> klantService.updateKlant(1L, testKlantInput));

        // assert
        assertEquals("Deze klant bestaat niet", error.getMessage());
    }

    @Test
    @DisplayName("Klant verwijderen werkt")
    void deleteExistingKlantDoesNotThrowError() {
        // arrange
        when(klantRepositoryMock.findById(1L)).thenReturn(Optional.of(testKlant));
        Factuur testFactuur = new Factuur();
        Klant andereTestKlant = new Klant();
        andereTestKlant.setKlantId(2L);
        testFactuur.setFactuurKlant(andereTestKlant);
        List<Factuur> testFactuurLijst = new ArrayList<>();
        testFactuurLijst.add(testFactuur);
        when(factuurRepositoryMock.findAll()).thenReturn(testFactuurLijst);

        // act
        //klantService.deleteKlant(1L);

        // assert
        assertDoesNotThrow(() -> klantService.deleteKlant(1L));
    }

    @Test
    @DisplayName("Klant met bestaande factuur verwijderen geeft foutmelding")
    void deleteExistingKlantWithFactuurThrowsError(){
        // arrange
        when(klantRepositoryMock.findById(1L)).thenReturn(Optional.of(testKlant));
        Factuur testFactuur = new Factuur();
        testFactuur.setFactuurKlant(testKlant);
        List<Factuur> testFactuurLijst = new ArrayList<>();
        testFactuurLijst.add(testFactuur);
        when(factuurRepositoryMock.findAll()).thenReturn(testFactuurLijst);

        // act
        var error = assertThrows(InExistingFactuurException.class, () -> klantService.deleteKlant(1L));

        // assert
        assertEquals("Deze klant is in gebruik door een of meer facturen", error.getMessage());
    }

    @Test
    @DisplayName("Niet bestaande klant verwijderen geeft foutmelding")
    void deleteNonExistingKlantThrowsError(){
        // arrange
        when(klantRepositoryMock.findById(1L)).thenReturn(Optional.empty());

        // act
        var error = assertThrows(NotFoundException.class, () -> klantService.deleteKlant(1L));

        // assert
        assertEquals("Deze klant bestaat niet", error.getMessage());
    }

    @Test
    @DisplayName("Klant opslaan geeft ingevulde klant terug")
    void saveKlantReturnsFilledInKlant() {
        // arrange
        when(klantRepositoryMock.save(Mockito.any(Klant.class))).thenReturn(null);

        // act
        Klant createdKlant = klantService.saveKlant(testKlantInput, new Klant());

        // assert
        assertEquals("Piet", createdKlant.getVoornaam());
        assertEquals("Testerslaan", createdKlant.getKlantAdres().getStraat());
    }

    @Test
    @DisplayName("Klant overzetten geeft juiste data terug")
    void transferKlantToDtoReturnsValidData() {
        // arrange

        // act
        KlantOutputDto klantOutput = klantService.transferKlantToDto(testKlant);

        // assert
        assertEquals("Jan", klantOutput.getVoornaam());
        assertEquals("Teststraat", klantOutput.getAdres().getStraat());
    }

    @Test
    @DisplayName("Adres overzetten geeft juiste data terug")
    void transferAdresToDto() {
        // arrange

        // act
        AdresInputDto adresOutput = klantService.transferAdresToDto(testAdres);

        // assert
        assertEquals("Teststraat", adresOutput.getStraat());
        assertEquals("België", adresOutput.getLand());
    }
}