package ru.yandex.practicum.bliushtein.mod3.blocker.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
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

    @CircuitBreaker(name = "blockerCircuitBreaker", fallbackMethod = "logNotificationError")
    public void sendNotification(String email, String subject, String message) {
        RestClient restClient = restClientBuilder.build();
        CreateNotificationRequest request =
                new CreateNotificationRequest("blocker-application", email, subject, message);
        restClient.post()
                .uri("http://" + configuration.getGatewayServiceName() + "/notification")
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity();
    }

    public void logNotificationError(Throwable throwable) {
        log.error("Error during notification send", throwable);
    }
}
