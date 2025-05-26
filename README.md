# 🧠 System Game – Simulador de Planificació de Processos

**System Game** és una aplicació educativa multiplataforma que simula el funcionament dels algorismes de planificació de processos dins una CPU. Està orientada a estudiants d’enginyeria informàtica i desenvolupadors que volen entendre millor la gestió de processos mitjançant una experiència visual, interactiva i gamificada.

## 🎯 Objectius del Projecte

- Simular de manera visual els principals algorismes de planificació: FCFS, SJF, Round Robin, Prioritats i HRRN.
- Fomentar l'aprenentatge mitjançant la gamificació.
- Desenvolupar una aplicació clara, accessible i multiplataforma.
- Aplicar bones pràctiques de desenvolupament com Clean Architecture, CI/CD i modularització.

## 🛠️ Tecnologies Utilitzades

| Tecnologia                     | Propòsit                                       |
|-------------------------------|------------------------------------------------|
| Kotlin Multiplatform Mobile   | Lògica de negoci compartida Android/iOS       |
| Jetpack Compose               | Interfície gràfica declarativa (Android)      |
| Firebase (Firestore + Auth)   | Persistència de dades i autenticació d'usuaris|
| Git & GitHub Actions          | Control de versions i integració contínua     |
| Clean Architecture            | Arquitectura modular i escalable              |

## 🚀 Instal·lació i Execució

### Requisits previs

- Android Studio Arctic Fox o superior
- JDK 11+
- Compte de Firebase amb Firestore i Authentication activats

### Clonació del projecte

```bash
git clone https://github.com/el_teu_usuari/system-game.git
cd system-game
```

### Compilació Android

```bash
./gradlew build
```

### Execució a l'emulador o dispositiu

```bash
./gradlew installDebug
```

## 🧪 CI/CD amb GitHub Actions

- Compilació automàtica del projecte
- Execució de proves unitàries
- Generació de builds per a producció
- Preparació per a desplegament automatitzat

Arxiu de configuració: `.github/workflows/build.yml`

## 🖼️ Captures de Pantalla

| Login | Menú Principal | Configuració |
|-------|----------------|--------------|
| ![Login](docs/screenshots/login.png) | ![Menu](docs/screenshots/menu.png) | ![Stepper](docs/screenshots/stepper.png) |

| Simulació | Gantt | Resultats |
|-----------|--------|-----------|
| ![Simulació](docs/screenshots/simulation.png) | ![Gantt](docs/screenshots/gantt.png) | ![Resultats](docs/screenshots/results.png) |

## 📚 Estructura del Projecte

```
system-game/
├── app/                      # Frontend Android amb Jetpack Compose
├── shared/                   # Backend compartit amb KMM
│   ├── model/                # Models de dades (cards, CPU, etc.)
│   ├── repository/           # Persistència i interfícies
│   └── scheduler/            # Lògica de planificació de processos
├── .github/workflows/        # Workflows per CI/CD
└── docs/                     # Documentació i captures de pantalla
```

## 🎮 Funcionalitats Principals

- Login i registre d’usuaris amb Firebase
- Creació i configuració de simulacions personalitzades
- Implementació d’algorismes com FCFS, SJF, RR, Prioritats, HRRN
- Visualització de l’execució amb un diagrama de Gantt
- Càlcul automàtic de mètriques (temps d’espera, resposta, retorn)
- Arquitectura escalable i modular basada en Clean Architecture

## 🔮 Desenvolupaments Futurs

- Versió per a iOS amb SwiftUI
- Persistència i autenticació també per iOS via mòdul compartit
- Nous modes de joc:
  - Contrarellotge: optimitzar el temps de planificació
  - Task Guess: deduir cartes ocultes en simulacions parcials
- Inserció de processos en temps real durant l’execució
- Simulació de CPU amb múltiples fils d’execució (multithreading)

## 📄 Crèdits

**Autor**: Joan Jaume Moll Alòs  
**Tutor del TFG**: Jordi Mateo Fornés  
**Titulació**: Grau en Tècniques d’Interacció Digital i Computació  
**Universitat**: Universitat de Lleida  
**Any**: 2025

## 📜 Llicència

Aquest projecte està llicenciat sota la [MIT License](LICENSE).
