import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import com.google.protobuf.gradle.*

val protobufVersion = "3.17.2"
val krotoPlusVersion = "0.6.1"

plugins {
    id("org.springframework.boot") version "2.5.3"
    id("io.spring.dependency-management") version "1.0.11.RELEASE"
    id("com.google.protobuf") version "0.8.17"
    idea
    java
    id("io.github.lognet.grpc-spring-boot") version "4.5.5"
    kotlin("jvm") version "1.5.21"
    kotlin("plugin.spring") version "1.5.21"
    kotlin("plugin.jpa") version "1.5.21"
}

group = "com.learning"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
    mavenCentral()
}

protobuf {
    protoc { artifact = "com.google.protobuf:protoc:$$protobufVersion" }

    plugins {
        id("coroutines") {
            artifact = "com.github.marcoferrer.krotoplus:protoc-gen-grpc-coroutines:$krotoPlusVersion"
        }
        id("kroto"){
            artifact = "com.github.marcoferrer.krotoplus:protoc-gen-kroto-plus:$krotoPlusVersion"
        }
    }

    generateProtoTasks {
        val krotoConfig = file("krotoPlusConfig.yml")

        all().forEach { task ->
            task.inputs.files(krotoConfig)

            task.plugins {
                id("coroutines")
                id("kroto") {
                    outputSubDir = "java"
                    option("ConfigPath=${krotoConfig.absolutePath.replace(System.getProperty("user.dir"), "").drop(1)}")
                }
            }
        }
    }
}


dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("com.h2database:h2")

    //Kotlin
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.1")

    //gRPC
    implementation("net.devh:grpc-spring-boot-starter:2.12.0.RELEASE")

    //Kroto+
    implementation("com.github.marcoferrer.krotoplus:kroto-plus-coroutines:$krotoPlusVersion")
    implementation("com.github.marcoferrer.krotoplus:kroto-plus-message:$krotoPlusVersion")

    annotationProcessor("org.projectlombok:lombok")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "11"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}