package br.com.workshop.demo.camelconsumerproducer.domain.service;

import br.com.workshop.demo.camelconsumerproducer.domain.model.external.cryptocurrencyapi.coinmarket.Market;

public interface MarketService {
    void sendMarketToQueue(Market market);
}
