package ru.yandex.practicum.bliushtein.mod3.shared.dto.transfer;

public record TransferRequest(String source, String sourceCurrency,
                              String target, String targetCurrency,
                              int sourceAmount) {
}
