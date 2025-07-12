package ru.yandex.practicum.bliushtein.mod3.accounts.data;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.entity.BankUserEntity;
import ru.yandex.practicum.bliushtein.mod3.accounts.data.repository.BankUserRepository;
import ru.yandex.practicum.bliushtein.mod3.sharedtest.AbstractTestWithTestcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.bliushtein.mod3.accounts.data.TestData.BANK_USER_ENTITY;
import static ru.yandex.practicum.bliushtein.mod3.accounts.data.TestData.copyBankUserEntity;

@DataJpaTest
public class BankUserRepositoryTest extends AbstractTestWithTestcontainers {

    @Autowired
    BankUserRepository userRepository;

    @Test
    @Transactional
    void test_findByName() {
        BankUserEntity savedUserEntity = userRepository.save(copyBankUserEntity(BANK_USER_ENTITY));
        BankUserEntity foundUserEntity = userRepository.findByName("name");
        assertEquals(savedUserEntity.getName(), foundUserEntity.getName());
    }
}
