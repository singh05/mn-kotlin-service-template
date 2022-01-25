plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    id("org.jetbrains.kotlin.kapt") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.1"
    id("io.micronaut.application") version "3.1.1"
    id("com.google.cloud.tools.jib") version "2.8.0"
}

version = "0.1"
group = "com.shopback"

val kotlinVersion=project.properties.get("kotlinVersion")
repositories {
    mavenCentral()
}

micronaut {
    runtime("netty")
    testRuntime("junit5")
    processing {
        incremental(true)
        annotations("com.shopback.*")
    }
}

dependencies {
    kapt("io.micronaut:micronaut-http-validation")
    kapt("io.micronaut.data:micronaut-data-processor")
    kapt("io.micronaut.openapi:micronaut-openapi")
    implementation("io.micronaut:micronaut-http-client")
    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut:micronaut-runtime")
    implementation("io.micronaut:micronaut-tracing")

    implementation("org.postgresql:postgresql:42.3.1")
    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut.flyway:micronaut-flyway")
    runtimeOnly("io.micronaut.sql:micronaut-jdbc-hikari")

    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")

    implementation("io.micronaut.micrometer:micronaut-micrometer-core")

//  implementation("io.micronaut.mongodb:micronaut-mongo-reactive")
//  implementation("io.micronaut.micrometer:micronaut-micrometer-registry-datadog")

    implementation("io.micronaut.micrometer:micronaut-micrometer-registry-prometheus")

    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    implementation("io.swagger.core.v3:swagger-annotations")

    implementation("jakarta.annotation:jakarta.annotation-api")

    implementation("io.micronaut:micronaut-validation")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")
    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")

    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("dev.miku:r2dbc-mysql")
    runtimeOnly("io.jaegertracing:jaeger-thrift")
    runtimeOnly("mysql:mysql-connector-java")

    compileOnly("org.graalvm.nativeimage:svm")

    //tests
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mongodb")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:r2dbc")
    testImplementation("org.testcontainers:testcontainers")
}


application {
    mainClass.set("com.shopback.ApplicationKt")
}
java {
    sourceCompatibility = JavaVersion.toVersion("11")
}

tasks {
    compileKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    compileTestKotlin {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
    dockerBuild {
        images.add("${System.getenv("DOCKER_IMAGE") ?: project.name}:${project.version}")
    }

    dockerBuildNative {
        images.add("${System.getenv("DOCKER_IMAGE") ?: project.name}:${project.version}-native")
    }
    jib {
        to {
            image = "gcr.io/myapp/jib-image"
        }
    }
}
graalvmNative.toolchainDetection.set(false)
