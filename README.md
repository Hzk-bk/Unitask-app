# UniTask

## Overview

UniTask is a mobile task management application developed using Kotlin and Jetpack Compose. The application helps students organize academic tasks, track deadlines, monitor progress, and receive automated notifications through Firebase Cloud Functions.

## Features

### Authentication

* User Registration
* User Login
* User Logout
* Firebase Authentication Integration

### Task Management

* Create Tasks
* View Tasks
* Edit Tasks
* Delete Tasks
* Task Status Management (Open / Completed)

### Search and Filtering

* Search tasks by title
* Dynamic task filtering

### Statistics Dashboard

* Total Tasks Count
* Open Tasks Count
* Completed Tasks Count

### Deadline Management

* Calendar Date Picker
* Deadline Tracking

### Validation and Error Handling

* Task Title Validation
* Description Validation
* Deadline Validation
* User-Friendly Error Messages

### Notifications System

* Firebase Cloud Functions Integration
* Automatic Notifications on Task Creation
* Automatic Notifications on Task Updates
* Notifications Stored in Firestore
* Notifications Screen
* Mark Notifications as Read
* Unread Notifications Counter

### User Interface Improvements

* Splash Screen
* Welcome Screen
* Responsive Navigation Menu
* Status Color Indicators
* Delete Confirmation Dialog
* Improved Mobile User Experience

## Technologies Used

### Frontend

* Kotlin
* Jetpack Compose
* Material 3

### Backend

* Firebase Authentication
* Firebase Firestore
* Firebase Cloud Functions

### Development Tools

* Android Studio
* GitHub
* Trello
* Firebase CLI

## Firebase Services

### Firebase Authentication

Used to securely manage user registration and login.

### Cloud Firestore

Used to store and manage task and notification data.

### Firebase Cloud Functions

Used to automatically generate notifications when tasks are created or updated.

## Cloud Functions Implemented

### onTaskCreated

Triggered whenever a new task is created and automatically creates a notification document.

### onTaskUpdated

Triggered whenever an existing task is updated and automatically creates a notification document.

## Testing

Unit tests were implemented to verify:

* Task title validation
* Description validation
* Deadline validation
* Valid input handling
* Input error detection

## Project Management

Trello was used to:

* Manage the project backlog
* Organize sprint tasks
* Track development progress
* Record completed features

## Future Improvements

* Push Notifications
* Deadline Reminder System
* User Profile Managemen
* Task Categories
* Task Priority Levels

## Author

Developed as an individual Android mobile application project using Firebase services and modern Android development practices.
