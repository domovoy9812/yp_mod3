package ru.yandex.practicum.bliushtein.mod3.accounts.data;

import ru.yandex.practicum.bliushtein.mod3.accounts.data.entity.AccountEntity;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.entity.BankUserEntity;

import java.time.ZoneId;
import java.time.ZonedDateTime;

public class TestData {
    public static final String DEFAULT_CURRENCY = "RUR";
    public static final int DEFAULT_BALANCE = 0;
    public static final BankUserEntity BANK_USER_ENTITY = new BankUserEntity("name","password",
            "first", "last",
            ZonedDateTime.of(2000, 1, 1, 0, 0, 0, 0,
                    ZoneId.systemDefault()), "email@dom.com");
    //public static final AccountEntity ACCOUNT_ENTITY = new AccountEntity(BANK_USER_ENTITY, "RUR");
    public static BankUserEntity copyBankUserEntity(BankUserEntity source) {
        return new BankUserEntity(source.getId(), source.getName(), source.getPassword(), source.getFirstName(),
                source.getLastName(), source.getBirthdate(), source.getEmail());
    }

    public static AccountEntity createAccountEntity(String currency, BankUserEntity user) {
        return new AccountEntity(user, currency);
    }
}
