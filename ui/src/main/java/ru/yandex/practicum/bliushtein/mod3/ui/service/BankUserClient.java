package ru.yandex.practicum.bliushtein.mod3.ui.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.BankUser;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.BankUserWithPassword;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.CreateUserRequest;

@Slf4j
@Component
public class BankUserClient {
    private final static String GET_USER_URL_TEMPLATE = "http://%s/account-service/user/authenticate/{name}";
    private final static String USER_URL_TEMPLATE = "http://%s/account-service/user";
    private final static String DELETE_USER_URL_TEMPLATE = "http://%s/account-service/user/{name}";

    private final ExternalConfiguration extConfig;
    private final RestTemplate restTemplate;
    private final PasswordEncoder passwordEncoder;
    public BankUserClient(@Autowired ExternalConfiguration extConfig,
                          @Autowired RestTemplate restTemplate,
                          @Autowired PasswordEncoder passwordEncoder) {
        this.extConfig = extConfig;
        this.restTemplate = restTemplate;
        this.passwordEncoder = passwordEncoder;
    }

    public BankUserWithPassword findUserByUsername(String name) {
        log.info("BankUserService executed for {}", name);
        return restTemplate.getForObject(GET_USER_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()),
                BankUserWithPassword.class, name);
    }

    public BankUser createUser(String name, String password, String firstName, String lastName, String email) {
        String encodedPassword = passwordEncoder.encode(password);
        return restTemplate.postForObject(USER_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()),
                new CreateUserRequest(name, encodedPassword, firstName, lastName, email), BankUser.class);
    }

    public void deleteUser(String name) {
        restTemplate.delete(DELETE_USER_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()), name);
    }
}
