package ru.yandex.practicum.bliushtein.mod3.shared.dto.blocker;

import java.time.ZonedDateTime;

public record BlockerValidationError(ZonedDateTime date,
                                     String operation,
                                     String message,
                                     String source,
                                     String target,
                                     int amount) {
}
