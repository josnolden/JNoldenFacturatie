package nl.novi.jnoldenfacturatie.services;
import nl.novi.jnoldenfacturatie.dtos.*;
import nl.novi.jnoldenfacturatie.exceptions.NotFoundException;
import nl.novi.jnoldenfacturatie.models.Artikel;
import nl.novi.jnoldenfacturatie.models.Factuur;
import nl.novi.jnoldenfacturatie.models.Klant;
import nl.novi.jnoldenfacturatie.models.OrderRegel;
import nl.novi.jnoldenfacturatie.repositories.ArtikelRepository;
import nl.novi.jnoldenfacturatie.repositories.FactuurRepository;
import nl.novi.jnoldenfacturatie.repositories.KlantRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FactuurService {
    private FactuurRepository factuurRepository;
    private KlantRepository klantRepository;
    private ArtikelRepository artikelRepository;
    private KlantService klantService;

    public FactuurService(FactuurRepository factuurRepository, KlantRepository klantRepository, ArtikelRepository artikelRepository, KlantService klantService){
        this.factuurRepository = factuurRepository;
        this.klantRepository = klantRepository;
        this.artikelRepository = artikelRepository;
        this.klantService = klantService;
    }

    public List<FactuurOutputDto> getAllFacturen(){
        List<Factuur> facturenData = factuurRepository.findAll();
        List<FactuurOutputDto> facturen = new ArrayList<>();
        for(Factuur factuur : facturenData){
            facturen.add(transferFactuurToDto(factuur));
        }
        return facturen;
    }

    public FactuurOutputDto getFactuur(Long id){
        Optional<Factuur> optionalFactuur = factuurRepository.findById(id);
        if(optionalFactuur.isPresent()){
            return transferFactuurToDto(optionalFactuur.get());
        }
        else {
            throw new NotFoundException("Deze factuur bestaat niet");
        }
    }

    public Long createFactuur(FactuurInputDto factuurInput){
        Factuur factuur = new Factuur();
        Klant klant = klantRepository.getReferenceById(factuurInput.getKlantId());
        factuur.setFactuurKlant(klant);
        factuur.setFactuurDatum(factuurInput.getFactuurDatum());
        factuur.setBetaalDatum(factuurInput.getBetaalDatum());
        Double subTotaal = 0.00;
        Double btwTotaal = 0.00;
        Double totaalPrijs = 0.00;
        List<OrderRegel> orderRegels = new ArrayList<>();
        Integer regelNummer = 0;
        for(OrderRegelInputDto orderRegelInput : factuurInput.getOrderRegels()){
            OrderRegel orderRegel = new OrderRegel();
            regelNummer++;
            Artikel artikel = artikelRepository.getReferenceById(orderRegelInput.getArtikelId());
            orderRegel.setOrderArtikel(artikel);
            orderRegel.setRegelNummer(regelNummer);
            Integer aantal = orderRegelInput.getAantal();
            orderRegel.setAantal(aantal);
            Double regelTotaal = artikel.getPrijs() * aantal;
            orderRegel.setPrijs(regelTotaal);
            Integer percentageInclusiefBtw = 100 + artikel.getBtwPercentage();
            Double regelBtw = (regelTotaal / percentageInclusiefBtw) * artikel.getBtwPercentage();
            Double regelSubTotaal = regelTotaal - regelBtw;
            orderRegel.setBtw(regelBtw);
            orderRegels.add(orderRegel);
            subTotaal += regelSubTotaal;
            btwTotaal += regelBtw;
            totaalPrijs += regelTotaal;
        }
        factuur.setOrderRegels(orderRegels);
        factuur.setBtwTotaal(btwTotaal);
        factuur.setSubTotaal(subTotaal);
        Integer kortingPercentage = factuurInput.getKortingPercentage();
        if(kortingPercentage > 0){
            Double korting = (totaalPrijs / 100) * kortingPercentage;
            factuur.setKorting(korting);
            factuur.setTotaalPrijs(totaalPrijs - korting);
        }
        else {
            factuur.setTotaalPrijs(totaalPrijs);
            factuur.setKorting(0.00);
        }
        this.factuurRepository.save(factuur);
        return factuur.getFactuurId();
    }

    public FactuurOutputDto transferFactuurToDto(Factuur factuurData){
        FactuurOutputDto factuurDto = new FactuurOutputDto();
        factuurDto.setFactuurNummer(factuurData.getFactuurId());
        factuurDto.setFactuurDatum(factuurData.getFactuurDatum());
        factuurDto.setBetaalDatum(factuurData.getBetaalDatum());
        KlantOutputDto klantOutput = klantService.transferKlantToDto(factuurData.getFactuurKlant());
        factuurDto.setFactuurKlant(klantOutput);
        List<OrderRegel> orderRegels = factuurData.getOrderRegels();
        List<OrderRegelSummaryDto> orderRegelsOutput = new ArrayList<>();
        for(OrderRegel orderRegel : orderRegels){
            orderRegelsOutput.add(transferOrderRegelToSummaryDto(orderRegel));
        }
        factuurDto.setOrderRegels(orderRegelsOutput);
        factuurDto.setSubTotaal(factuurData.getSubTotaal());
        factuurDto.setBtwTotaal(factuurData.getBtwTotaal());
        factuurDto.setKorting(factuurData.getKorting());
        factuurDto.setTotaalPrijs(factuurData.getTotaalPrijs());
        return factuurDto;
    }

    public OrderRegelSummaryDto transferOrderRegelToSummaryDto(OrderRegel orderRegelData){
        OrderRegelSummaryDto orderRegelDto = new OrderRegelSummaryDto();
        orderRegelDto.setRegelNummer(orderRegelData.getRegelNummer());
        orderRegelDto.setArtikelNaam(orderRegelData.getOrderArtikel().getNaam());
        orderRegelDto.setAantal(orderRegelData.getAantal());
        orderRegelDto.setBtw(orderRegelData.getBtw());
        orderRegelDto.setRegelPrijs(orderRegelData.getPrijs());
        return orderRegelDto;
    }
}
