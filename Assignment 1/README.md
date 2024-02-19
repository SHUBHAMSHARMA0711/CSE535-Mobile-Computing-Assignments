# Journey Tracker Application

## Overview
This Android app, implemented in Kotlin using Jetpack Compose, serves as a visual representation of a journey with multiple stops. The app displays progress, distance covered, remaining distance, and a list of stations with their corresponding distances.

## Key Components

### 1. `MainActivity`
- Entry point of the application.
- Sets up the Compose UI using the `AssignmentTheme` and `Surface`.
- Invokes the `Main` composable function.

### 2. `Main` Composable
- Manages the overall UI layout and logic.
- Utilizes various Jetpack Compose components such as `Button`, `Row`, `Column`, and custom `MainUI` composable.
- Handles station reaching logic, distance unit switching, and station list switching.

### 3. `MainUI` Composable
- Displays the main user interface, including total distance, distance covered, remaining distance, progress bar, and a list of stations.
- Utilizes Jetpack Compose components like `Column`, `Row`, `LinearProgressIndicator`, `Icon`, and custom `NormalList` and `LazyList` composables.
- Implements a `Two-Column` layout for the `Total Distance` and a `Two-Row` layout for the `Distance Covered` and `Remaining Distance`.

### 4. `NormalList` and `LazyList` Composables
- Display the list of stations and their distances.
- `NormalList` is used when there are a specific number of stations (10 in this case), while `LazyList` is used when the number of stations is more than 10.
- Both `NormalList` and `LazyList` will use Jetpack Compose's `Column` and `LazyColumn` composable respectively to display lists.

### 5. Helper Functions
- `reduceToNDecimalPlace`: A utility function to round a double value to two decimal places using `BigDecimal`.

## Preview
- The `@Preview` annotation is used for UI preview during development.

## Usage
- The main screen displays journey progress, control buttons, and a list of stations.
- Tap the `Reached` button when reaching a station to mark it as reached.
- Use the `Km/Mi` button to toggle between distance units.
- The progress bar visually represents the journey progress.