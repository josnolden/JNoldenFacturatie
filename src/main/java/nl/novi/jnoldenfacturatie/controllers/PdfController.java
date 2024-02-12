package nl.novi.jnoldenfacturatie.controllers;

import nl.novi.jnoldenfacturatie.dtos.FactuurOutputDto;
import nl.novi.jnoldenfacturatie.services.PdfService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;

@RestController
public class PdfController {
    private final PdfService pdfService;

    public PdfController(PdfService pdfService){
        this.pdfService = pdfService;
    }
    @GetMapping("/facturen/{id}/pdf")
    public ResponseEntity<byte[]> getFactuurPdf(@PathVariable("id")Long id){
        byte[] output = pdfService.getFactuurPdf(id).toByteArray();
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        String filename = "factuur.pdf";
        headers.setContentDispositionFormData(filename, filename);
        headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
        ResponseEntity<byte[]> response = new ResponseEntity<>(output, headers, HttpStatus.OK);
        return response;
    }
}
