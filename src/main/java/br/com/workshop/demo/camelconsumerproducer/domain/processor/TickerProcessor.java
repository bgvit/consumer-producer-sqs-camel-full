package br.com.workshop.demo.camelconsumerproducer.domain.processor;

import br.com.workshop.demo.camelconsumerproducer.domain.model.external.cryptocurrencyapi.coin.Ticker;
import br.com.workshop.demo.camelconsumerproducer.domain.model.external.cryptocurrencyapi.coinmarket.Market;
import br.com.workshop.demo.camelconsumerproducer.domain.service.TickerService;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TickerProcessor implements Processor {

    private final TickerService tickerService;

    @Override
    public void process(Exchange exchange) throws Exception {
        Ticker ticker = exchange.getIn().getBody(Ticker.class);
        ResponseEntity<List<Market>> coinMarkets = tickerService.findMarketsByCoinId(ticker.getId());

        if (coinMarkets.getStatusCode().equals(HttpStatus.OK.value())) {
            Optional<Market> marketOptional = coinMarkets.getBody().stream().findFirst();

            if (marketOptional.isPresent()) {
                Market market = marketOptional.get();

            }
        }
    }


}

