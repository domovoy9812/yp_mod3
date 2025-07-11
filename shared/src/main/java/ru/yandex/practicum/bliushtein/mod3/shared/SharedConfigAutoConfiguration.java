package ru.yandex.practicum.bliushtein.mod3.shared;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.context.annotation.Bean;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;

@AutoConfiguration
public class SharedConfigAutoConfiguration {

    @Bean
    public ExternalConfiguration externalConfiguration(@Value("${ui-service-name}") String uiServiceName,
                                                       @Value("${accounts-service-name}") String accountsServiceName,
                                                       @Value("${gateway-service-name}") String gatewayServiceName) {
        return new ExternalConfiguration(uiServiceName, accountsServiceName, gatewayServiceName);
    }
}
