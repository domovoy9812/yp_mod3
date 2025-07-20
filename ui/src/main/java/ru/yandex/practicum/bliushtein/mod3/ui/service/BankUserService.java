package ru.yandex.practicum.bliushtein.mod3.ui.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.Account;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUser;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUserResponse;
import ru.yandex.practicum.bliushtein.mod3.ui.client.AccountsClient;

import java.time.ZonedDateTime;
import java.util.List;

@Service
public class BankUserService {
    private final AccountsClient accountsClient;

    public BankUserService(@Autowired AccountsClient accountsClient) {
        this.accountsClient = accountsClient;
    }

    public BankUserResponse createUser(String name, String password, String firstName, String lastName,
                                       ZonedDateTime birthdate, String email) {
        return accountsClient.createUser(name, password, firstName, lastName, birthdate, email);
    }

    public BankUser getUser(String name) {
        return accountsClient.getBankUser(name).getBankUser();
    }

    public List<Account> getUserAccounts(String name) {
        return accountsClient.getUserAccounts(name).getAccounts();
    }

    public void changePassword(String user, String newPassword, List<String> errors) {
        GenericResponse response = accountsClient.changeUserPassword(user, newPassword);
        if (!response.isSuccessful()) {
            errors.add(response.getErrorMessage());
        }
    }

    public BankUser updateBankUser(String name, String firstName, String lastName, ZonedDateTime birthdate,
                                   String email, List<String> errors) {
        BankUserResponse response = accountsClient.updateBankUser(name, firstName, lastName, birthdate, email);
        if (!response.isSuccessful()) {
            errors.add(response.getErrorMessage());
        }
        return response.getBankUser();
    }
}
