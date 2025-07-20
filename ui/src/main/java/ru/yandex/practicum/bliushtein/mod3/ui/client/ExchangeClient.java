package ru.yandex.practicum.bliushtein.mod3.ui.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;

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

/*    public int exchange(String sourceCurrency, String targetCurrency, int amount) {
        RestClient restClient = restClientBuilder.build();
        ExchangeResponse response = restClient.post()
                .uri("http://" + configuration.getGatewayServiceName() + "/exchange")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ExchangeRequest(sourceCurrency, targetCurrency, amount))
                .retrieve()
                .body(ExchangeResponse.class);
        if (response.isSuccessful()) {
            return response.getAmount();
        } else {
            throw TransferException.exchangeFailed(response.getErrorMessage());
        }
    }*/

}