package ru.yandex.practicum.bliushtein.mod3.transfer.client;

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

    public GenericResponse validate(String source, String target, int amount, String email) {
        RestClient restClient = restClientBuilder.build();
        BlockerValidationRequest request = new BlockerValidationRequest(BlockerValidationRequest.Operation.TRANSFER,
                source, target, amount, email);
        return restClient.post()
                .uri("http://" + configuration.getGatewayServiceName() + "/blocker/validate")
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .body(GenericResponse.class);
    }
}