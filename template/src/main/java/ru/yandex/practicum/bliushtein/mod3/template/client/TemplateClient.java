package ru.yandex.practicum.bliushtein.mod3.template.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;

@Component
@Slf4j
public class TemplateClient {
    private final RestClient.Builder restClientBuilder;
    private final ExternalConfiguration configuration;

    public TemplateClient(@Autowired RestClient.Builder restClientBuilder,
                          @Autowired ExternalConfiguration configuration) {
        this.restClientBuilder = restClientBuilder;
        this.configuration = configuration;
    }

}
