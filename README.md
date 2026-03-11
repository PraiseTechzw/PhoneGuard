# PhoneGuard

A production-minded Android application for used-phone verification, stolen-device reporting, and ownership management.

## Tech Stack
- **Language**: Kotlin
- **UI Framework**: Jetpack Compose
- **Architecture**: MVVM + Clean Architecture
- **Dependency Injection**: Hilt
- **Database**: Room
- **Networking**: Retrofit
- **Background Tasks**: WorkManager
- **Navigation**: Navigation Compose
- **Concurrency**: Coroutines + Flow
- **Styling**: Material 3
- **Data Persistence**: DataStore

## Project Structure
- `app`: Main Android application module.
- `core/common`: Shared utilities, base classes, and wrappers (Result, UiState).
- `core/navigation`: Navigation graph and screen definitions.
- `core/ui`: Shared UI components and theme setup.
- `feature/*`: Feature-based modules/packages (Auth, Dashboard, IMEI Check, etc.).

## Setup Instructions
1. Clone the repository.
2. Open the project in Android Studio (Ladybug or later recommended).
3. Wait for Gradle sync to complete.
4. Run the `app` module on an emulator or physical device.

## Key Features (MVP)
- **IMEI Verification**: Check if a device is reported stolen or belongs to the seller.
- **Stolen Reporting**: Report your device as stolen with proof of ownership.
- **Ownership Management**: Securely store device details and receipts.
- **Privacy First**: No cell tower tracking or unauthorized spying.
