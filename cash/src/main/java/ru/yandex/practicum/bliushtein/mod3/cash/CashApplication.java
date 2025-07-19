package ru.yandex.practicum.bliushtein.mod3.cash;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.bliushtein.mod3.shared.config.SharedRestClientConfiguration;
import ru.yandex.practicum.bliushtein.mod3.shared.config.SharedSecurityConfiguration;

@SpringBootApplication
@Import({SharedSecurityConfiguration.class, SharedRestClientConfiguration.class})
public class CashApplication {
	public static void main(String[] args) {
		SpringApplication.run(CashApplication.class, args);
	}

}
