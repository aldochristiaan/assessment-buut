

# 📱 BUUT Assessment Application

This application built with **Jetpack Compose**, **Ktor**, and robust tooling for quality, testing, and CI. This app is designed with modern development practices using **Java 21**, **Kotlin DSL**, **Compose UI testing**, **Kaspresso**, **Allure reports**, **SonarQube**, and **GitHub Actions** for automation.


---

## 🧩 Project Highlights

- 🧱 **Jetpack Compose** – Declarative UI development.
- 🔌 **Ktor Client** – Asynchronous REST API calls.
- 🧪 **Kaspresso** – Stable and readable UI tests with Compose support.
- 📸 **Screenshot Testing** – Prevent visual regressions.
- 🧱 **Layout Assertions** – Ensure consistent UI structure.
- 📊 **Allure Reporting** – Beautiful test result visualization.
- ☕️ Built with **Java 21** and Kotlin DSL (`build.gradle.kts`) support.
- 🤖 **CI with GitHub Actions** – Emulator tests, validation, and reporting

---

## ✅ Prerequisites

### 1. Java 21

Ensure **Java 21 (JDK 21)** is installed and properly configured.

- 📥 [Download JDK 21](https://jdk.java.net/21/)
- Set `JAVA_HOME` to JDK 21:

**macOS/Linux:**
```bash
export JAVA_HOME=/path/to/jdk-21
export PATH=$JAVA_HOME/bin:$PATH
java -version
# java version "21"
```

### 2. Android Studio (Latest Stable Version)
Make sure you have the latest stable version of Android Studio installed.
- 📦 Download: https://developer.android.com/studio
- Minimum required version: Android Studio Iguana | 2023.2.1 or newer.
- Android Gradle Plugin: AGP 8.3.0+ is recommended for Java 21 support.

### 3. Android SDK & Build Tools
- Compile SDK: 34 or newer
- Build Tools Version: 34.0.0 or newer
- Kotlin Version: 1.9.0+

You can install these using the SDK Manager in Android Studio.

### 4. 🚀 Tech Stack

| **Category**           | **Technology**                     |
|------------------------|------------------------------------|
| UI Framework           | Jetpack Compose                    |
| Networking             | Ktor Client                        |
| Dependency Injection   | Koin            |
| UI Testing             | Kaspresso + Compose Test APIs      |
| Screenshot Testing     | Compose ScreenshotTest             |
| Reporting              | Allure Android                     |
| Build System           | Gradle (Kotlin DSL)                |
| Language               | Kotlin + Java 21                   |

### 5. 📊 Allure Test Reporting

All UI and integration tests generate Allure-compatible results.

---

#### 🔧 Allure CLI Installation (macOS with Homebrew)

To view the test reports locally, install the **Allure CLI**:

```bash
brew install allure
allure --version
```

Once installed, you can generate and view the report using:

```bash
# Run UI tests 
./gradlew connectedDebugAndroidTest
```

Allure results will be automatically fetched from the emulator by gradle task

```
# Generate single allure report file
allure generate -c --single-file app/build/reports/allure-results -o allure-report --clean
```

### 6. 🧼 Code Quality – SonarQube (Local Setup)

This project supports **static code analysis** via **SonarQube**.

You can run a **local SonarQube server** using Docker and analyze the project using the Sonar CLI.

---

###  ▶️ Start SonarQube Locally via Docker

1. Create a `docker-compose.yml` file with the following content:

    ```yaml
    version: "1"
    services:
      sonarqube:
        image: "sonarqube:latest"
        ports:
          - "9000:9000"
        environment:
          - SONAR_ES_BOOTSTRAP_CHECKS_DISABLE=true
    ```

2. Start the container:

    ```bash
    docker-compose up
    ```

3. Once running, open [http://localhost:9000](http://localhost:9000) in your browser.

---

#### 🔐 Authentication

- On first launch, SonarQube uses default credentials:
    - **Username:** `admin`
    - **Password:** `admin`
- After login, **create a new project manually** in the SonarQube UI.
- Generate a **project token**.

---

#### ⚙️ Configure `sonar-project.properties`

Create a `sonar-project.properties` file at the root of your project and update the following values:

```properties
sonar.projectKey=your_project_key
sonar.projectName=Your Project Name
sonar.host.url=http://localhost:9000
sonar.login=your_generated_token
```

Replace your_project_key and your_generated_token with your own values from the SonarQube dashboard.

#### 🚀 Run Sonar Analysis
With SonarQube running and sonar-project.properties configured, run:

```bash
./gradlew sonar
```

![Sonar local setup](sonar-local.png)

### 7. ⚙️ CI/CD with GitHub Actions
This project includes two main workflows:

#### 🧪 Run UI Tests (Manual)
Run emulator tests via a manual dispatch action:

- Inputs: API level, device type, system image, CPU arch
- Executes: connectedCheck
- Generates: Allure report
- Uploads: Report as artifact

Example : [Run UI Test Workflow](https://github.com/aldochristiaan/assessment-buut/actions/runs/16400657621)


#### 🔍 PR Checks (Automatic)
Runs on all PRs to main:

- Detekt (lint)
- Unit tests + Jacoco report
- Screenshot test validation
- Uploads all test artifacts

Examoke : [PR validation](https://github.com/aldochristiaan/assessment-buut/actions/runs/16405543353)
