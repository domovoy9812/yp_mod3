package ru.yandex.practicum.bliushtein.mod3.notification.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bliushtein.mod3.notification.data.entity.NotificationEntity;
import ru.yandex.practicum.bliushtein.mod3.notification.data.repository.NotificationRepository;

@Slf4j
@Service
public class NotificationService {
    private final NotificationRepository notificationRepository;
    public NotificationService(@Autowired NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional
    public void createNotification(String source, String email, String subject, String message) {
        notificationRepository.save(new NotificationEntity(source, email, subject, message, false, 0));
    }

}
