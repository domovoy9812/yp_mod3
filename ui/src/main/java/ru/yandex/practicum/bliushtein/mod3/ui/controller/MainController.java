package ru.yandex.practicum.bliushtein.mod3.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/main")
public class MainController {
    private final RestTemplate restTemplate;

    public MainController(@Autowired RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String showMainPage() {
        String result = restTemplate.getForObject("http://accounts-application/accounts", String.class);
        return "value returned from ui" + result;
    }
}
