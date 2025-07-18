package ru.yandex.practicum.bliushtein.mod3.exchangegenerator.client;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.exchange.UpdateExchangeRatesRequest;

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

    @Retry(name = "exchangeRetryConfig", fallbackMethod = "logError")
    public void updateExchangeRates(UpdateExchangeRatesRequest request) {
        RestClient restClient = restClientBuilder.build();
        GenericResponse response = restClient.put()
                .uri("http://" + configuration.getGatewayServiceName() + "/exchange")
                .contentType(MediaType.APPLICATION_JSON)
                .body(request)
                .retrieve()
                .body(GenericResponse.class);
        if (response.isSuccessful()) {
            log.info("Exchange rates updated. Current values: {}", request.exchangeRates());
        } else {
            log.warn("Error during exchange rates updated with values: {}. Error message: {}",
                    request.exchangeRates(), response.getErrorMessage());
        }
    }

    void logError(Throwable throwable) {
        log.error("Exception in ExchangeClient.", throwable);
    }
}