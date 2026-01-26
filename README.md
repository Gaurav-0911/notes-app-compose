# Notes App – Task 2

## Overview
This is a Notes application developed using **Kotlin**, **Jetpack Compose**, and **Room Database** as part of **Android App Development – Task 2**.

In Task 1, notes were stored temporarily in memory and were lost when the app was closed.  
In Task 2, the application has been upgraded into a **real Android application with persistent local storage** using Room Database.

The app allows users to **add, view, update, and delete notes**, and all data remains saved even after restarting the app.

---

## Features
- Single Activity application
- Built with Jetpack Compose (Material 3)
- Add notes using dialog
- Display notes using LazyColumn
- Edit notes using update dialog
- Delete notes using delete action
- Persistent local storage using Room Database
- MVVM architecture implementation
- Reactive UI updates using StateFlow

---

## Tech Stack
- Kotlin
- Jetpack Compose
- Room Database
- ViewModel
- Kotlin Coroutines
- StateFlow
- Material 3
- Android Studio

---

## Database Architecture Explanation
This application uses **Room Database** to provide persistent local data storage. The database architecture follows the **MVVM (Model–View–ViewModel)** pattern to ensure clean separation of concerns and maintainable code.

The **NoteEntity** represents the database table and defines the structure of a note, including fields such as an auto-generated ID, title, and description. The **NoteDao (Data Access Object)** contains methods for performing CRUD operations such as insert, update, delete, and fetch notes. These methods provide an abstraction over SQLite queries.

The **NoteDatabase** class acts as the main access point to the Room database and is implemented as a singleton to ensure only one instance exists during the application lifecycle. A **Repository layer** is used to manage data operations and acts as a bridge between the ViewModel and the DAO, ensuring that the ViewModel does not directly interact with the database.

The **NotesViewModel** communicates with the repository and exposes note data to the UI using **StateFlow**. Whenever a note is added, updated, or deleted, the database is updated through the DAO, and the latest data is automatically reflected in the Jetpack Compose UI. This architecture ensures persistent storage, efficient CRUD operations, and a reactive user interface suitable for production-level Android applications.

---

## 📸 Screenshots (Task 2)

### ➕ Add Note
![Add Note](screenshots_task2/add_note.jpg)

### ✏️ Update Note
![Update Note](screenshots_task2/update_note.jpg)

### 🗑 Delete Note
![Delete Note](screenshots_task2/delete_note.jpg)

### 🔄 CRUD Operations Overview
![CRUD](screenshots_task2/crud.jpg)

## How to Run the App
1. Open the project in Android Studio.
2. Allow Gradle sync to complete.
3. Connect a physical device or emulator.
4. Click the Run button to launch the app.

---

## Learning Outcomes
- Understanding of Android local data persistence
- Implementation of Room Database architecture
- Full CRUD operations in Android
- MVVM architecture using ViewModel and Repository
- Jetpack Compose UI development
- Production-ready Android app structure

---

## Author
Kumar Gaurav

