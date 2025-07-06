plugins {
	id("org.springframework.boot") version "3.4.4" apply false
	id("io.spring.dependency-management") version "1.1.7" apply false
}

extra["springCloudVersion"] = "2024.0.1"

group = "ru.yandex.practicum.bliushtein"
version = "0.0.1-SNAPSHOT"

repositories {
	mavenCentral()
}
