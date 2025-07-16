package ru.yandex.practicum.bliushtein.mod3.accounts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.bliushtein.mod3.shared.config.SharedRestClientConfiguration;
import ru.yandex.practicum.bliushtein.mod3.shared.config.SharedSecurityConfiguration;

@SpringBootApplication
@Import({SharedSecurityConfiguration.class, SharedRestClientConfiguration.class})
public class AccountsApplication {

	public static void main(String[] args) {
		SpringApplication.run(AccountsApplication.class, args);
	}

}
