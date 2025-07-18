package ru.yandex.practicum.bliushtein.mod3.accounts.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bliushtein.mod3.accounts.AccountServiceException;
import ru.yandex.practicum.bliushtein.mod3.accounts.service.AccountService;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.*;

@Slf4j
@RequestMapping("/account-service/user/{user}/account")
@RestController
public class AccountController {
    private final AccountService accountService;

    public AccountController(@Autowired AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping("/{currency}")
    @PreAuthorize("hasAuthority('SCOPE_accounts.read')")
    public AccountResponse getAccount(@PathVariable String user, @PathVariable String currency)
            throws AccountServiceException {
        return AccountResponse.ok(accountService.findAccount(user, currency));
    }

    @GetMapping("/all")
    @PreAuthorize("hasAuthority('SCOPE_accounts.read')")
    public AccountsResponse getAllAccounts(@PathVariable String user) {
        return AccountsResponse.ok(accountService.findAllAccounts(user));
    }

    @DeleteMapping("/{currency}")
    @PreAuthorize("hasAuthority('SCOPE_accounts.write')")
    public GenericResponse deleteAccount(@PathVariable String user, @PathVariable String currency)
            throws AccountServiceException {
        accountService.deleteAccount(user, currency);
        return GenericResponse.ok();
    }
    @PostMapping("/{currency}")
    @PreAuthorize("hasAuthority('SCOPE_accounts.write')")
    public AccountResponse createAccount(@PathVariable String user, @PathVariable String currency)
            throws AccountServiceException {
        return AccountResponse.ok(accountService.createAccount(user, currency));
    }

    @PatchMapping("/{currency}")
    @PreAuthorize("hasAuthority('SCOPE_accounts.write')")
    public AccountResponse changeAccountBalance(@PathVariable String user, @PathVariable String currency,
                                                @RequestBody ChangeAccountBalanceRequest request)
            throws AccountServiceException {
        return AccountResponse.ok(accountService.updateBalance(user, currency, request.change()));
    }

    @PatchMapping("/{currency}/transfer")
    @PreAuthorize("hasAuthority('SCOPE_accounts.write')")
    public AccountsTransferResponse transfer(@PathVariable String user, @PathVariable String currency,
                                             @RequestBody AccountsTransferRequest request)
            throws AccountServiceException {
        return accountService.transfer(user, currency, request);
    }

    @ExceptionHandler
    public GenericResponse handleException(Throwable exception) {
        log.error("exception in AccountController.", exception);
        return GenericResponse.fail(exception.getMessage());
    }
}