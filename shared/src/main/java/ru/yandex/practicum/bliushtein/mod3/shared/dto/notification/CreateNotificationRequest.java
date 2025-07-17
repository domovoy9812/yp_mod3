package ru.yandex.practicum.bliushtein.mod3.shared.dto.notification;

public record CreateNotificationRequest(String source,
                                        String email,
                                        String subject,
                                        String message) { }
