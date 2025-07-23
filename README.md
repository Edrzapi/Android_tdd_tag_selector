# Android TDD Tag Selector

A minimal starter app built with Kotlin, Jetpack Compose & MVVM—designed for your Day 1 introduction to Test‑Driven Development on Android. Follow along to learn how to drive your features with failing tests, mock your dependencies, and keep your UI layer decoupled from your analytics logic.

## Purpose
This repo provides a simple “tag selector” screen and a batch‑analytics manager. You’ll practice:  
- Writing your first Red → Green → Refactor cycle  
- Mocking dependencies (ViewModel → Selector, AnalyticsManager → Logger)  
- Structuring your code for easy unit testing  
- Verifying interaction order with `inOrder` or `verifySequence`

## Table of Contents
- [Prerequisites](#prerequisites)  
- [Setup Environment](#setup-environment)  
- [Getting Started](#getting-started)  
- [Building and Running from the Command Line](#building-and-running-from-the-command-line)  
- [Running Tests](#running-tests)  
- [Resources](#resources)  

---

## Prerequisites
- **Kotlin** 1.8+  
- **Android Studio** Giraffe (2022.3.1) or newer  
- **Android SDK** (API 21+)  
- **Gradle** 7.5+ (uses the Gradle wrapper)  

---

## Setup Environment
1. **Clone the repo**  
   ```bash
   git clone https://github.com/Edrzapi/Android_tdd_tag_selector.git
   cd Android_tdd_tag_selector
   git checkout QLC-3-ANS
   ```
2. **Open in Android Studio**  
   - File → Open... → select the project folder  
   - Let Android Studio sync Gradle and download dependencies  

---

## Getting Started
- Explore the **`viewmodels/`** package for the Compose ViewModel and `TagSelector` interface  
- Jump into **`analytics/`** for the new `MoodLogger` and `MoodAnalyticsManager`  
- Open the tests in **`test/.../analytics/`** to see the Red → Green → Refactor cycle in action  

---

## Building and Running from the Command Line
```bash
# Assemble the debug APK
./gradlew assembleDebug

# Install on a connected device/emulator
./gradlew installDebug

# Launch the main activity (if you have adb in your PATH)
adb shell am start -n uk.co.devfoundry.moodselector/.MainActivity
```

---

## Running Tests
- **Unit tests only**  
  ```bash
  ./gradlew test
  ```
- **Instrumentation (UI) tests**  
  ```bash
  ./gradlew connectedAndroidTest
  ```

---

## Resources
- [Android Testing Documentation](https://developer.android.com/training/testing/)  
- [Jetpack Compose Testing](https://developer.android.com/jetpack/compose/testing)  
- [Mockito‑Kotlin Guide](https://github.com/mockito/mockito-kotlin)  
- [MockK Documentation](https://mockk.io/)  

---

Happy coding, and may your tests always start red! 
