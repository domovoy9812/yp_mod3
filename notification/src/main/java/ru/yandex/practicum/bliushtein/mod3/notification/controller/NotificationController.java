package ru.yandex.practicum.bliushtein.mod3.notification.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bliushtein.mod3.notification.service.NotificationService;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;
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
    public GenericResponse sendNotification(@RequestBody CreateNotificationRequest request) {
        notificationService.createNotification(request.source(), request.email(), request.subject(), request.message());
        return GenericResponse.ok();
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public GenericResponse handleException(Exception exception) {
        return GenericResponse.fail(exception.getMessage());
    }
}
