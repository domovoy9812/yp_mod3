package ru.yandex.practicum.bliushtein.mod3.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bliushtein.mod3.notification.NotificationException;
import ru.yandex.practicum.bliushtein.mod3.notification.data.entity.NotificationEntity;
import ru.yandex.practicum.bliushtein.mod3.notification.data.repository.NotificationRepository;

import java.time.ZonedDateTime;

@Component
@Slf4j
public class NotificationSender {
    private final NotificationRepository notificationRepository;

    public NotificationSender(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void sendNotification(Long id) {
        NotificationEntity notification = notificationRepository.findById(id)
                .orElseThrow(() -> NotificationException.notificationIsNotFound(id));
        //TODO implement email send
        log.debug("send email source:'{}' to:'{}' subject:'{}' message:'{}'",
                notification.getSource(),
                notification.getEmail(),
                notification.getSubject(),
                notification.getMessage());
        notification.setSendDate(ZonedDateTime.now());
        notification.setSent(true);
        notificationRepository.save(notification);
    }


    @Transactional
    public void increaseRetryCount(NotificationEntity notification) {
        notification.setRetryCount(notification.getRetryCount() + 1);
        notificationRepository.save(notification);
    }
}
