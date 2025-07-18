package ru.yandex.practicum.bliushtein.mod3.transfer.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.notification.CreateNotificationRequest;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.transfer.TransferRequest;

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

    public void sendSuccessNotification(String email, TransferRequest request) {
        sendNotification(email, "Successful transaction",
                "Transferred '%d' %s to user: '%s'".formatted(request.sourceAmount(),
                        request.sourceCurrency(), request.target()));
    }

    public void sendFailNotification(String email, TransferRequest request, String errorMessage) {
        sendNotification(email, "Transaction failed",
                "Details: '%d' %s to user: '%s'. Reason: '%s'".formatted(request.sourceAmount(),
                        request.sourceCurrency(), request.target(), errorMessage));
    }

    @CircuitBreaker(name = "notificationsCircuitBreaker", fallbackMethod = "sendNotificationFallback")
    public void sendNotification(String email, String subject, String message) {
        RestClient restClient = restClientBuilder.build();
        CreateNotificationRequest request = new CreateNotificationRequest("transfer-application", email, subject, message);
        restClient.post()
                .uri("http://" + configuration.getGatewayServiceName() + "/notification")
                .body(request)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .toBodilessEntity();
    }

    public void sendNotificationFallback(Throwable throwable) {
        log.error("Send notification failed.", throwable);
    }
}