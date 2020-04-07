import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.5.RELEASE"
	id("io.spring.dependency-management") version "1.0.9.RELEASE"
	id("org.jetbrains.kotlin.plugin.jpa") version "1.3.71"
	kotlin("jvm") version "1.3.61"
	kotlin("plugin.spring") version "1.3.61"
}

group = "br.com.bidei"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
}

buildscript {
	dependencies {
		classpath("org.jetbrains.kotlin:kotlin-noarg:1.3.71")
	}
}

apply(plugin = "org.jetbrains.kotlin.plugin.jpa")

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-data-jpa")
	implementation("org.springframework.boot:spring-boot-starter-security")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	implementation("org.apache.logging.log4j:log4j-api-kotlin:1.0.0")
	implementation("com.nimbusds:nimbus-jose-jwt:5.12")
	implementation("com.google.code.gson:gson")
	implementation("com.squareup.okhttp3:okhttp:4.4.0")
	implementation("org.liquibase:liquibase-core")
	implementation(platform("com.amazonaws:aws-java-sdk-bom:1.11.228"))
	implementation("com.amazonaws:aws-java-sdk-s3")
	implementation("com.google.firebase:firebase-admin:6.12.2")

	compileOnly("org.apache.logging.log4j:log4j-api:2.13.1")
	compileOnly("org.apache.logging.log4j:log4j-core:2.13.1")

	runtimeOnly("org.postgresql:postgresql")
	runtimeOnly("com.h2database:h2")

	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}
	testImplementation("org.springframework.security:spring-security-test")
}

tasks {

	withType<Test> {
		useJUnitPlatform()
	}

	withType<KotlinCompile> {
		kotlinOptions {
			freeCompilerArgs = listOf("-Xjsr305=strict")
			jvmTarget = "1.8"
		}
	}

	bootJar {
		archiveFileName.set("app.jar")
	}

}
