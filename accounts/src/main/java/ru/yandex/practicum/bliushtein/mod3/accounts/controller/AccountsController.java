package ru.yandex.practicum.bliushtein.mod3.accounts.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.yandex.practicum.bliushtein.mod3.accounts.service.AccountService;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.BankUser;

@RestController
@RequestMapping
public class AccountsController {
    private final AccountService accountService;

    public AccountsController(@Autowired AccountService accountService) {
        this.accountService = accountService;
    }

    @GetMapping
    public String sayHello() {
        return "test message from AccountsController";
    }

    @GetMapping("/user/{name}")
    public BankUser findBankUserByName(@PathVariable String name) {
        return accountService.findBankUser(name);
    }
}
