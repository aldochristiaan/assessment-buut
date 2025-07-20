plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.jacoco)
    alias(libs.plugins.spotbugs)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.detekt)
}

android {
    namespace = "com.abnamro.apps.referenceandroid"

    compileSdk = 36

    defaultConfig {
        applicationId = "com.abnamro.apps.referenceandroid"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "com.kaspersky.kaspresso.runner.KaspressoRunner"
    }

    buildTypes {
        debug {
            isTestCoverageEnabled = true
        }
        release {
            isMinifyEnabled = false
            proguardFiles(getDefaultProguardFile("proguard-android.txt"), "proguard-rules.pro")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.composeCompiler.get()
    }

    packaging {
        resources {
            excludes += setOf(
                "/META-INF/{AL2.0,LGPL2.1}",
                "META-INF/INDEX.LIST"
            )
        }
    }

    testOptions {
        animationsDisabled = true
        emulatorControl { enable = true }
        unitTests.isReturnDefaultValues = true
        experimentalProperties["android.experimental.enableScreenshotTest"] = true
    }

    lint {
        disable += listOf("AndroidGradlePluginVersion", "OldTargetApi")
    }

    sourceSets {
        getByName("androidTest").manifest.srcFile("src/androidTest/AndroidManifest.xml")
        maybeCreate("screenshotTest").java.srcDirs("src/screenshotTest/kotlin")
    }
}

jacoco {
    toolVersion = libs.versions.jacoco.get()
}

detekt {
    source = files("src/main/kotlin", "src/test/kotlin")
    buildUponDefaultConfig = true
    config.setFrom(file("$rootDir/config/detekt/detekt.yml"))
}

val fileFilter = listOf(
    "**/R.class",
    "**/R$*.class",
    "**/BuildConfig.*",
    "**/Manifest*.*",
    "**/*Test*.*",
    "android/**/*.*"
)

tasks.withType<Test>().configureEach {
    extensions.configure(JacocoTaskExtension::class) {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

tasks.register<JacocoReport>("jacocoTestReport") {
    dependsOn("testDebugUnitTest", "createDebugCoverageReport")

    val debugTree = fileTree("${buildDir}/intermediates/classes/debug") {
        exclude(fileFilter)
    }

    val mainSrc = "${project.projectDir}/src/main/java"

    sourceDirectories.setFrom(files(mainSrc))
    classDirectories.setFrom(files(debugTree))
    executionData.setFrom(
        fileTree(buildDir).include(
            "jacoco/testDebugUnitTest.exec",
            "outputs/code-coverage/connected/*coverage.ec"
        )
    )
}

val allureReportDirectory = "$buildDir/reports/allure-results"
val allureDownloadsDirectory = "/sdcard/Documents/allure-results"

// Task: clearAllureResult
val clearAllureResultTask = tasks.register<Exec>("clearAllureResult") {
    executable = android.adbExe.toString()
    args("shell", "rm", "-r", allureDownloadsDirectory)
}

// Task: createAllureDirectory
val createAllureDirectoryTask = tasks.register<Exec>("createAllureDirectory") {
    executable = android.adbExe.toString()
    args("shell", "mkdir", "-p", allureDownloadsDirectory)
}

// Task: fetchAllureReport
val fetchAllureReportTask = tasks.register<Exec>("fetchAllureReport") {
    group = "reporting"
    executable = android.adbExe.toString()
    args("pull", "$allureDownloadsDirectory/.", allureReportDirectory)

    finalizedBy(clearAllureResultTask)
    dependsOn(createAllureDirectoryTask)

    doFirst {
        delete(allureReportDirectory)
        File(allureReportDirectory).mkdirs()
    }
}

// Hook: Add finalizedBy to AndroidTest tasks
tasks.configureEach {
    if (name.contains("AndroidTest")) {
        finalizedBy(fetchAllureReportTask)
    }
}

dependencies {
    implementation(platform(libs.compose.bom))
    androidTestImplementation(platform(libs.compose.bom))

    implementation(libs.kotlin.stdlib)
    implementation(fileTree("libs") { include("*.jar") })
    implementation(libs.activity.compose)
    implementation(libs.appcompat)
    implementation(libs.constraintlayout)
    implementation(libs.material)

    implementation(libs.compose.ui.preview)
    debugImplementation(libs.compose.ui.tooling)
    androidTestImplementation(libs.compose.ui.test.junit4)
    debugImplementation(libs.compose.ui.test.manifest)
    implementation(libs.compose.material3)

    implementation(libs.logback)

    implementation(libs.screenshot.api)
    androidTestImplementation(libs.screenshot.plugin)
    screenshotTestImplementation(libs.screenshot.api)
    screenshotTestImplementation(libs.compose.ui.tooling)

    testImplementation(libs.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation(libs.kaspresso)
    implementation(libs.kaspresso.compose)
    implementation(libs.kaspresso.allure)
    androidTestImplementation(libs.kaspresso.compose)
    androidTestImplementation(libs.kaspresso.allure)
    androidTestImplementation(libs.espresso.device)
    androidTestUtil(libs.orchestrator)

    implementation(libs.ktor.client.android)
    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.serialization)
    implementation(libs.ktor.client.logging)
    implementation(libs.ktor.client.json)
    implementation(libs.ktor.client.negotiation)

    implementation(libs.coroutines.core)
    implementation(libs.coroutines.android)
    implementation(libs.coroutines.adapter)

    implementation(libs.koin.android)
    implementation(libs.koin.compose)

    detektPlugins(libs.detekt.twitter)
}
