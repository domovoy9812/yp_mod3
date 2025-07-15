import org.springframework.cloud.contract.verifier.plugin.ContractVerifierExtension

plugins {
	java
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	id("org.springframework.cloud.contract")
	id("maven-publish")
}
configure<ContractVerifierExtension> {
	baseClassForTests.set("ru.yandex.practicum.bliushtein.mod3.notification.contract.BaseContractTest")
}

publishing {
	repositories {
		mavenLocal()
	}
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
	implementation("org.springframework.boot:spring-boot-starter-actuator")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	//implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
	//implementation("org.springframework.cloud:spring-cloud-starter-consul-config")
	//implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	runtimeOnly("org.postgresql:postgresql")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(project(":shared-test"))
	testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
}
configurations.implementation {
	exclude(group = "org.springframework.boot", module = "spring-boot-starter-oauth2-client")
}
tasks.withType<Test> {
	useJUnitPlatform()
}
