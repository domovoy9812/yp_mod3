package ru.yandex.practicum.bliushtein.mod3.exchangegenerator.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bliushtein.mod3.exchangegenerator.client.ExchangeClient;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.exchange.UpdateExchangeRatesRequest;

import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
public class ExchangeGeneratorService {
    private final ExchangeClient exchangeClient;

    public ExchangeGeneratorService(@Autowired ExchangeClient exchangeClient) {
        this.exchangeClient = exchangeClient;
    }

    @Scheduled(fixedDelay = 60_000L)
    public void updateExchangeRates() {
        Map<String, Float> exchageRates = Map.of("USD", getRandomExchangeRate(), "CNY", getRandomExchangeRate());
        exchangeClient.updateExchangeRates(new UpdateExchangeRatesRequest(exchageRates));
    }

    private static float getRandomExchangeRate() {
        return ThreadLocalRandom.current().nextFloat(0.5f, 2f);
    }
}
