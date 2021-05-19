package br.com.workshop.demo.camelconsumerproducer.domain.service.impl;

import br.com.workshop.demo.camelconsumerproducer.domain.model.external.cryptocurrencyapi.coin.CoinSocialStats;
import br.com.workshop.demo.camelconsumerproducer.domain.model.external.cryptocurrencyapi.coinmarket.Market;
import br.com.workshop.demo.camelconsumerproducer.domain.model.external.cryptocurrencyapi.globalinformation.AllCoins;
import br.com.workshop.demo.camelconsumerproducer.domain.service.QueueComponent;
import br.com.workshop.demo.camelconsumerproducer.domain.service.TickerService;
import br.com.workshop.demo.camelconsumerproducer.infrastructure.api.CryptoCurrencyInformationAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TickerServiceImpl implements TickerService {

    private final CryptoCurrencyInformationAPI cryptoCurrencyInformationAPI;

    private final QueueComponent queueComponent;

    @Value("${application.queues.consumer.coin-url}")
    private String tickerQueueUrl;

    public ResponseEntity<AllCoins> findAllCoins() {
        return cryptoCurrencyInformationAPI.getAllCoins();
    }

    public ResponseEntity<List<Market>> findMarketsByCoinId(String id){
        return cryptoCurrencyInformationAPI.getMarketsByCoinId(id);
    }

    public ResponseEntity<CoinSocialStats> findSocialStatsByCoinId(String id){
        return cryptoCurrencyInformationAPI.getSocialStatsByCoinId(id);
    }

    public void stressApplication(){
        ResponseEntity<AllCoins> allCoinsResponseEntity = cryptoCurrencyInformationAPI.getAllCoins();
        allCoinsResponseEntity.getBody().getData().forEach(coinTicker -> {
            queueComponent.sendMessage(coinTicker, tickerQueueUrl);
        });
    }
}
