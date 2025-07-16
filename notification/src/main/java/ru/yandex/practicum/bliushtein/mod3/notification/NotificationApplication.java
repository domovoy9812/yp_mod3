package ru.yandex.practicum.bliushtein.mod3.notification;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Import;
import ru.yandex.practicum.bliushtein.mod3.shared.config.SharedSecurityConfiguration;

@SpringBootApplication
@Import(SharedSecurityConfiguration.class)
public class NotificationApplication {

	public static void main(String[] args) {
		SpringApplication.run(NotificationApplication.class, args);
	}

}
