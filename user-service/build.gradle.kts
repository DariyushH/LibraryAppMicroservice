plugins {
    java
    id("org.springframework.boot") version "3.3.5"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "com.book"
version = "0.0.1-SNAPSHOT"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

extra["springCloudVersion"] = "2023.0.3"

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-aop:3.3.4")
    implementation("org.springframework.boot:spring-boot-starter-data-rest")
    implementation("org.springframework.boot:spring-boot-starter-web")

    implementation("org.springframework.cloud:spring-cloud-starter")
    implementation("org.springframework.cloud:spring-cloud-starter-config")
    implementation("org.springframework.cloud:spring-cloud-starter-consul-discovery")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    compileOnly("org.projectlombok:lombok:1.18.28")
    annotationProcessor ("org.projectlombok:lombok:1.18.28")
    implementation("jakarta.transaction:jakarta.transaction-api:2.0.1")

    implementation("org.springframework.boot:spring-boot-starter-data-jpa:3.3.4")
    implementation("org.springframework.boot:spring-boot-starter-jdbc:3.3.4")
    runtimeOnly("org.postgresql:postgresql")
    implementation ("org.hibernate:hibernate-jcache:6.2.9.Final")
    //implementation("org.liquibase:liquibase-core")

    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("io.jsonwebtoken:jjwt-api:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-jackson:0.12.6")
    runtimeOnly("io.jsonwebtoken:jjwt-impl:0.12.6")
    implementation ("org.springframework.boot:spring-boot-starter-validation")
//    implementation ("org.hibernate:hibernate-jcache:6.2.9.Final")
    implementation("org.springframework.boot:spring-boot-starter-actuator:3.3.5")

}

dependencyManagement {
    imports {
        mavenBom("org.springframework.cloud:spring-cloud-dependencies:${property("springCloudVersion")}")
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
