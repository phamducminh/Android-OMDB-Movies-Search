# Android-OMDB-Movies-Search

## Tech Stack 🛠
- Language: [Kotlin](https://kotlinlang.org/) | [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html)
- Architecture: [Android Architecture Components](https://developer.android.com/topic/libraries/architecture)
    - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) | [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) | [ViewBinding](https://developer.android.com/topic/libraries/view-binding)
- Dependency Injection: [Hilt](https://developer.android.com/training/dependency-injection/hilt-android)
- Network: [Retrofit](https://square.github.io/retrofit/) | [GSON](https://github.com/google/gson) | [GSON Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/gson) | [OkHttp3](https://github.com/square/okhttp)
- Image Library: [Glide](https://github.com/bumptech/glide)
- Principle Design: [Material Components for Android](https://github.com/material-components/material-components-android)
- Memory Leak Detection: [LeakCanary](https://square.github.io/leakcanary/)

## Testing Frameworks

- Unit Testing: [JUnit](https://junit.org/junit4/) | [Mockito](https://site.mockito.org/) | [Kotlin Coroutines Test](https://kotlinlang.org/api/kotlinx.coroutines/kotlinx-coroutines-test/) | [Google Truth](https://github.com/google/truth) | [Gson](https://github.com/google/gson)
- UI Testing: [Android Test JUnit](https://developer.android.com/training/testing/local-tests) | [Espresso](https://developer.android.com/training/testing/espresso) | [Hilt Testing](https://developer.android.com/training/dependency-injection/hilt-testing)

# Package Structure
    src                             # src folder
    ├── androidTest                 # UI Testing
    ├── main
        ├── java
            com.pdminh.omdbmoviessearch    # Root Package
            ├── api                 # API Service using Retrofit
            ├── data                # Repository
            ├── di                  # Dependency Injection using Hilt
            ├── model               # Model classes including Movie, UiModel, UiState,...|
            ├── ui                  # Activity/ViewModel/RecyclerViewAdapter
            │   ├── viewholder      # ViewHolder for RecyclerView
            |── utils               # Utility Classes / Kotlin extensions
        ├── res                     # Resource folder
    ├── test                        # Unit Testing


## Architecture
This app uses [***MVVM (Model View View-Model)***](https://developer.android.com/jetpack/docs/guide#recommended-app-arch) architecture.

![](https://developer.android.com/topic/libraries/architecture/images/final-architecture.png)

## Room for more developing
1. Support Offline Mode
    * Integrate the Paging library and Room database to improve data management.
    * Fetch movies from the network and store them in the local database using Room.
    * Modify the repository to retrieve data directly from the database, ensuring seamless offline access.
2. Complete Unit Testing for ViewModel
    * Finalize and improve unit tests for MovieSearchViewModel to ensure correctness.
    * Use modern testing tools and best practices, including coroutine testing utilities.