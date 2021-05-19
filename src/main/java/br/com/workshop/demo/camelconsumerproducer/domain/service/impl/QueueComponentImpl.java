package br.com.workshop.demo.camelconsumerproducer.domain.service.impl;

import br.com.workshop.demo.camelconsumerproducer.domain.service.QueueComponent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.camel.ProducerTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class QueueComponentImpl implements QueueComponent {

    private final ProducerTemplate producerTemplate;

    private final ObjectMapper objectMapper;

    @Override
    public <T> void sendMessage(T message, String queueName) {
        try {
            producerTemplate.sendBody(queueName, objectMapper.writeValueAsString(message));
        } catch (Exception ex){
            log.error("The message {} wasn't sent to queue {}", message, queueName);
        }
    }
}
