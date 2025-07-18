package ru.yandex.practicum.bliushtein.mod3.exchangegenerator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableScheduling;
import ru.yandex.practicum.bliushtein.mod3.shared.config.SharedRestClientConfiguration;

@SpringBootApplication
@Import(SharedRestClientConfiguration.class)
@EnableScheduling
public class ExchangeGeneratorApplication {

	public static void main(String[] args) {
		SpringApplication.run(ExchangeGeneratorApplication.class, args);
	}

}
