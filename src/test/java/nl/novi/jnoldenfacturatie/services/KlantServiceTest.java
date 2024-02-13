package nl.novi.jnoldenfacturatie.services;

import nl.novi.jnoldenfacturatie.dtos.AdresInputDto;
import nl.novi.jnoldenfacturatie.dtos.KlantInputDto;
import nl.novi.jnoldenfacturatie.dtos.KlantOutputDto;
import nl.novi.jnoldenfacturatie.exceptions.NotFoundException;
import nl.novi.jnoldenfacturatie.models.Adres;
import nl.novi.jnoldenfacturatie.models.Klant;
import nl.novi.jnoldenfacturatie.repositories.FactuurRepository;
import nl.novi.jnoldenfacturatie.repositories.KlantRepository;
import org.junit.jupiter.api.BeforeEach;
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
    List<Klant> klantList = new ArrayList<>();

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

        klantList.add(testKlant);
    }

    @Test
    void getAllKlantenReturnsTestKlant() {
        // arrange
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
    void getNonExistingKlantReturnsError() {
        // arrange
        when(klantRepositoryMock.findById(2L)).thenReturn(Optional.empty());

        // act
        var error =  assertThrows(NotFoundException.class, () -> klantService.getKlant(2L));

        // assert
        assertEquals("Deze klant bestaat niet", error.getMessage());
    }

    @Test
    void createKlantDoesSomething() {
        // arrange
        when(klantRepositoryMock.save(Mockito.any(Klant.class))).thenReturn(null);

        // act
        Long createdKlantId = klantService.createKlant(testKlantInput);

        // assert
        assertNull(createdKlantId);
    }

    @Test
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
    void deleteKlant() {
    }

    @Test
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
    void transferKlantToDto() {
    }

    @Test
    void transferAdresToDto() {
    }
}