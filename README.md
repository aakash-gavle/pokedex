# Pokedex Android Application

A native Android application that fetches and displays Pokemon data from the PokeAPI. The project is structured using Clean Architecture principles and Jetpack Compose for the user interface.

## Tech Stack
- Language: Kotlin
- UI Framework: Jetpack Compose with Material 3
- Networking: Retrofit & Gson
- Paging: Jetpack Paging 3 (Paging & Compose)
- Image Loading: Coil
- Architecture: Clean Architecture (Data, Domain, Presentation) with UDF (Unidirectional Data Flow)
- Dependency Injection: Configured with Dagger Hilt (instantiated via manual injection in the application class)

## Architecture
The project follows clean architecture separation of concerns across three layers:

### 1. Data Layer
Handles network requests and data model transformations:
- API Client: Retrofit-based API interface (`PokemonListApiService`) defining endpoints for fetching a list of Pokemon (`pokemon`) and details for a specific Pokemon (`pokemon/{name}`).
- Pagination: Implements `PagingSource` (`PokemonPagingSource`) to manage page key offsets and limits, mapping remote data transfer objects (`PokemonDTO`) to domain model entities (`PokemonOverview`).
- Repository implementation: `PokemonRepositoryImpl` connects the paging flow configuration and detail endpoint to the domain-level repository interface.

### 2. Domain Layer
Contains the core business rules and data models, completely independent of UI or framework libraries:
- Use Cases:
  - `GetPokemonListUseCase`: Fetches a stream of paginated Pokemon entries.
  - `GetPokemonDetailUseCase`: Fetches details (height, base experience, abilities, sprites) for a specific Pokemon.
- Models: Plain Kotlin data classes (`PokemonOverview`, `PokemonDetail`, `Ability`) representing application data.
- Repository Interface: Abstraction contract (`PokemonRepository`) for the data layer.

### 3. Presentation Layer
Implements UI screens and manages UI state using Compose and Jetpack Lifecycle Components:
- State Management: `PokedexViewModel` holds the paginated list flow (`Flow<PagingData<PokemonOverview>>`) and details state flow (`StateFlow<DetailUiState>`).
- UI Screens:
  - `PokemonListScreen`: Lists Pokemon in a scrollable view with Jetpack Compose paging support, featuring loading states, retry handles, and append loaders.
  - `PokemonDetailScreen`: Displays details for a single Pokemon. It includes a statistics card, lists of active abilities, and a horizontal scrollable view showcasing official/shiny sprites.
- Widgets: `PokemonItemCard` provides a consistent grid/list card component with an integrated preview image.

## Project Structure
```
app/src/main/java/com/aakash/android/
├── App.kt (Application subclass managing dependencies)
├── MainActivity.kt (Entry point setting up compose UI and Navigation Host)
├── AppNavHost.kt (Navigation configuration with Compose Destination Routes)
├── data/
│   ├── remote/ (ApiService, DTOs, and PagingSource)
│   └── repository/ (Repository implementation)
├── domain/
│   ├── model/ (Domain models)
│   ├── repository/ (Repository interfaces)
│   └── usecase/ (Business logic use cases)
├── di/
│   └── AppModules.kt (Hilt module setup)
├── presentation/
│   ├── screen/ (PokemonListScreen and PokemonDetailScreen)
│   ├── viewmodel/ (PokedexViewModel and DetailUiState)
│   └── widgets/ (Reusable UI components)
├── ui/
│   └── theme/ (Compose colors, typography, and themes)
└── utils/
    └── constants.kt (API base URLs and settings)
```

## Setup and Run
1. Open the project in Android Studio (Ladybug or later recommended).
2. Sync the project with Gradle files.
3. Build and run the `app` module on a compatible emulator or physical device running API level 32 or higher.
