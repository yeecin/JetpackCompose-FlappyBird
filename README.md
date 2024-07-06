# JetpackCompose-FlappyBird


A clone of the popular Flappy Bird game built using Jetpack Compose and Kotlin. This project demonstrates the integration of a traditional Android `SurfaceView` within a Jetpack Compose UI.

## Features

- Flappy Bird gameplay
- Custom game loop with a `SurfaceView`
- Collision detection
- Score tracking
- Sound effects

## Getting Started

### Prerequisites

- Android Studio Arctic Fox or later
- Kotlin 1.5 or later

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/yourusername/jetpack-compose-flappy-bird.git
    ```

2. Open the project in Android Studio.

3. Sync the project with Gradle files.

4. Run the app on an emulator or a physical device.

## Project Structure

- `MainActivity.kt`: The main entry point of the app. Integrates the `GameManager` with Jetpack Compose.
- `GameManager.kt`: The custom `SurfaceView` handling the game logic, rendering, and touch events.
- `Bird.kt`, `ObstacleManager.kt`, `Background.kt`, etc.: Game components for the Flappy Bird game.

## Contributing
Contributions are welcome! Please open an issue or submit a pull request if you have any suggestions or improvements.

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgements
Original Flappy Bird game by Dong Nguyen
Sound effects from freesound.org
