package br.com.workshop.demo.camelconsumerproducer.infrastructure.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.apache.camel.component.aws2.sqs.Sqs2Component;
import org.apache.camel.component.aws2.sqs.Sqs2Configuration;
import org.apache.camel.component.aws2.sqs.client.Sqs2ClientFactory;
import org.apache.camel.component.aws2.sqs.client.Sqs2InternalClient;
import software.amazon.awssdk.core.Protocol;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;

@Configuration
public class AWSConfig {

    @Value("${camel.component.aws2-sqs.proxy-host}")
    private String sqsHost;

    @Value("${camel.component.aws2-sqs.proxy-port}")
    private Integer sqsPort;

    @Value("${camel.component.aws2-sqs.region}")
    private Region sqsRegion;

    @Value("${camel.component.aws2-sqs.protocol}")
    private String protocol;


    @Bean("sqsClient")
    public SqsClient createSQSClient() {
        Sqs2Configuration clientConfiguration = new Sqs2Configuration();
        clientConfiguration.setProxyHost(sqsHost);
        clientConfiguration.setProxyPort(sqsPort);
        clientConfiguration.setProtocol(protocol);
        clientConfiguration.setProxyProtocol(Protocol.HTTP);
        clientConfiguration.setRegion(sqsRegion.toString());
//        clientConfiguration.setConcurrentConsumers(50);

        Sqs2Component sqsComponent = new Sqs2Component();
        sqsComponent.setConfiguration(clientConfiguration);

        Sqs2InternalClient internalClientConfiguration = Sqs2ClientFactory.getSqsClient(sqsComponent.getConfiguration());
        SqsClient sqsClient = internalClientConfiguration.getSQSClient();

        return sqsClient;
    }

}
