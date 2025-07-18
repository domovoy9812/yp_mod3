package ru.yandex.practicum.bliushtein.mod3.shared.dto.exchange;

import java.util.Map;

public record UpdateExchangeRatesRequest(Map<String, Float> exchangeRates) {
}
