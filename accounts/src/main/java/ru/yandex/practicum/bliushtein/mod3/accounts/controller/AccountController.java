package ru.yandex.practicum.bliushtein.mod3.accounts.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
    public Account getAccount(@PathVariable String user, @PathVariable String currency) throws AccountServiceException {
        return accountService.findAccount(user, currency);
    }

    @GetMapping("/all")
    public List<Account> getAllAccounts(@PathVariable String user) {
        return accountService.findAllAccounts(user);
    }

    @DeleteMapping("/{currency}")
    public void deleteAccount(@PathVariable String user, @PathVariable String currency) throws AccountServiceException {
        accountService.deleteAccount(user, currency);
    }

    @PostMapping("/{currency}")
    public Account createAccount(@PathVariable String user, @PathVariable String currency)
            throws AccountServiceException {
        return accountService.createAccount(user, currency);
    }

    @PatchMapping("/{currency}")
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