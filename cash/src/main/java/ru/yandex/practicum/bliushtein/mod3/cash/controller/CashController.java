package ru.yandex.practicum.bliushtein.mod3.cash.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.AccountResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.cash.CashRequest;
import ru.yandex.practicum.bliushtein.mod3.cash.service.CashService;

@Slf4j
@RequestMapping("/cash")
@RestController
public class CashController {
    private final CashService cashService;

    public CashController(@Autowired CashService cashService) {
        this.cashService = cashService;
    }

    @PostMapping("/get-cash")
    @PreAuthorize("hasAuthority('SCOPE_cash.write')")
    public AccountResponse getCash(@RequestBody CashRequest request) {
        return cashService.getCash(request);
    }

    @PostMapping("/add-money")
    @PreAuthorize("hasAuthority('SCOPE_cash.write')")
    public AccountResponse addMoney(@RequestBody CashRequest request) {
        return cashService.addMoney(request);
    }

    @ExceptionHandler
    public GenericResponse handleException(Throwable exception) {
        log.error("exception in CashController.", exception);
        return GenericResponse.fail(exception.getMessage());
    }
}