package ru.yandex.practicum.bliushtein.mod3.transfer.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.*;

@Component
@Slf4j
public class AccountsClient {
    private final RestClient.Builder restClientBuilder;
    private final ExternalConfiguration configuration;

    public AccountsClient(@Autowired RestClient.Builder restClientBuilder,
                          @Autowired ExternalConfiguration configuration) {
        this.restClientBuilder = restClientBuilder;
        this.configuration = configuration;
    }

    public AccountsTransferResponse transfer(String user, String currency, AccountsTransferRequest accountsTransferRequest) {
        RestClient restClient = restClientBuilder.build();
        return restClient.patch().uri("http://" + configuration.getGatewayServiceName()
                        + "/account-service/user/{user}/account/{currency}/transfer", user, currency)
                .contentType(MediaType.APPLICATION_JSON)
                .body(accountsTransferRequest)
                .retrieve()
                .body(AccountsTransferResponse.class);
    }

    public BankUserResponse findBankUser(String user) {
        RestClient restClient = restClientBuilder.build();
        return restClient.get()
                .uri("http://" + configuration.getGatewayServiceName() + "/account-service/user/{user}", user)
                .retrieve()
                .body(BankUserResponse.class);
    }
}