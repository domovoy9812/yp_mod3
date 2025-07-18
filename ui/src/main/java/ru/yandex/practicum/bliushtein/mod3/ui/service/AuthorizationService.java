package ru.yandex.practicum.bliushtein.mod3.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUserWithPassword;
import ru.yandex.practicum.bliushtein.mod3.ui.client.BankUserClient;

import java.util.List;

@Service
public class AuthorizationService implements UserDetailsService {
    private final BankUserClient bankUserClient;

    public AuthorizationService(@Autowired BankUserClient bankUserClient) {
        this.bankUserClient = bankUserClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BankUserWithPassword user = bankUserClient.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("Bank user with name = '%s' is not found".formatted(username));
        }
        return new User(user.name(), user.password(), List.of());

    }
}
