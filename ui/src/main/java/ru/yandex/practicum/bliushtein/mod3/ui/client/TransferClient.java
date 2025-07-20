package ru.yandex.practicum.bliushtein.mod3.ui.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.AccountsTransferResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.transfer.TransferRequest;

@Slf4j
@Component
public class TransferClient {
    private final static String TRANSFER_URL_TEMPLATE = "http://%s/transfer";

    private final ExternalConfiguration extConfig;
    private final RestClient.Builder restClientBuilder;
    public TransferClient(@Autowired ExternalConfiguration extConfig,
                          @Autowired RestClient.Builder restClientBuilder) {
        this.extConfig = extConfig;
        this.restClientBuilder = restClientBuilder;
    }

    public AccountsTransferResponse transfer(String sourceUser, String sourceCurrency,
                                             String targetUser, String targetCurrency,
                                             int sourceAmount) {
        return restClientBuilder.build().post()
                .uri(TRANSFER_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()))
                .body(new TransferRequest(sourceUser, sourceCurrency, targetUser, targetCurrency, sourceAmount))
                .retrieve()
                .body(AccountsTransferResponse.class);
    }

}
