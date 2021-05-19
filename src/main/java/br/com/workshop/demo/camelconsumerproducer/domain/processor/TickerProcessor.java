package br.com.workshop.demo.camelconsumerproducer.domain.processor;

import br.com.workshop.demo.camelconsumerproducer.domain.model.external.cryptocurrencyapi.coin.Ticker;
import lombok.RequiredArgsConstructor;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TickerProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        Ticker ticker = exchange.getIn().getBody(Ticker.class);
        System.out.println(ticker);
    }


}

