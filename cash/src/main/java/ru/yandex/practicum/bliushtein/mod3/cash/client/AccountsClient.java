package ru.yandex.practicum.bliushtein.mod3.cash.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.AccountResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUserResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.ChangeAccountBalanceRequest;

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

    @CircuitBreaker(name = "accountsCircuitBreaker", fallbackMethod = "changeAccountBalanceFallback")
    public AccountResponse changeAccountBalance(String user, String currency, int change) {
        RestClient restClient = restClientBuilder.build();
        return restClient.patch().uri("http://" + configuration.getGatewayServiceName()
                        + "/account-service/user/{user}/account/{currency}", user, currency)
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ChangeAccountBalanceRequest(change))
                .retrieve()
                .body(AccountResponse.class);
    }

    @CircuitBreaker(name = "accountsCircuitBreaker", fallbackMethod = "findBankUserFallback")
    public BankUserResponse findBankUser(String user) {
        RestClient restClient = restClientBuilder.build();
        return restClient.get()
                .uri("http://" + configuration.getGatewayServiceName() + "/account-service/user/{user}", user)
                .retrieve()
                .body(BankUserResponse.class);
    }

    public AccountResponse changeAccountBalanceFallback(Throwable throwable) {
        log.error("error in AccountsClient.changeAccountBalance", throwable);
        return AccountResponse.fail(throwable.getMessage());
    }

    public BankUserResponse findBankUserFallback(Throwable throwable) {
        log.error("error in AccountsClient.findBankUser", throwable);
        return BankUserResponse.fail(throwable.getMessage());
    }
}