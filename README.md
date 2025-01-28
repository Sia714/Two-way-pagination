# SpeakX - Two-Way Pagination with MVVM Architecture

## Overview
SpeakX is a sample Android application that demonstrates how to implement two-way pagination using MVVM architecture. The app fetches data dynamically when the user scrolls up or down, displaying progress bars at the top or bottom as data loads.

---

## Features
- Implements **MVVM Architecture** with clear separation of concerns.
- **Two-Way Pagination**: Fetches more data when the user scrolls up or down.
- **Progress Bar Visibility**: Progress bars appear and disappear based on the scroll direction and data-fetching state.
- Search functionality to filter displayed items.

---

## Project Structure
### Package Structure:
1. **speakx.model**
   - Contains `DataRep`, which mocks data fetching.
2. **speakx.viewmodel**
   - Contains `MainViewModel`, which manages the app's data and state.
3. **MainActivity**
   - Connects the View (UI elements) with the ViewModel.

### Layout Files:
- **`activity_main.xml`**: Contains the `ListView`, `ProgressBar` (top and bottom), and `SearchView`.

---

## Prerequisites
- Android Studio installed on your system.
- Minimum SDK Version: 21
- Target SDK Version: 33

---

## Steps to Run the Project

### 1. Clone the Repository
Clone the project to your local machine using:
```bash
git clone https://github.com/Sia714/Two-way-pagination
```

### 2. Open in Android Studio
1. Open Android Studio.
2. Select **File > Open** and navigate to the project directory.
3. Wait for Gradle to sync and build the project.

### 3. Run the Project
1. Connect an Android device or start an emulator.
2. Click on the **Run** button in Android Studio or press **Shift + F10**.

### 4. Interact with the App
- Scroll up or down to see data load dynamically with progress bars.
- Use the search bar to filter items.

---

## Troubleshooting
- **Gradle Sync Issues**: Ensure you have a stable internet connection and are using the correct Gradle version.
- **Emulator Issues**: Verify that your emulator meets the minimum SDK requirements.
- **Data Not Loading**: Check `MainViewModel` for any logic issues or logs for errors.

---

## Additional Notes
- The data is mocked in `DataRep`. You can replace it with actual API calls for a real-world application.
- Modify the `ListView` adapter to display more complex data types if needed.

---

## Contact
If you have any questions or suggestions, feel free to reach out:
- Email: sayjan7777@gmail.com
- GitHub: [YourGitHubProfile](https://github.com/Sia714/Two-way-pagination)

