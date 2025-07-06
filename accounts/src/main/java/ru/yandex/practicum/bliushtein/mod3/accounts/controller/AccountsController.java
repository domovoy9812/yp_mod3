package ru.yandex.practicum.bliushtein.mod3.accounts.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/accounts")
public class AccountsController {

    @GetMapping
    public String sayHello() {
        return "test message from AccountsController";
    }
}
