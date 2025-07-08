package ru.yandex.practicum.bliushtein.mod3.shared.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ExternalConfiguration {
    private final String uiServiceName;
    private final String accountsServiceName;
    private final String gatewayServiceName;
}
