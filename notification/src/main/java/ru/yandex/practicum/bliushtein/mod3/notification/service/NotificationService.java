package ru.yandex.practicum.bliushtein.mod3.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bliushtein.mod3.notification.data.entity.NotificationEntity;
import ru.yandex.practicum.bliushtein.mod3.notification.data.repository.NotificationRepository;

import java.time.ZonedDateTime;

@Slf4j
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    public NotificationService(@Autowired NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void createNotification(String source, String email, String message) {
        NotificationEntity notificationEntity = notificationRepository.save(
                new NotificationEntity(source, email, message));
        sendNotification(notificationEntity);
    }

    public void sendNotification(NotificationEntity notification) {
        try {
            //TODO implement email send
            log.debug("send email source:'{}' to:'{}' message:'{}'", notification.getSource(), notification.getEmail(),
                    notification.getMessage());
            notification.setSendDate(ZonedDateTime.now());
            notificationRepository.save(notification);
        } catch (Throwable throwable) {
            log.error("Error during email send", throwable);
        }
    }
}
