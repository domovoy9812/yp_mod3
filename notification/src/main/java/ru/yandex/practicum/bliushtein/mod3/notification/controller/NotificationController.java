package ru.yandex.practicum.bliushtein.mod3.notification.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.bliushtein.mod3.notification.service.NotificationService;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.notification.CreateNotificationRequest;

@Slf4j
@RequestMapping("/notification")
@RestController
public class NotificationController {
    private final NotificationService notificationService;

    public NotificationController(@Autowired NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_notification.write')")
    public void sendNotification(@RequestBody CreateNotificationRequest request) {
        notificationService.createNotification(request.source(), request.email(), request.message());
    }
}
