plugins {
    id("org.sonarqube") version "6.2.0.5505"
    id("org.jetbrains.kotlin.plugin.compose") version libs.versions.kotlin.get() apply false
}

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:8.11.1")
        classpath("org.jetbrains.kotlin:kotlin-gradle-plugin:${libs.versions.kotlin.get()}")
        classpath(libs.jacoco)  // jacoco dependency with version from toml
    }
    repositories {
        google()
        mavenCentral()
    }
}

allprojects {
    repositories {
        google()
        mavenCentral()
    }
}

tasks.register("kotlinLintCheck") {
    dependsOn("detekt")
    group = "verification"
}

tasks.register("clean") {
    delete(layout.buildDirectory)
}

sonar {
    properties {
        property("sonar.host.url", "http://localhost:9000")
        property("sonar.login", "1c33e69cccc01ffafc22414db270ae2f172bfd70")
        property("sonar.projectName", "BUUT Assessment Application")
        property("sonar.projectKey", "buut-assessment-app")
        property("sonar.projectVersion", "1.0.0")
        property("sonar.description", "This is the project for BUUT assessment")
        property("sonar.sourceEncoding", "UTF-8")
        property("sonar.sources", "./src/main")
        property("sonar.tests", "./src/test, src/androidTest")
    }
}
