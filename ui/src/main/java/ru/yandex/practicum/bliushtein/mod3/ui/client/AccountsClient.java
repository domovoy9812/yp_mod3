package ru.yandex.practicum.bliushtein.mod3.ui.client;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.*;

import java.time.ZonedDateTime;

@Slf4j
@Component
public class AccountsClient {
    private final static String GET_USER_WITH_PASSWORD_URL_TEMPLATE = "http://%s/account-service/user/authenticate/{name}";
    private final static String CREATE_USER_URL_TEMPLATE = "http://%s/account-service/user";
    private final static String USER_URL_TEMPLATE = "http://%s/account-service/user/{name}";
    private final static String CHANGE_PASSWORD_URL_TEMPLATE = "http://%s/account-service/user/{name}/changePassword";
    private final static String GET_USER_ACCOUNTS_URL_TEMPLATE = "http://%s/account-service/user/{name}/account/all";

    private final ExternalConfiguration extConfig;
    private final RestClient.Builder restClientBuilder;
    private final PasswordEncoder passwordEncoder;
    public AccountsClient(@Autowired ExternalConfiguration extConfig,
                          @Autowired RestClient.Builder restClientBuilder,
                          @Autowired PasswordEncoder passwordEncoder) {
        this.extConfig = extConfig;
        this.restClientBuilder = restClientBuilder;
        this.passwordEncoder = passwordEncoder;
    }


    @CircuitBreaker(name="accountsCircuitBreaker", fallbackMethod = "accountsServiceIsUnreachableFallback")
    public BankUserWithPassword getBankUserWithPassord(String name) {
        return restClientBuilder.build().get()
                .uri(GET_USER_WITH_PASSWORD_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()), name)
                .retrieve()
                .body(BankUserWithPassword.class);
    }

    public BankUserWithPassword accountsServiceIsUnreachableFallback(Throwable throwable) {
        throw new RuntimeException("External system is unreachable", throwable);
    }

    public BankUserResponse createUser(String name, String password, String firstName, String lastName,
                                       ZonedDateTime birthdate, String email) {
        String encodedPassword = passwordEncoder.encode(password);
        return restClientBuilder.build().post()
                .uri(CREATE_USER_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()))
                .body(new CreateUserRequest(name, encodedPassword, firstName, lastName, birthdate, email))
                .retrieve()
                .body(BankUserResponse.class);
    }

    public BankUserResponse getBankUser(String name) {
        return restClientBuilder.build().get()
                .uri(USER_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()), name)
                .retrieve()
                .body(BankUserResponse.class);
    }

    public AccountsResponse getUserAccounts(String name) {
        return restClientBuilder.build().get()
                .uri(GET_USER_ACCOUNTS_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()), name)
                .retrieve()
                .body(AccountsResponse.class);

    }

    public GenericResponse changeUserPassword(String name, String newPassword) {
        String encodedPassword = passwordEncoder.encode(newPassword);
        return restClientBuilder.build().patch()
                .uri(CHANGE_PASSWORD_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()), name)
                .body(new ChangeUserPasswordRequest(encodedPassword))
                .retrieve()
                .body(GenericResponse.class);
    }

    public BankUserResponse updateBankUser(String name, String firstName, String lastName, ZonedDateTime birthdate,
                               String email) {
        return restClientBuilder.build().patch()
                .uri(USER_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()), name)
                .body(new UpdateUserRequest(firstName, lastName, birthdate, email))
                .retrieve()
                .body(BankUserResponse.class);
    }

    /*public void deleteUser(String name) {
        restClientBuilder.build().delete()
                .uri(DELETE_USER_URL_TEMPLATE.formatted(extConfig.getGatewayServiceName()), name)
                .retrieve()
                .toBodilessEntity();
    }*/

}
