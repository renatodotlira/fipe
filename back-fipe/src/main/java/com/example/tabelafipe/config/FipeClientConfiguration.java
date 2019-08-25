package com.example.tabelafipe.config;

import com.example.tabelafipe.client.FipeClient;
import feign.Client;
import feign.Feign;
import feign.Logger;
import feign.Retryer;
import feign.jackson.JacksonDecoder;
import feign.jackson.JacksonEncoder;
import feign.slf4j.Slf4jLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FipeClientConfiguration {

    @Value("${application.client.endpoint.fipe}")
    private String coreUrl;
    private Client client;

    @Autowired
    public FipeClientConfiguration(Client client) {
        this.client = client;
    }

    @Bean
    FipeClient getCoreClient(){
        return Feign.builder()
                .client(client)
                .encoder(new JacksonEncoder())
                .decoder(new JacksonDecoder())
                .logger(new Slf4jLogger(FipeClient.class))
                .logLevel(Logger.Level.BASIC)
                .retryer(Retryer.NEVER_RETRY)
                .target(FipeClient.class, coreUrl);
    }
}
