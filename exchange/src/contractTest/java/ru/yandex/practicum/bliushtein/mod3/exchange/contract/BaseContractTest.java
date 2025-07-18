package ru.yandex.practicum.bliushtein.mod3.exchange.contract;

import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.bliushtein.mod3.exchange.service.ExchangeService;

import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
public class BaseContractTest {
    @Autowired
    private WebApplicationContext context;

    @Autowired
    ExchangeService exchangeService;

    @BeforeEach
    public void setup() {
        exchangeService.updateExchangeRates(Map.of("USD", 0.0125f));
        io.restassured.module.mockmvc.RestAssuredMockMvc.mockMvc(
                MockMvcBuilders.webAppContextSetup(context).build()
        );
    }

}
