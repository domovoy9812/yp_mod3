package ru.yandex.practicum.bliushtein.mod3.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bliushtein.mod3.notification.data.entity.NotificationEntity;
import ru.yandex.practicum.bliushtein.mod3.notification.data.repository.NotificationRepository;

import java.util.List;

@Service
@Slf4j
public class NotificationJobService {
    private final NotificationRepository notificationRepository;
    private final NotificationSender notificationSender;
    public NotificationJobService(@Autowired NotificationRepository notificationRepository,
                                  @Autowired NotificationSender notificationSender) {
        this.notificationRepository = notificationRepository;
        this.notificationSender = notificationSender;
    }

    @Scheduled(fixedRate = 10_000L)
    public void sendNotifications() {
        List<NotificationEntity> notificationsToSend = notificationRepository.
                findBySentAndRetryCountLessThan(false, 3, PageRequest.ofSize(30));
        notificationsToSend.forEach(notification -> {
            try {
                notificationSender.sendNotification(notification.getId());
            } catch (Exception e) {
                log.error("Error during send notification with id='%d'".formatted(notification.getId()), e);
                notificationSender.increaseRetryCount(notification);
            }
        });
    }
}
