package ru.yandex.practicum.bliushtein.mod3.accounts.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bliushtein.mod3.accounts.AccountServiceException;
import ru.yandex.practicum.bliushtein.mod3.accounts.service.AccountService;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.Account;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.ChangeAccountBalanceRequest;

import java.util.List;

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
    public Account getAccount(@PathVariable String user, @PathVariable String currency) throws AccountServiceException {
        return accountService.findAccount(user, currency);
    }
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('SCOPE_accounts.read')")
    public List<Account> getAllAccounts(@PathVariable String user) {
        return accountService.findAllAccounts(user);
    }
    @DeleteMapping("/{currency}")
    @PreAuthorize("hasAuthority('SCOPE_accounts.write')")
    public void deleteAccount(@PathVariable String user, @PathVariable String currency) throws AccountServiceException {
        accountService.deleteAccount(user, currency);
    }
    @PostMapping("/{currency}")
    @PreAuthorize("hasAuthority('SCOPE_accounts.write')")
    public Account createAccount(@PathVariable String user, @PathVariable String currency)
            throws AccountServiceException {
        return accountService.createAccount(user, currency);
    }
    @PatchMapping("/{currency}")
    @PreAuthorize("hasAuthority('SCOPE_accounts.write')")
    public Account changeAccountBalance(@PathVariable String user, @PathVariable String currency,
                                     @RequestBody ChangeAccountBalanceRequest request) throws AccountServiceException {
        return accountService.updateBalance(user, currency, request.change());
    }

    @ExceptionHandler
    public void handleException(Throwable exception) throws Throwable {
        log.error("-----exception in BankUserController: " + exception.getMessage(), exception);
        throw exception;
    }
}