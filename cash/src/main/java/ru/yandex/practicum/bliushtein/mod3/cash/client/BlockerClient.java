package ru.yandex.practicum.bliushtein.mod3.cash.client;

import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.blocker.BlockerValidationRequest;

@Component
@Slf4j
public class BlockerClient {
    private final RestClient.Builder restClientBuilder;
    private final ExternalConfiguration configuration;

    public BlockerClient(@Autowired RestClient.Builder restClientBuilder,
                         @Autowired ExternalConfiguration configuration) {
        this.restClientBuilder = restClientBuilder;
        this.configuration = configuration;
    }

    @Retry(name = "blockerRetryConfig", fallbackMethod = "validateFallback")
    public GenericResponse validate(String user, String currency, int amount, boolean isGetCash, String email) {
        RestClient restClient = restClientBuilder.build();
        BlockerValidationRequest request = new BlockerValidationRequest(
                isGetCash ? BlockerValidationRequest.Operation.GET_CACHE : BlockerValidationRequest.Operation.ADD_MONEY,
                user, null, amount, email);
        return restClient.post()
                .uri("http://" + configuration.getGatewayServiceName() + "/blocker/validate")
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(GenericResponse.class);
    }

    public GenericResponse validateFallback(Throwable throwable) {
        log.error("error in BlockerClient.validate", throwable);
        return GenericResponse.fail(throwable.getMessage());
    }
}