package ru.yandex.practicum.bliushtein.mod3.accounts.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bliushtein.mod3.accounts.AccountServiceException;
import ru.yandex.practicum.bliushtein.mod3.accounts.service.BankUserService;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUser;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUserWithPassword;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.CreateUserRequest;

@Slf4j
@RestController
@RequestMapping("/account-service/user")
public class BankUserController {
    private final BankUserService bankUserService;

    public BankUserController(@Autowired BankUserService bankUserService) {
        this.bankUserService = bankUserService;
    }

    @GetMapping("/{name}")
    @PreAuthorize("hasAuthority('SCOPE_accounts.read')")
    public BankUser findBankUserByName(@PathVariable String name) {
        return bankUserService.findBankUser(name);
    }

    @GetMapping("/authenticate/{name}")
    @PreAuthorize("hasAuthority('SCOPE_accounts.read')")
    public BankUserWithPassword findBankUserWithPasswordByName(@PathVariable String name) {
        return bankUserService.findBankUserToAuthenticate(name);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_accounts.write')")
    public BankUser createBankUser(@RequestBody CreateUserRequest request) {
        return bankUserService.createBankUser(request.name(), request.password(), request.firstName(),
                request.lastName(), request.email());
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasAuthority('SCOPE_accounts.write')")
    public void deleteBankUser(@PathVariable String name) throws AccountServiceException {
        bankUserService.deleteBankUser(name);
    }

    @ExceptionHandler
    public void handleException(Throwable exception) throws Throwable {
        log.error("-----exception in BankUserController: " + exception.getMessage(), exception);
        throw exception;
    }
}
