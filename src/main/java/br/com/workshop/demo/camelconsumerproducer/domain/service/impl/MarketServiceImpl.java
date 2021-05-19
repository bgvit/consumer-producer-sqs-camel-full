package br.com.workshop.demo.camelconsumerproducer.domain.service.impl;

import br.com.workshop.demo.camelconsumerproducer.domain.model.external.cryptocurrencyapi.coinmarket.Market;
import br.com.workshop.demo.camelconsumerproducer.domain.service.MarketService;
import br.com.workshop.demo.camelconsumerproducer.domain.service.QueueComponent;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MarketServiceImpl implements MarketService {


    @Value("${application.queues.producer.market-url}")
    private String marketQueueUrl;

    private final QueueComponent queueComponent;

    public void sendMarketToQueue(Market market) {
        queueComponent.sendMessage(market, marketQueueUrl);
    }
}
