package ru.yandex.practicum.bliushtein.mod3.shared.dto.notification;

import java.time.ZonedDateTime;

public record Notification(String source, String email, String message, ZonedDateTime sendDate) {
}
