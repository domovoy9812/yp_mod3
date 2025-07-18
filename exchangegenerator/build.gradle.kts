
plugins {
	java
	id("org.springframework.boot")
	id("io.spring.dependency-management")
}

group = "ru.yandex.practicum.bliushtein"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

dependencyManagement {
	imports {
		mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
	}
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenLocal()
	mavenCentral()
}

dependencies {
	implementation(project(":shared"))
	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	//implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("io.github.resilience4j:resilience4j-spring-boot3")
	implementation("org.springframework.boot:spring-boot-starter-aop")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(project(":shared-test"))
}
configurations.implementation {
	exclude(group = "org.springframework.boot", module = "spring-boot-starter-oauth2-resource-server")
}
tasks.withType<Test> {
	useJUnitPlatform()
}
