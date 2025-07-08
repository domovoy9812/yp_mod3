package ru.yandex.practicum.bliushtein.mod3.ui.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.yandex.practicum.bliushtein.mod3.shared.config.ExternalConfiguration;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.BankUser;

import java.util.List;

@Slf4j
@Service
public class BankUserService implements UserDetailsService {
    private final ExternalConfiguration extConfig;
    private final RestTemplate restTemplate;
    public BankUserService(@Autowired ExternalConfiguration extConfig,
                           @Autowired RestTemplate restTemplate) {
        this.extConfig = extConfig;
        this.restTemplate = restTemplate;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        log.info("BankUserService executed for {}", username);
        BankUser user = restTemplate.getForObject("http://" + extConfig.getGatewayServiceName() + "/account-service/user/" + username, BankUser.class);
        return new User(user.getUsername(), user.getPassword(), List.of());
    }
}
