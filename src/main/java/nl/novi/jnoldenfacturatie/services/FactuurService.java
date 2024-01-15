package nl.novi.jnoldenfacturatie.services;
import nl.novi.jnoldenfacturatie.models.Artikel;
import nl.novi.jnoldenfacturatie.models.Factuur;
import nl.novi.jnoldenfacturatie.models.OrderRegel;
import nl.novi.jnoldenfacturatie.repositories.FactuurRepository;
import nl.novi.jnoldenfacturatie.repositories.OrderRegelRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FactuurService {
    private FactuurRepository factuurRepository;
    private OrderRegelRepository orderRegelRepository;

    public FactuurService(FactuurRepository factuurRepository, OrderRegelRepository orderRegelRepository){
        this.factuurRepository = factuurRepository;
        this.orderRegelRepository = orderRegelRepository;
    }

    public Long createFactuur(Factuur factuurInput, Artikel[] artikels){
        var factuur = new Factuur();
        factuur.setFactuurKlant(factuurInput.getFactuurKlant());
        factuur.setFactuurDatum(factuurInput.getFactuurDatum());
        factuur.setKorting(factuurInput.getKorting());
        Double subTotaal = 0.00;
        Double btwTotaal = 0.00;
        Double totaalPrijs = 0.00;
        List<OrderRegel> orderRegels = new ArrayList<>();
        for(Artikel artikel : artikels){
            var orderRegel = new OrderRegel();
            orderRegel.setOrderArtikel(artikel);
            Double regelTotaal = artikel.getPrijs();
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
        factuur.setTotaalPrijs(totaalPrijs);
        this.factuurRepository.save(factuur);
        return factuur.getFactuurId();
    }
}
