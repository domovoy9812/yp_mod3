package ru.yandex.practicum.bliushtein.mod3.cash.contract;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.bliushtein.mod3.cash.client.AccountsClient;
import ru.yandex.practicum.bliushtein.mod3.cash.client.BlockerClient;
import ru.yandex.practicum.bliushtein.mod3.cash.client.NotificationClient;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.Account;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.AccountResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUser;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.BankUserResponse;

import java.time.ZonedDateTime;

import static org.mockito.Mockito.*;

@SpringBootTest
@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
public class BaseContractTest {
    @Autowired
    private WebApplicationContext context;

    @MockitoBean
    private AccountsClient accountsClientMock;
    @MockitoBean
    private BlockerClient blockerClientMock;
    @MockitoBean
    private NotificationClient notificationClientMock;

    @BeforeEach
    public void setup() {
        when(accountsClientMock.findBankUser("user1")).thenReturn(
                BankUserResponse.ok(new BankUser("user1", "first", "last", ZonedDateTime.now(), "email@dom.com"))
        );
        when(accountsClientMock.changeAccountBalance("user1", "USD", -80)).thenReturn(
                AccountResponse.ok(new Account("USD", 20))
        );
        when(blockerClientMock.validate("user1", "USD", 80, true,
                "email@dom.com")).thenReturn(GenericResponse.ok());
        io.restassured.module.mockmvc.RestAssuredMockMvc.mockMvc(
                MockMvcBuilders.webAppContextSetup(context).build()
        );
    }

}
