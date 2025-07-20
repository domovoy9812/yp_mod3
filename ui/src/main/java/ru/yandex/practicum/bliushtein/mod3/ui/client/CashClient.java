package ru.yandex.practicum.bliushtein.mod3.ui.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.*;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.cash.CashRequest;

@Slf4j
@Component
public class CashClient {
    private final static String GET_CASH_URL_TEMPLATE = "http://%s/cash/get-cash";
    private final static String ADD_MONEY_URL_TEMPLATE = "http://%s/cash/add-money";

    private final ExternalConfiguration extConfig;
    private final RestClient.Builder restClientBuilder;
    public CashClient(@Autowired ExternalConfiguration extConfig,
                      @Autowired RestClient.Builder restClientBuilder) {
        this.extConfig = extConfig;
        this.restClientBuilder = restClientBuilder;
    }

    public AccountResponse getCash(String user, String currency, int amount) {
        return restClientBuilder.build().post()
                .uri(GET_CASH_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()))
                .body(new CashRequest(user, currency, amount))
                .retrieve()
                .body(AccountResponse.class);
    }

    public AccountResponse addMoney(String user, String currency, int amount) {
        return restClientBuilder.build().post()
                .uri(ADD_MONEY_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()))
                .body(new CashRequest(user, currency, amount))
                .retrieve()
                .body(AccountResponse.class);
    }

}
