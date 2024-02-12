package nl.novi.jnoldenfacturatie.services;

import nl.novi.jnoldenfacturatie.exceptions.NotFoundException;
import nl.novi.jnoldenfacturatie.models.Logo;
import nl.novi.jnoldenfacturatie.repositories.LogoRepository;
import nl.novi.jnoldenfacturatie.utilities.CompressionUtility;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Optional;

@Service
public class LogoService {
    private final LogoRepository logoRepository;
    private final CompressionUtility compressionUtility;

    public LogoService(LogoRepository logoRepository, CompressionUtility compressionUtility){
        this.logoRepository = logoRepository;
        this.compressionUtility = compressionUtility;
    }

    public void uploadLogo(MultipartFile file) throws IOException {
        Optional<Logo> logoOptional = logoRepository.findById(Long.valueOf(1));
        if(logoOptional.isPresent()){
            Logo logo = logoOptional.get();
            logo.setAfbeeldingData(compressionUtility.compress(file.getBytes()));
            logoRepository.save(logo);
        }
        else{
            Logo logo = new Logo();
            logo.setLogoId(Long.valueOf(1));
            logo.setAfbeeldingData(compressionUtility.compress(file.getBytes()));
            logoRepository.save(logo);
        }
    }

    public void removeLogo() {
        Optional<Logo> logoOptional = logoRepository.findById(Long.valueOf(1));
        if(logoOptional.isPresent()){
            Logo logo = logoOptional.get();
            logoRepository.delete(logo);
        }
        else {
            throw new NotFoundException("Er is geen logo opgeslagen");
        }
    }
}
