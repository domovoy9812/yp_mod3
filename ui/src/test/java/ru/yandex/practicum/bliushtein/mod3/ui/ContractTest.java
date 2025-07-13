package ru.yandex.practicum.bliushtein.mod3.ui;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import(TestSecurityConfiguration.class)
@ActiveProfiles("test")
@AutoConfigureStubRunner(
        ids = {"ru.yandex.practicum.bliushtein:accounts:+:stubs:8082"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL
)

public class ContractTest {
    @Autowired
    TestRestTemplate restTemplate;

    @LocalServerPort
    int port;

    @Test
    @WithMockUser(username = "user1")
    void test() {
        URI uri = UriComponentsBuilder.fromHttpUrl("http://localhost:" + port + "/main/create")
                .queryParam("name", "{name}")
                .queryParam("password", "{password}")
                .queryParam("firstName", "{firstName}")
                .queryParam("lastName", "{lastName}")
                .queryParam("email", "{email}")
                .build("user1", "12345", "first name", "last name", "test@dom.com");
        String response = restTemplate.getForObject(uri, String.class);
        assertEquals("BankUser[name=user1, firstName=first name, lastName=last name, email=test@dom.com]", response);
    }
}
