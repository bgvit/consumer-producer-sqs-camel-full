package br.com.workshop.demo.camelconsumerproducer.domain.service;

import org.springframework.stereotype.Component;

@Component
public interface QueueComponent {
    <T> void sendMessage(T message, String queueName);
}
