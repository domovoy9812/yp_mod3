package ru.yandex.practicum.bliushtein.mod3.accounts.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.notification.CreateNotificationRequest;

@Component
@Slf4j
public class NotificationClient {
    private final RestClient.Builder restClientBuilder;
    private final ExternalConfiguration configuration;

    public NotificationClient(@Autowired RestClient.Builder restClientBuilder,
                              @Autowired ExternalConfiguration configuration) {
        this.restClientBuilder = restClientBuilder;
        this.configuration = configuration;
    }

    public void sendNotification(String email, String message) {
        RestClient restClient = restClientBuilder.build();
        CreateNotificationRequest request = new CreateNotificationRequest("accounts-application", email, message);
        restClient.post()
                .uri("http://" + configuration.getGatewayServiceName() + "/notification")
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity();
    }
}
