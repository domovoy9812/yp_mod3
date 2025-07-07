package ru.yandex.practicum.bliushtein.mod3.ui.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;

@RestController
@RequestMapping("/main")
public class MainController {
    private final ExternalConfiguration extConfig;
    private final RestTemplate restTemplate;

    @Value("${config_value_from_consul:no_value}")
    private String consulValue;

    public MainController(@Autowired ExternalConfiguration extConfig,
                          @Autowired RestTemplate restTemplate) {
        this.extConfig = extConfig;
        this.restTemplate = restTemplate;
    }

    @GetMapping
    public String showMainPage() {
        String result = restTemplate.getForObject("http://" + extConfig.getAccountsServiceName() + "/accounts", String.class);
        return "value returned from ui" + result;
    }

    @GetMapping("/value")
    public String readConfigValue() {
        return consulValue;
    }
}
