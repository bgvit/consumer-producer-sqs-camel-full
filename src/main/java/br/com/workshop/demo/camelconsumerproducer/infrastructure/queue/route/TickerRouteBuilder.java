package br.com.workshop.demo.camelconsumerproducer.infrastructure.queue.route;

import br.com.workshop.demo.camelconsumerproducer.domain.model.external.cryptocurrencyapi.coin.Ticker;
import br.com.workshop.demo.camelconsumerproducer.domain.processor.TickerProcessor;
import lombok.RequiredArgsConstructor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TickerRouteBuilder extends RouteBuilder {

    private final TickerProcessor tickerProcessor;

    @Override
    public void configure() throws Exception {
        from("{{application.queues.coin-url}}")
        .unmarshal()
        .json(JsonLibrary.Jackson, Ticker.class)
        .process(tickerProcessor);
    }
}
