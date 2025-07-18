package ru.yandex.practicum.bliushtein.mod3.exchange.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bliushtein.mod3.exchange.ExchangeException;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Service
public class ExchangeService {
    private final static String BASE_CURRENCY = "RUR";
    private final ConcurrentHashMap<String, Float> exchangeRates;

    public ExchangeService() {
        this.exchangeRates = new ConcurrentHashMap<>();
    }

    public int exchange(String sourceCurrency, String targetCurrency, int amount) {
        if (Objects.equals(sourceCurrency, targetCurrency)) {
            return amount;
        }
        if (BASE_CURRENCY.equals(sourceCurrency)) {
            Float exchangeRate = getExchangeRate(targetCurrency);
            return Math.round(amount * exchangeRate);
        }
        if (BASE_CURRENCY.equals(targetCurrency)) {
            Float exchangeRate = getExchangeRate(sourceCurrency);
            return Math.round(amount / exchangeRate);
        }
        Float sourceExchangeRate = getExchangeRate(sourceCurrency);
        Float targetExchangeRate = getExchangeRate(targetCurrency);
        return Math.round(amount / sourceExchangeRate * targetExchangeRate);
    }

    private Float getExchangeRate(String sourceCurrency) {
        Float exchangeRate = exchangeRates.get(sourceCurrency);
        if (exchangeRate == null) {
            throw ExchangeException.incorrectCurrency(sourceCurrency);
        }
        return exchangeRate;
    }

    public void updateExchangeRates(Map<String, Float> newExchangeRates) {
        validateExchangeRates(newExchangeRates);
        exchangeRates.putAll(newExchangeRates);
    }

    private static void validateExchangeRates(Map<String, Float> newExchangeRates) {
        newExchangeRates.forEach((currency, exchangeRate) -> {
            if (BASE_CURRENCY.equals(currency)) {
                throw ExchangeException.cantSetExchangeRateForBaseCurrency();
            }
            if (exchangeRate < 0.001f || exchangeRate > 10000f) {
                throw ExchangeException.incorrectExchangeRate(exchangeRate, currency);
            }
        });
    }

    public Map<String, Float> getExchangeRates() {
        return exchangeRates;
    }
}
