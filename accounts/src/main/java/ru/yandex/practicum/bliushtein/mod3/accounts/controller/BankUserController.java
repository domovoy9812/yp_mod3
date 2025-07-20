package ru.yandex.practicum.bliushtein.mod3.accounts.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bliushtein.mod3.accounts.AccountServiceException;
import ru.yandex.practicum.bliushtein.mod3.accounts.service.BankUserService;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.*;

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
    public BankUserResponse findBankUserByName(@PathVariable String name) {
        BankUser bankUser = bankUserService.findBankUser(name);
        return bankUser == null ? BankUserResponse.fail("User is not found") : BankUserResponse.ok(bankUser);
    }

    @GetMapping("/authenticate/{name}")
    @PreAuthorize("hasAuthority('SCOPE_accounts.read')")
    public BankUserWithPassword findBankUserWithPasswordByName(@PathVariable String name) {
        return bankUserService.findBankUserToAuthenticate(name);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @PreAuthorize("hasAuthority('SCOPE_accounts.write')")
    public BankUserResponse createBankUser(@RequestBody CreateUserRequest request) {
        return BankUserResponse.ok(bankUserService.createBankUser(request.name(), request.password(), request.firstName(),
                request.lastName(), request.birthdate(), request.email()));
    }

    @PatchMapping("/{name}")
    @PreAuthorize("hasAuthority('SCOPE_accounts.write')")
    public BankUserResponse updateBankUser(@PathVariable String name,
                                           @RequestBody UpdateUserRequest request) {
        return BankUserResponse.ok(bankUserService.updateBankUser(name, request.firstName(), request.lastName(),
                request.birthdate(), request.email()));
    }

    @PatchMapping("/{name}/changePassword")
    @PreAuthorize("hasAuthority('SCOPE_accounts.write')")
    public GenericResponse changeBankUserPassword(@PathVariable String name,
                                                   @RequestBody ChangeUserPasswordRequest request) {
        bankUserService.changeBankUserPassword(name, request.newPassword());
        return GenericResponse.ok();
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasAuthority('SCOPE_accounts.write')")
    public GenericResponse deleteBankUser(@PathVariable String name) throws AccountServiceException {
        bankUserService.deleteBankUser(name);
        return GenericResponse.ok();
    }

    @ExceptionHandler
    public GenericResponse handleException(Throwable exception) throws Throwable {
        log.error("exception in BankUserController.", exception);
        return GenericResponse.fail(exception.getMessage());
    }
}
