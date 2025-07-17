package ru.yandex.practicum.bliushtein.mod3.shared.dto.blocker;

public record BlockerValidationRequest(Operation operation, String source, String target, int amount, String email) {
    public enum Operation {
        GET_CACHE,
        ADD_MONEY,
        TRANSFER
    }
}
