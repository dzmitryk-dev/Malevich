# Malevich Copilot Instructions

## Project Overview
Malevich is a Kotlin Multiplatform Compose application for generating and displaying procedural graphics (malunki).
The project supports multiple targets: Web (WASM), Android, and Desktop (JVM).

## Key Technologies
- **Language**: Kotlin Multiplatform (KMP)
- **UI Framework**: Jetbrains Compose
- **Build System**: Gradle (Kotlin DSL)
- **Targets**: 
  - Web (WASM/JS)
  - Android
  - Desktop (JVM)
- **Linting**: Detekt

## Project Structure
```
compose-app/
├── src/
│   ├── commonMain/kotlin/     # Shared Kotlin code
│   │   ├── ui/               # Composable UI components
│   │   ├── malunki/          # Procedural graphics generators (Mandelbrot, WhiteNoise, etc.)
│   │   └── App.kt
│   ├── androidMain/          # Android-specific code
│   ├── desktopMain/          # Desktop (JVM) specific code
│   └── wasmJsMain/           # Web (WASM) specific code
├── build.gradle.kts          # Gradle configuration
└── resources/                # Platform resources
```

## Common Tasks

### Build
```bash
./gradlew build
```

### Run Desktop App
```bash
./gradlew :compose-app:runDistributable
```

### Run Android App
```bash
./gradlew :compose-app:assembleDebug
```

### Run Web App
```bash
./gradlew :compose-app:wasmJsBrowserDevelopmentRun
```

### Lint Code
```bash
./gradlew detekt
```

## Code Guidelines

### Composable Functions
- Keep composables small and focused
- Use `@Composable` annotation
- Follow naming conventions: PascalCase for Composables
- Add `internal` visibility for private UI components

### Multiplatform Code
- Put shared logic in `commonMain`
- Use `expect/actual` pattern for platform-specific implementations when needed
- Import from `androidx.compose.*` for UI components

### Malunki (Graphics) Module
- Each graphics generator inherits from `Malunek` interface
- Implement pixel generation logic efficiently for performance
- Examples: `Mandelbrot.kt`, `WhiteNoise.kt`, `MandelbrotChatGpt.kt`

### Dependencies
- Check `libs.versions.toml` for available library versions
- Use version catalogs defined in build setup
- Update dependencies through the root `build.gradle.kts`

## Testing & Quality
- Run Detekt before committing: `./gradlew detekt`
- Ensure code compiles for all targets
- Test UI changes on desktop first, then verify on web and Android

## Important Files
- `.github/workflows/push.yml` - CI/CD pipeline
- `gradle.properties` - Project properties
- `local.properties` - Local development settings (not committed)
- `mainframer.sh` - Development script
