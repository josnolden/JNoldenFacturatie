package nl.novi.jnoldenfacturatie.services;
import nl.novi.jnoldenfacturatie.dtos.FactuurOutputDto;
import nl.novi.jnoldenfacturatie.exceptions.NotFoundException;
import nl.novi.jnoldenfacturatie.models.Factuur;
import nl.novi.jnoldenfacturatie.repositories.FactuurRepository;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class PdfService {
    private FactuurRepository factuurRepository;
    private FactuurService factuurService;

    public PdfService(FactuurRepository factuurRepository, FactuurService factuurService){
        this.factuurRepository = factuurRepository;
        this.factuurService = factuurService;
    }

    public ByteArrayOutputStream getFactuurPdf(Long id){
        Optional<Factuur> optionalFactuur = factuurRepository.findById(id);
        if(optionalFactuur.isPresent()){
            FactuurOutputDto factuur = factuurService.transferFactuurToDto(optionalFactuur.get());
            try {
                return createPdf(factuur);
            } catch (IOException exception) {
                throw new RuntimeException(exception);
            }
        }
        else {
            throw new NotFoundException("Deze factuur bestaat niet");
        }
    }

    public ByteArrayOutputStream createPdf(FactuurOutputDto factuur) throws IOException {
        ByteArrayOutputStream output =new ByteArrayOutputStream();
        PDFont font = PDType1Font.HELVETICA;
        PDDocument factuurPdf = new PDDocument();
        PDPage factuurPagina = new PDPage();
        factuurPdf.addPage(factuurPagina);
        PDPageContentStream contentStream;

        contentStream = new PDPageContentStream(factuurPdf, factuurPagina);
        contentStream.beginText();
        contentStream.setFont(font, 18);
        contentStream.newLineAtOffset(10, 770);
        contentStream.setLeading(18f);
        String naam = factuur.getFactuurKlant().getVoornaam();
        String tussenVoegsel = factuur.getFactuurKlant().getTussenvoegsel();
        if(tussenVoegsel != null){
            naam += " " + tussenVoegsel;
        }
        naam += " " + factuur.getFactuurKlant().getAchternaam();
        contentStream.showText(naam);
        contentStream.newLine();
        String adres = factuur.getFactuurKlant().getAdres().getStraat() + " " + factuur.getFactuurKlant().getAdres().getHuisnummer().toString();
        String toevoeging = factuur.getFactuurKlant().getAdres().getHuisnummerToevoeging();
        if(toevoeging != null){
            adres += " " + toevoeging;
        }
        contentStream.showText(adres);
        contentStream.newLine();
        contentStream.showText(factuur.getFactuurKlant().getAdres().getWoonplaats());
        contentStream.newLine();
        contentStream.showText(factuur.getFactuurKlant().getAdres().getLand());
        contentStream.endText();

        contentStream.close();

        factuurPdf.save(output);
        factuurPdf.close();
        return output;
    }
}
