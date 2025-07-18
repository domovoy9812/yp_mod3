package ru.yandex.practicum.bliushtein.mod3.shared.dto.exchange;

public record ExchangeRequest(String sourceCurrency, String targetCurrency, int amount) {
}
