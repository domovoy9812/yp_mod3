package ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts;

public record AccountsTransferRequest(int amount, String targetUser, String targetCurrency, int targetAmount) {
}
