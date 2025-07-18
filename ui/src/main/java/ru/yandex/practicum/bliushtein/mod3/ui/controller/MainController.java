package ru.yandex.practicum.bliushtein.mod3.ui.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bliushtein.mod3.ui.client.BankUserClient;

@Slf4j
@RestController
@RequestMapping("/main")
public class MainController {
    private final BankUserClient bankUserClient;

    public MainController(@Autowired BankUserClient bankUserClient) {
        this.bankUserClient = bankUserClient;
    }

    @GetMapping
    @PostMapping
    public String showMainPage(Authentication authentication) {
        return "The main page stub. Current User:%s. isAuthenticated=%s".formatted(authentication.getName(), authentication.isAuthenticated());
    }

    @GetMapping("/create")
    public String createUser(@RequestParam String name,
                             @RequestParam String password,
                             @RequestParam String firstName,
                             @RequestParam String lastName,
                             @RequestParam String email) {
        log.debug("calling bankUserClient.createUser with name='{}', password='{}', firstName='{}', lastName='{}', email='{}'",
                name, password, firstName, lastName, email);
        return bankUserClient.createUser(name, password, firstName, lastName, email).toString();
    }

    @GetMapping("/delete")
    String deleteUser(@RequestParam String name) {
        bankUserClient.deleteUser(name);
        return "user deleted";
    }
}
