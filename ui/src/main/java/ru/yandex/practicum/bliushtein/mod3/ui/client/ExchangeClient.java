package ru.yandex.practicum.bliushtein.mod3.ui.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;

import java.util.Map;

@Component
@Slf4j
public class ExchangeClient {
    private final RestClient.Builder restClientBuilder;
    private final ExternalConfiguration configuration;

    public ExchangeClient(@Autowired RestClient.Builder restClientBuilder,
                          @Autowired ExternalConfiguration configuration) {
        this.restClientBuilder = restClientBuilder;
        this.configuration = configuration;
    }

    public Map<String, Float> getExchangeRates() {
        RestClient restClient = restClientBuilder.build();
        return restClient.get()
                .uri("http://" + configuration.getGatewayServiceName() + "/exchange")
                .retrieve()
                .body(new ParameterizedTypeReference<Map<String, Float>>() {});
    }

}