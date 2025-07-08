package ru.yandex.practicum.bliushtein.mod3.accounts.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.Account;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.BankUser;

import java.util.List;

@Slf4j
@Service
public class AccountService {
    public BankUser findBankUser(String name) {
        log.info("AccountService called for {}", name);
        return new BankUser(name, "password", List.of(new Account("USD", 100), new Account("RUR", 100)));
    }
}
