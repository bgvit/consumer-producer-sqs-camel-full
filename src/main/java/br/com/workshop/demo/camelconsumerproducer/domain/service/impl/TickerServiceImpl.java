package br.com.workshop.demo.camelconsumerproducer.domain.service.impl;

import br.com.workshop.demo.camelconsumerproducer.domain.Cacheable;
import br.com.workshop.demo.camelconsumerproducer.domain.model.external.cryptocurrencyapi.coin.CoinSocialStats;
import br.com.workshop.demo.camelconsumerproducer.domain.model.external.cryptocurrencyapi.coinmarket.Market;
import br.com.workshop.demo.camelconsumerproducer.domain.model.external.cryptocurrencyapi.globalinformation.AllCoins;
import br.com.workshop.demo.camelconsumerproducer.domain.service.CacheService;
import br.com.workshop.demo.camelconsumerproducer.domain.service.QueueComponent;
import br.com.workshop.demo.camelconsumerproducer.domain.service.TickerService;
import br.com.workshop.demo.camelconsumerproducer.infrastructure.api.CryptoCurrencyInformationAPI;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TickerServiceImpl implements TickerService {

    private final CryptoCurrencyInformationAPI cryptoCurrencyInformationAPI;

    private final QueueComponent queueComponent;

    private final CacheService cacheService;

    @Value("${application.queues.consumer.coin-url}")
    private String tickerQueueUrl;

    public ResponseEntity<AllCoins> findAllCoins() {

        AllCoins allCoinsCached = cacheService.get(cryptoCurrencyInformationAPI.getAllCoins().getBody().getClass(), "allCoins");

        if (Objects.isNull(allCoinsCached)){
            ResponseEntity<AllCoins> allcoins =  cryptoCurrencyInformationAPI.getAllCoins();
            Cacheable allCoinsCacheable = Cacheable.builder()
                    .key("allCoins")
                    .object(allcoins.getBody())
                    .period(12)
                    .timeUnit(TimeUnit.HOURS)
                    .build();
            cacheService.cacheable(allCoinsCacheable);
            return allcoins;
        }

        return ResponseEntity.ok(allCoinsCached);
    }

    public ResponseEntity<List<Market>> findMarketsByCoinId(String id){
        return cryptoCurrencyInformationAPI.getMarketsByCoinId(id);
    }

    public ResponseEntity<CoinSocialStats> findSocialStatsByCoinId(String id){
        return cryptoCurrencyInformationAPI.getSocialStatsByCoinId(id);
    }

    public void stressApplication(){
        ResponseEntity<AllCoins> allCoinsResponseEntity = cryptoCurrencyInformationAPI.getAllCoins();
        allCoinsResponseEntity.getBody().getData().forEach(coinTicker -> queueComponent.sendMessage(coinTicker, tickerQueueUrl));
    }
}
