package ru.yandex.practicum.bliushtein.mod3.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUserWithPassword;
import ru.yandex.practicum.bliushtein.mod3.ui.client.AccountsClient;

import java.util.List;

@Service
public class AuthenticationService implements UserDetailsService {
    private final AccountsClient accountsClient;

    public AuthenticationService(@Autowired AccountsClient accountsClient) {
        this.accountsClient = accountsClient;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        BankUserWithPassword user = accountsClient.getBankUserWithPassord(username);
        if (user == null) {
            throw new UsernameNotFoundException("Bank user with name = '%s' is not found".formatted(username));
        }
        return new User(user.name(), user.password(), List.of());
    }

}
