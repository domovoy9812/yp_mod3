package ru.yandex.practicum.bliushtein.mod3.blocker.data;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bliushtein.mod3.sharedtest.AbstractTestWithTestcontainers;

@DataJpaTest
public class BlockerRepositoryTest extends AbstractTestWithTestcontainers {

    @Test
    @Transactional
    void test() {
    }
}
