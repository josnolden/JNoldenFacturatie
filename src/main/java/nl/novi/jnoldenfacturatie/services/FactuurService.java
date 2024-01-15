package nl.novi.jnoldenfacturatie.services;
import nl.novi.jnoldenfacturatie.dtos.FactuurInputDto;
import nl.novi.jnoldenfacturatie.dtos.OrderRegelInputDto;
import nl.novi.jnoldenfacturatie.models.Artikel;
import nl.novi.jnoldenfacturatie.models.Factuur;
import nl.novi.jnoldenfacturatie.models.Klant;
import nl.novi.jnoldenfacturatie.models.OrderRegel;
import nl.novi.jnoldenfacturatie.repositories.ArtikelRepository;
import nl.novi.jnoldenfacturatie.repositories.FactuurRepository;
import nl.novi.jnoldenfacturatie.repositories.KlantRepository;
import nl.novi.jnoldenfacturatie.repositories.OrderRegelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FactuurService {
    private FactuurRepository factuurRepository;
    private OrderRegelRepository orderRegelRepository;
    private KlantRepository klantRepository;
    private ArtikelRepository artikelRepository;

    public FactuurService(FactuurRepository factuurRepository, OrderRegelRepository orderRegelRepository, KlantRepository klantRepository, ArtikelRepository artikelRepository){
        this.factuurRepository = factuurRepository;
        this.orderRegelRepository = orderRegelRepository;
        this.klantRepository = klantRepository;
        this.artikelRepository = artikelRepository;
    }

    public Long createFactuur(FactuurInputDto factuurInput){
        Factuur factuur = new Factuur();
        Klant klant = klantRepository.getReferenceById(factuurInput.getFactuurKlant());
        factuur.setFactuurKlant(klant);
        factuur.setFactuurDatum(factuurInput.getFactuurDatum());
        factuur.setBetaalDatum(factuurInput.getBetaalDatum());
        Double subTotaal = 0.00;
        Double btwTotaal = 0.00;
        Double totaalPrijs = 0.00;
        List<OrderRegel> orderRegels = new ArrayList<>();
        for(OrderRegelInputDto orderRegelInput : factuurInput.getOrderRegels()){
            OrderRegel orderRegel = new OrderRegel();
            Artikel artikel = artikelRepository.getReferenceById(orderRegelInput.getArtikelId());
            orderRegel.setOrderArtikel(artikel);
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
}
