package ru.yandex.practicum.bliushtein.mod3.ui.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.yandex.practicum.bliushtein.mod3.ui.client.AccountsClient;

@Slf4j
@Controller
@RequestMapping("/main")
public class MainController {
    private final AccountsClient accountsClient;

    public MainController(@Autowired AccountsClient accountsClient) {
        this.accountsClient = accountsClient;
    }

    @GetMapping
    @PostMapping
    public String showMainPage(Authentication authentication) {
        return "The main page stub. Current User:%s. isAuthenticated=%s".formatted(authentication.getName(), authentication.isAuthenticated());
    }

    @GetMapping("/delete")
    String deleteUser(@RequestParam String name) {
        accountsClient.deleteUser(name);
        return "user deleted";
    }
}
