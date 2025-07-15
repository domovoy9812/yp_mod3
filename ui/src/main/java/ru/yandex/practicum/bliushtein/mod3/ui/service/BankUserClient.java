package ru.yandex.practicum.bliushtein.mod3.ui.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUser;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUserWithPassword;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.CreateUserRequest;

@Slf4j
@Component
public class BankUserClient {
    private final static String GET_USER_URL_TEMPLATE = "http://%s/account-service/user/authenticate/{name}";
    private final static String USER_URL_TEMPLATE = "http://%s/account-service/user";
    private final static String DELETE_USER_URL_TEMPLATE = "http://%s/account-service/user/{name}";

    private final ExternalConfiguration extConfig;
    private final RestClient.Builder restClientBuilder;
    private final PasswordEncoder passwordEncoder;
    public BankUserClient(@Autowired ExternalConfiguration extConfig,
                          @Autowired RestClient.Builder restClientBuilder,
                          @Autowired PasswordEncoder passwordEncoder) {
        this.extConfig = extConfig;
        this.restClientBuilder = restClientBuilder;
        this.passwordEncoder = passwordEncoder;
    }

    public BankUserWithPassword findUserByUsername(String name) {
        log.info("BankUserService executed for {}", name);
        return restClientBuilder.build().get()
                .uri(GET_USER_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()), name)
                .retrieve()
                .body(BankUserWithPassword.class);
        /*return restTemplate.getForObject(GET_USER_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()),
                BankUserWithPassword.class, name);*/
    }

    public BankUser createUser(String name, String password, String firstName, String lastName, String email) {
        String encodedPassword = passwordEncoder.encode(password);
        return restClientBuilder.build().post()
                .uri(USER_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()))
                .body(new CreateUserRequest(name, encodedPassword, firstName, lastName, email))
                .retrieve()
                .body(BankUser.class);
        /*return restTemplate.postForObject(USER_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()),
                , BankUser.class);*/
    }

    public void deleteUser(String name) {
        restClientBuilder.build().delete()
                .uri(DELETE_USER_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()), name)
                .retrieve()
                .toBodilessEntity();
        /*restTemplate.delete(DELETE_USER_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()), name);*/
    }
}
