plugins {
    id("org.jetbrains.kotlin.jvm") version "1.6.10"
    id("org.jetbrains.kotlin.kapt") version "1.6.10"
    id("org.jetbrains.kotlin.plugin.allopen") version "1.6.10"
    id("com.github.johnrengelman.shadow") version "7.1.1"
    id("io.micronaut.application") version "3.1.1"
    id("com.google.cloud.tools.jib") version "2.8.0"
    id("com.ryandens.javaagent-application") version "0.2.1"
}

version = "0.1"
group = "com.shopback"

val kotlinVersion = project.properties["kotlinVersion"]

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
    implementation("io.micronaut.reactor:micronaut-reactor-http-client")
    implementation("io.micronaut:micronaut-management")
    implementation("io.micronaut:micronaut-runtime")

    implementation("io.micronaut.data:micronaut-data-jdbc")
    implementation("io.micronaut.flyway:micronaut-flyway")
    runtimeOnly("io.micronaut.sql:micronaut-jdbc-hikari")




    implementation("io.micronaut.micrometer:micronaut-micrometer-registry-prometheus")

    implementation("io.swagger.core.v3:swagger-annotations")

    implementation("jakarta.annotation:jakarta.annotation-api")
    implementation("io.micronaut:micronaut-validation")

    implementation("io.micronaut.kotlin:micronaut-kotlin-extension-functions")
    implementation("io.micronaut.kotlin:micronaut-kotlin-runtime")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("org.jetbrains.kotlin:kotlin-reflect:${kotlinVersion}")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:${kotlinVersion}")


    implementation("io.micronaut.sql:micronaut-jdbc-hikari")
    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("ch.qos.logback:logback-classic")
    runtimeOnly("io.micronaut.reactor:micronaut-reactor")
    implementation("io.micronaut.data:micronaut-data-r2dbc")
    runtimeOnly("io.r2dbc:r2dbc-postgresql")
    //runtimeOnly("mysql:mysql-connector-java")
    //runtimeOnly("dev.miku:r2dbc-mysql")

    runtimeOnly("com.fasterxml.jackson.module:jackson-module-kotlin")
    compileOnly("org.graalvm.nativeimage:svm")

    implementation("io.micronaut.micrometer:micronaut-micrometer-registry-datadog")
    implementation("io.micronaut.micrometer:micronaut-micrometer-core")
    javaagent("com.datadoghq:dd-java-agent:0.93.0")

    //javaagent("co.elastic.apm:elastic-apm-agent:1.28.4")

    //tests
    testImplementation("org.testcontainers:junit-jupiter")
    testImplementation("org.testcontainers:mongodb")
    testImplementation("org.testcontainers:mysql")
    testImplementation("org.testcontainers:r2dbc")
    testImplementation("org.testcontainers:testcontainers")
}


application {
    applicationDefaultJvmArgs = listOf(
        "-Ddd.profiling.enabled=true",
        "-Ddd.logs.injection=true",
        "-Ddd.trace.sample.rate=1",
        "-Ddd.service=$name",
        "-Ddd.env=local",
        "-Ddd.version=$version",
        "-Ddd.service=$name",
        //"-Delastic.apm.service_name=$name"
    )

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
