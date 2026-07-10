# Project Plan

App Name: BeatsList
Description: An application to manage a band's library of set lists. A set list is a list of songs in the order they will be played. A song contains lyrics and chords.
Features:
- View an alphabetically ordered list of songs (Open Lyrics).
- View a list of set lists (Open Set Lists).
- Create or edit songs (Manage Lyrics).
- Create or edit set lists (Manage Set Lists).
- Clicking a song in a set list displays its lyrics and chords.
- Opening screen with navigation to these functions.
- Strictly follow Material Design 3, vibrant/energetic color scheme, full edge-to-edge display, and adaptive app icon.

## Project Brief

# BeatsList - Project Brief

BeatsList is a dynamic, performance-
ready Android application designed for bands and musicians to manage their song libraries and curate set lists. The app provides a high-energy,
 Material Design 3 interface for quick access to lyrics and chords during rehearsals or live performances.

## Features
- **Song Library (Open Lyrics):** A comprehensive, alphabetically ordered repository of the band's repertoire, allowing users to create and edit song entries featuring lyrics and chord progressions.
- **Set List Manager (Manage Set Lists):** An intuitive interface to build and organize custom set lists, defining the exact order of songs for upcoming performances.
- **Live Performance Mode:** Interactive set list viewer where selecting a song instantly displays its lyrics and chords for seamless transitions on stage.
- **Vibrant Dashboard:** An energetic home screen providing rapid navigation to the song library and set list collections, optimized for high-pressure environments.

## High-Level Technical Stack
- **Language:** Kotlin
- **UI Framework:** Jetpack Compose with **Material Design 3** (Vibrant/Energetic theme, Edge-to-Edge display)
- **Navigation:** **Jetpack Navigation 3** (State-driven architecture)
- **Adaptive Layouts:** **Compose Material Adaptive** library (supporting diverse screen sizes and foldables)
- **Concurrency:** Kotlin Coroutines & Flow
- **Image Loading:** Coil (for song/album art placeholders)

## Implementation Steps
**Total Duration:** 30h 14m 3s

### Task_1_CoreDataAndSongs: Initialize Room database and implement Song management. Configure vibrant Material 3 theme and edge-to-edge support. Create UI for alphabetical song list and song editor (lyrics/chords).
- **Status:** COMPLETED
- **Updates:** - Implemented Room database with Song, SetList, and CrossRef entities.
- **Acceptance Criteria:**
  - Room entities and DAO for Songs and SetLists created
  - Material 3 theme with vibrant colors and edge-to-edge display configured
  - Song library displays alphabetically ordered songs
  - Create/Edit song functionality persists lyrics and chords
  - Build passes
- **Duration:** 10h 6m 42s

### Task_2_SetListsAndPerformance: Implement Set List management and performance mode. Create UI to build and order set lists, and a viewer to display song content during performance.
- **Status:** COMPLETED
- **Updates:** - Implemented Set List List screen for browsing and deleting set lists.
- **Acceptance Criteria:**
  - Set list list, creation, and editing (song ordering) implemented
  - Performance mode displays lyrics and chords when a song is selected from a set list
  - Seamless transition between songs in a set list
  - Build passes
- **Duration:** 10h 1m 53s

### Task_3_DashboardNavigationAdaptive: Build the home dashboard and integrate Navigation 3. Implement adaptive layouts to ensure the app works well on all screen sizes.
- **Status:** COMPLETED
- **Updates:** - Redesigned Home Dashboard with vibrant UI and navigation cards.
- Implemented adaptive layouts using Compose Material Adaptive (ListDetailSceneStrategy).
- Integrated Jetpack Navigation 3 with state-driven back stack.
- Created adaptive app icon.
- Refactored project structure for better organization.
- Successfully built the project.
- **Acceptance Criteria:**
  - Home dashboard provides navigation to Songs and Set Lists
  - Jetpack Navigation 3 state-driven architecture integrated
  - Adaptive layouts implemented using Compose Material Adaptive
  - App is fully navigable and handles configuration changes
  - Build passes
- **Duration:** 10h 5m 28s

### Task_4_FinalPolishAndVerify: Create an adaptive app icon and perform final verification. Ensure the app meets all Material 3 and vibrant aesthetic requirements.
- **Status:** IN_PROGRESS
- **Acceptance Criteria:**
  - Adaptive app icon matching the app's function created
  - Edge-to-edge display verified on all screens
  - Final aesthetic check for vibrant/energetic Material 3 design
  - Build passes
  - App does not crash
  - All existing tests pass
- **StartTime:** 2026-05-17 15:06:33 BST

