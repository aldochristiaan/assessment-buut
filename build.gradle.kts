import org.gradle.api.attributes.java.TargetJvmEnvironment.STANDARD_JVM

plugins {
    alias(libs.plugins.android.application) apply false
    alias(libs.plugins.kotlin.android) apply false
    alias(libs.plugins.kotlin.compose) apply false
    alias(libs.plugins.sonarqube)
}

tasks.register("clean") {
    delete(layout.buildDirectory)
}

subprojects {
    afterEvaluate {
        dependencies.constraints {
            add("screenshotTestImplementation", "com.google.guava:guava:32.1.3-android") {
                attributes {
                    attribute(
                        TargetJvmEnvironment.TARGET_JVM_ENVIRONMENT_ATTRIBUTE,
                        objects.named(TargetJvmEnvironment::class.java, STANDARD_JVM)
                    )
                }
            }
        }
    }
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
