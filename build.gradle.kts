import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

plugins {
	kotlin("jvm") version "1.9.25"
	kotlin("plugin.spring") version "1.9.25"
	kotlin("plugin.jpa") version "1.9.25"
	id("org.springframework.boot") version "3.4.0"
	id("io.spring.dependency-management") version "1.1.6"
	id("org.jlleitschuh.gradle.ktlint") version "12.1.2"
}

group = "kr.flooding"
version = "0.0.1-SNAPSHOT"

java {
	toolchain {
		languageVersion = JavaLanguageVersion.of(21)
	}
}

repositories {
	mavenCentral()
}

dependencies {
	// 기본
	testImplementation("org.springframework.boot:spring-boot-starter-test")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
	testRuntimeOnly("org.junit.platform:junit-platform-launcher")

	// 검증
	implementation("org.springframework.boot:spring-boot-starter-validation")

	// 통신
	implementation("org.springframework.boot:spring-boot-starter-webflux")
	implementation("org.springframework.boot:spring-boot-starter-mail")

	// 상태 확인
	implementation("org.springframework.boot:spring-boot-starter-actuator")

	// 보안
	implementation("org.springframework.boot:spring-boot-starter-security")
	testImplementation("org.springframework.security:spring-security-test")
	compileOnly("io.jsonwebtoken:jjwt-api:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
	runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")

	// 데이터 저장
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.postgresql:postgresql")
	implementation("software.amazon.awssdk:s3:2.30.21")
	implementation("org.springframework.boot:spring-boot-starter-data-redis")

	// Swagger
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.8.5")
	implementation("org.springdoc:springdoc-openapi-starter-webmvc-api:2.8.5")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")

	// Kotlin JDSL
	implementation("com.linecorp.kotlin-jdsl:jpql-dsl:3.5.5")
	implementation("com.linecorp.kotlin-jdsl:jpql-render:3.5.5")
	implementation("com.linecorp.kotlin-jdsl:spring-data-jpa-support:3.5.5")
}

kotlin {
	compilerOptions {
		freeCompilerArgs.addAll("-Xjsr305=strict")
	}
}

allOpen {
	annotation("jakarta.persistence.Entity")
	annotation("jakarta.persistence.MappedSuperclass")
	annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.register<Jar>("buildJar") {
	from(sourceSets.main.get().output)
	from(tasks.compileJava)
	from(tasks.processResources)
	into("lib") {
		from(configurations.runtimeClasspath)
	}
	manifest {
		attributes["Main-Class"] = "kr.flooding.backend.FloodingApplication"
	}
}

ktlint {
	reporters {
		reporter(ReporterType.JSON)
	}
}
