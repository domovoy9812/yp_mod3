package ru.yandex.practicum.bliushtein.mod3.accounts.data;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.entity.AccountEntity;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.entity.BankUserEntity;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.repository.AccountRepository;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.repository.BankUserRepository;
import ru.yandex.practicum.bliushtein.mod3.sharedtest.AbstractTestWithTestcontainers;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static ru.yandex.practicum.bliushtein.mod3.accounts.data.TestData.*;
@DataJpaTest
public class AccountRepositoryTest extends AbstractTestWithTestcontainers {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    BankUserRepository userRepository;

    BankUserEntity user;

    @BeforeEach
    void setUp() {
        user = userRepository.save(copyBankUserEntity(BANK_USER_ENTITY));
    }

    @Test
    @Transactional
    void test() {
        AccountEntity account = accountRepository.save(createAccountEntity(DEFAULT_CURRENCY, user));
        List<AccountEntity> foundAccounts = accountRepository.findAllByUser(user);
        assertEquals(1, foundAccounts.size());
        assertEquals(account.getId(), foundAccounts.getFirst().getId());
        assertEquals(DEFAULT_CURRENCY, foundAccounts.getFirst().getCurrency());
        assertEquals(DEFAULT_BALANCE, foundAccounts.getFirst().getBalance());
        assertEquals(user.getId(), foundAccounts.getFirst().getUser().getId());
        AccountEntity foundAccount = accountRepository.findByUserIdAndCurrency(user.getId(), DEFAULT_CURRENCY);
        assertEquals(account.getId(), foundAccount.getId());
    }

    @Test
    @Transactional
    void test_onlyOneAccountForSameCurrency() {
        accountRepository.save(createAccountEntity(DEFAULT_CURRENCY, user));
        assertThrows(DataIntegrityViolationException.class,
                () -> accountRepository.save(createAccountEntity(DEFAULT_CURRENCY, user)));
    }
}
