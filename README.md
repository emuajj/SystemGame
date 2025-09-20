# 🧠 System Game – Process Planning Simulator

**System Game** is a cross-platform educational application that simulates the operation of process planning algorithms inside a CPU. It is aimed at computer engineering students and developers who want to better understand process management through a visual, interactive and gamified experience.

## 🎯 Project Objectives

- Visually simulate the main planning algorithms: FCFS, SJF, Round Robin, Priorities and HRRN.
- Promote learning through gamification.
- Develop a clear, accessible and cross-platform application.
- Apply good development practices such as Clean Architecture, CI/CD and modularization.

## 🛠️ Technologies Used

| Technology | Purpose |
|----------------------------|------------------------------------------------|
| Kotlin Multiplatform Mobile | Shared Business Logic Android/iOS |
| Jetpack Compose | Declarative GUI (Android) |
| Firebase (Firestore + Auth) | Data Persistence & User Authentication |
| Git & GitHub Actions | Version Control & Continuous Integration |
| Clean Architecture | Modular & Scalable Architecture |

## 🚀 Installation and Execution

### Prerequisites

- Android Studio Arctic Fox or higher
- JDK 11+
- Firebase account with Firestore and Authentication enabled

### Cloning the project

```bash
git clone https://github.com/el_teu_usuari/system-game.git
cd system-game
```

### Android compilation

```bash
./gradlew build
```

### Running on emulator or device

```bash
./gradlew installDebug
```

## 🧪 CI/CD with GitHub Actions

- Automatic project compilation
- Running unit tests
- Generating builds for production
- Preparing for automated deployment

Configuration file: `.github/workflows/build.yml`

## 🖼️ Screenshots

| Login | Main Menu | Settings |
|-------|----------------|--------------|
| ![Login](docs/screenshots/login.png) | ![Menu](docs/screenshots/menu.png) | ![Stepper](docs/screenshots/stepper.png) |

| Simulation | Gantt | Results |
|-----------|--------|-----------|
| ![Simulation](docs/screenshots/simulation.png) | ![Gantt](docs/screenshots/gantt.png) | ![Results](docs/screenshots/results.png) |

## 📚 Project Structure

```
system-game/
├── app/ # Android frontend with Jetpack Compose
├── shared/ # Backend shared with KMM
│ ├── model/ # Data models (cards, CPU, etc.)
│ ├── repository/ # Persistence and interfaces
│ └── scheduler/ # Process planning logic
├── .github/workflows/ # Workflows for CI/CD
└── docs/ # Documentation and screenshots
```

## 🎮 Main Features

- User login and registration with Firebase
- Creation and configuration of custom simulations
- Implementation of algorithms such as FCFS, SJF, RR, Priorities, HRRN
- Execution visualization with a Gantt chart
- Automatic calculation of metrics (waiting time, response, return)
- Scalable and modular architecture based on Clean Architecture

## 🔮 Future Developments

- Version for iOS with SwiftUI
- Persistence and authentication also for iOS via shared module
- New game modes:
- Time trial: optimize planning time
- Task Guess: deduce hidden cards in partial simulations
- Insertion of processes in real time during execution
- CPU simulation with multiple execution threads (multithreading)

## 📄 Credits

**Author**: Joan Jaume Moll Alòs
**TFG Tutor**: Jordi Mateo Fornés
**Degree**: Degree in Digital Interaction and Computing Techniques
**University**: University of Lleida
**Year**: 2025

## 📜 License

This project is licensed under the [MIT License](LICENSE).
