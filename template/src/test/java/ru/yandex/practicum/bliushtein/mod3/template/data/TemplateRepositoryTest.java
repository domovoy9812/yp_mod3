package ru.yandex.practicum.bliushtein.mod3.template.data;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;
import ru.yandex.practicum.bliushtein.mod3.sharedtest.AbstractTestWithTestcontainers;

import static ru.yandex.practicum.bliushtein.mod3.template.data.TestData.*;
@DataJpaTest
public class TemplateRepositoryTest extends AbstractTestWithTestcontainers {

    @Test
    @Transactional
    void test() {
    }
}
