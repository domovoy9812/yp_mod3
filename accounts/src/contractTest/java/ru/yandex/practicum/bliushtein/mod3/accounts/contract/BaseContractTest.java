package ru.yandex.practicum.bliushtein.mod3.accounts.contract;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.bliushtein.mod3.sharedtest.AbstractTestWithTestcontainers;

@SpringBootTest
public class BaseContractTest extends AbstractTestWithTestcontainers {
    @Autowired
    private WebApplicationContext context;

    @BeforeEach
    public void setup() {
        // Настраиваем RestAssuredMockMvc с нужным контекстом
        io.restassured.module.mockmvc.RestAssuredMockMvc.mockMvc(
                MockMvcBuilders.webAppContextSetup(context).build()
        );
    }

}
