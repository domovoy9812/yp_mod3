import org.springframework.cloud.contract.verifier.plugin.ContractVerifierExtension

plugins {
	java
	id("org.springframework.boot")
	id("io.spring.dependency-management")
	id("org.springframework.cloud.contract")
	id("maven-publish")
}

group = "ru.yandex.practicum.bliushtein"
version = "0.0.1-SNAPSHOT"

configure<ContractVerifierExtension> {
	baseClassForTests.set("ru.yandex.practicum.bliushtein.mod3.accounts.contract.BaseContractTest")
}
publishing {
	publications {
		create<MavenPublication>("mavenJava") {
			from(components["java"])
			artifact(file("${layout.buildDirectory.get()}/libs/${project.name}-${version}-stubs.jar")) {
				classifier = "stubs"
			}
		}
	}
	repositories {
		mavenLocal()
	}
}

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
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("org.mapstruct:mapstruct:1.5.5.Final")
	runtimeOnly("org.postgresql:postgresql")
	compileOnly("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.mapstruct:mapstruct-processor:1.5.5.Final")
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation(project(":shared-test"))
	testImplementation("org.springframework.cloud:spring-cloud-starter-contract-verifier")
}

tasks.withType<Test> {
	useJUnitPlatform()
}
tasks.named("publishMavenJavaPublicationToMavenLocal") {
	dependsOn("verifierStubsJar")
}
tasks.named("build") {
	dependsOn("publishToMavenLocal")
}
