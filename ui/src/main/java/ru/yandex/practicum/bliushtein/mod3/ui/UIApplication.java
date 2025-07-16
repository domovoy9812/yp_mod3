package ru.yandex.practicum.bliushtein.mod3.ui;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.bliushtein.mod3.shared.config.SharedRestClientConfiguration;

@SpringBootApplication
@Import(SharedRestClientConfiguration.class)
public class UIApplication {

	public static void main(String[] args) {
		SpringApplication.run(UIApplication.class, args);
	}

}
