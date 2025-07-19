package ru.yandex.practicum.bliushtein.mod3.transfer.contract;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.GenericResponse;
import ru.yandex.practicum.bliushtein.mod3.shared.dto.accounts.*;
import ru.yandex.practicum.bliushtein.mod3.transfer.client.AccountsClient;
import ru.yandex.practicum.bliushtein.mod3.transfer.client.BlockerClient;
import ru.yandex.practicum.bliushtein.mod3.transfer.client.NotificationClient;

import java.time.ZonedDateTime;

import static org.mockito.Mockito.when;

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
                BankUserResponse.ok(new BankUser("user1", "first1", "last1",
                        ZonedDateTime.now(), "email1@dom.com"))
        );
        when(accountsClientMock.findBankUser("user2")).thenReturn(
                BankUserResponse.ok(new BankUser("user2", "first2", "last2",
                        ZonedDateTime.now(), "email2@dom.com"))
        );
        when(accountsClientMock.transfer("user1", "RUR",
                new AccountsTransferRequest(50, "user2", "RUR", 50))).thenReturn(
                AccountsTransferResponse.ok(
                        new Account("RUR", 50),
                        new Account("RUR", 50)
                )
        );
        when(blockerClientMock.validate("user1", "user2", 50, "email1@dom.com"))
                .thenReturn(GenericResponse.ok());

        io.restassured.module.mockmvc.RestAssuredMockMvc.mockMvc(
                MockMvcBuilders.webAppContextSetup(context).build()
        );
    }

}
