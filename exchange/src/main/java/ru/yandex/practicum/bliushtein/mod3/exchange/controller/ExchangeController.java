package ru.yandex.practicum.bliushtein.mod3.exchange.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.exchange.ExchangeRequest;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.exchange.ExchangeResponse;
import ru.yandex.practicum.bliushtein.mod3.exchange.service.ExchangeService;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.exchange.UpdateExchangeRatesRequest;

import java.util.Map;

@Slf4j
@RequestMapping("/exchange")
@RestController
public class ExchangeController {
    private final ExchangeService exchangeService;

    public ExchangeController(@Autowired ExchangeService exchangeService) {
        this.exchangeService = exchangeService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('SCOPE_exchange.read')")
    public ExchangeResponse exchange(@RequestBody ExchangeRequest request) {
        return ExchangeResponse.ok(exchangeService.exchange(request.sourceCurrency(), request.targetCurrency(),
                request.amount()));
    }

    @GetMapping
    @PreAuthorize("hasAuthority('SCOPE_exchange.read')")
    public Map<String, Float> getExchangeRates() {
        return exchangeService.getExchangeRates();
    }

    @PutMapping
    @PreAuthorize("hasAuthority('SCOPE_exchange.write')")
    public GenericResponse updateExchangeRates(@RequestBody UpdateExchangeRatesRequest request) {
        exchangeService.updateExchangeRates(request.exchangeRates());
        return GenericResponse.ok();
    }

    @ExceptionHandler
    public GenericResponse handleException(Throwable exception) {
        log.error("exception in ExchangeController.", exception);
        return GenericResponse.fail(exception.getMessage());
    }
}