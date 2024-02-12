package nl.novi.jnoldenfacturatie.services;
import nl.novi.jnoldenfacturatie.dtos.FactuurOutputDto;
import nl.novi.jnoldenfacturatie.dtos.OrderRegelSummaryDto;
import nl.novi.jnoldenfacturatie.exceptions.NotFoundException;
import nl.novi.jnoldenfacturatie.models.Factuur;
import nl.novi.jnoldenfacturatie.models.Logo;
import nl.novi.jnoldenfacturatie.repositories.FactuurRepository;
import nl.novi.jnoldenfacturatie.repositories.LogoRepository;
import nl.novi.jnoldenfacturatie.utilities.CompressionUtility;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Optional;

@Service
public class PdfService {
    private FactuurRepository factuurRepository;
    private LogoRepository logoRepository;
    private FactuurService factuurService;
    private CompressionUtility compressionUtility;

    public PdfService(FactuurRepository factuurRepository, LogoRepository logoRepository, FactuurService factuurService, CompressionUtility compressionUtility){
        this.factuurRepository = factuurRepository;
        this.logoRepository = logoRepository;
        this.factuurService = factuurService;
        this.compressionUtility = compressionUtility;
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

        PDPageContentStream contentStream = new PDPageContentStream(factuurPdf, factuurPagina);
        contentStream.beginText();
        contentStream.setFont(font, 24);
        contentStream.newLineAtOffset(250, 770);
        contentStream.setLeading(18f);
        contentStream.showText("Factuur");
        contentStream.endText();

        contentStream.beginText();
        contentStream.setFont(font, 12);
        contentStream.newLineAtOffset(10, 750);
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

        contentStream.beginText();
        contentStream.newLineAtOffset(10, 650);
        contentStream.showText("Factuurnummer: " + factuur.getFactuurNummer().toString());
        contentStream.newLine();
        contentStream.showText("Factuurdatum: " + factuur.getFactuurDatum().toString());
        contentStream.newLine();
        contentStream.showText("Betaaldatum: " + factuur.getBetaalDatum().toString());
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(60, 580);
        contentStream.showText("Artikelnaam");
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(400, 580);
        contentStream.showText("Aantal");
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(450, 580);
        contentStream.showText("Btw");
        contentStream.endText();

        contentStream.beginText();
        contentStream.newLineAtOffset(520, 580);
        contentStream.showText("Prijs incl. btw");
        contentStream.endText();

        contentStream.moveTo(10, 570);
        contentStream.lineTo(600, 570);
        contentStream.stroke();

        Integer yPosition = 570;
        for(OrderRegelSummaryDto orderRegel : factuur.getOrderRegels()){
            yPosition -= 12;
            contentStream.beginText();
            contentStream.newLineAtOffset(10, yPosition);
            contentStream.showText(orderRegel.getRegelNummer().toString());
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(60, yPosition);
            contentStream.showText(orderRegel.getArtikelNaam());
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(400, yPosition);
            contentStream.showText(orderRegel.getAantal().toString());
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(450, yPosition);
            contentStream.showText("€ " + orderRegel.getBtw().toString());
            contentStream.endText();

            contentStream.beginText();
            contentStream.newLineAtOffset(520, yPosition);
            contentStream.showText("€ " + orderRegel.getRegelPrijs().toString());
            contentStream.endText();
        }

        yPosition -= 10;
        contentStream.moveTo(10, yPosition);
        contentStream.lineTo(600, yPosition);
        contentStream.stroke();

        yPosition -= 12;
        contentStream.beginText();
        contentStream.newLineAtOffset(450, yPosition);
        contentStream.showText("Subtotaal: € " + factuur.getSubTotaal().toString());
        contentStream.newLine();
        contentStream.showText("Btw totaal: € " + factuur.getBtwTotaal().toString());
        contentStream.newLine();
        contentStream.showText("Korting: € " + factuur.getKorting().toString());
        contentStream.newLine();
        contentStream.showText("Totaalprijs: € " + factuur.getTotaalPrijs().toString());
        contentStream.endText();

        contentStream.close();

        Optional<Logo> logoOptional = logoRepository.findById(Long.valueOf(1));
        if(logoOptional.isPresent()){
            Logo logo = logoOptional.get();
            PDImageXObject pdImage = PDImageXObject.createFromByteArray(factuurPdf, compressionUtility.decompress(logo.getAfbeeldingData()), "Logo");
            PDPageContentStream logoStream = new PDPageContentStream(factuurPdf, factuurPagina, PDPageContentStream.AppendMode.APPEND, true, true);
            logoStream.drawImage(pdImage, 450, 660, 100, 100);
            logoStream.close();
        }

        factuurPdf.save(output);
        factuurPdf.close();
        return output;
    }
}
