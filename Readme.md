<div align="center">

<img width="96" src="https://github.com/TutorialsAndroid/FilePicker/blob/master/sample/src/main/res/mipmap-xxhdpi/ic_launcher.png" alt="FilePicker logo">

# FilePicker

**A lightweight Android file and directory picker library for Java/Kotlin Android apps.**

[![API](https://img.shields.io/badge/API-23%2B-brightgreen.svg?style=flat)](#requirements)
[![Maven Central](https://img.shields.io/maven-central/v/io.github.tutorialsandroid/filepicker)](https://central.sonatype.com/artifact/io.github.tutorialsandroid/filepicker)
[![](https://jitpack.io/v/TutorialsAndroid/FilePicker.svg)](https://jitpack.io/#TutorialsAndroid/FilePicker)
[![License](https://img.shields.io/badge/License-Apache%202.0-orange.svg)](https://opensource.org/licenses/Apache-2.0)
[![AndroidX](https://img.shields.io/badge/AndroidX-supported-brightgreen)](#requirements)

Select files, folders, or both from device storage with single-selection and multi-selection support.

</div>

---

<div align="center">

## 🌐 Explore the Official FilePicker Landing Page

Experience the complete documentation, installation guide, Android storage notes, Play Store compliance guidance, customization examples, and API reference in a clean visual format.

<br>

<a href="https://tutorialsandroid.github.io/FilePicker/" target="_blank">
  <img src="https://img.shields.io/badge/View%20Landing%20Page-FilePicker%20Docs-2563EB?style=for-the-badge&logo=githubpages&logoColor=white" alt="View FilePicker Landing Page">
</a>

<a href="https://github.com/TutorialsAndroid/FilePicker" target="_blank">
  <img src="https://img.shields.io/badge/GitHub-Repository-181717?style=for-the-badge&logo=github&logoColor=white" alt="GitHub Repository">
</a>

<a href="https://github.com/sponsors/TutorialsAndroid" target="_blank">
  <img src="https://img.shields.io/badge/Sponsor%20Project-GitHub%20Sponsors-EA4AAA?style=for-the-badge&logo=githubsponsors&logoColor=white" alt="Sponsor FilePicker">
</a>

<br><br>

<table>
  <tr>
    <td align="center"><strong>📦 Installation</strong><br>Maven Central + JitPack setup</td>
    <td align="center"><strong>🛡️ Play Store Guide</strong><br>Storage policy and compliance notes</td>
    <td align="center"><strong>🎨 Customization</strong><br>Theme, dialog size, filters, and examples</td>
  </tr>
</table>

<br>

<strong>Recommended for new users:</strong> Start with the landing page for the fastest setup and complete Android version-wise guidance.

</div>

---

Community links:

- Instagram: https://instagram.com/coderx09
- Telegram: https://t.me/a_masram444

---

## Contributors

Thanks to all contributors who helped improve this library.

- [M&R Games](https://github.com/mrgames13)
- [Hatzen](https://github.com/Hatzen)

---

## ❤️ Support the Project

If this library helps you, please consider supporting my open-source work.

<p align="start">
  <a href="https://github.com/sponsors/TutorialsAndroid">
    <img src="https://img.shields.io/badge/Sponsor%20on-GitHub-EA4AAA?style=for-the-badge&logo=githubsponsors&logoColor=white" alt="Sponsor on GitHub" />
  </a>
</p>

---

## Table of Contents

- [Overview](#overview)
- [What is new in v10](#what-is-new-in-v10)
- [Important Android storage reality](#important-android-storage-reality)
- [Requirements](#requirements)
- [Installation](#installation)
- [Permissions by Android version](#permissions-by-android-version)
- [Google Play Store policy and compliance](#google-play-store-policy-and-compliance)
- [Manifest setup](#manifest-setup)
- [Basic usage](#basic-usage)
- [Customizing dialog theme](#customizing-dialog-theme)
- [Changing dialog width and height](#changing-dialog-width-and-height)
- [Complete Java example](#complete-java-example)
- [DialogProperties reference](#dialogproperties-reference)
- [DialogConfigs reference](#dialogconfigs-reference)
- [Extension filtering](#extension-filtering)
- [Single and multiple selection](#single-and-multiple-selection)
- [Selecting directories](#selecting-directories)
- [Pre-selecting files](#pre-selecting-files)
- [Handling Android 11+ all-files access](#handling-android-11-all-files-access)
- [Handling Android 13+ media permissions](#handling-android-13-media-permissions)
- [Handling Android 14+ partial photo/video access](#handling-android-14-partial-photovideo-access)
- [Play Store safe integration options](#play-store-safe-integration-options)
- [Troubleshooting](#troubleshooting)
- [Migration guide from v9.x to v10.1.3](#migration-guide-from-v9x-to-v1013)
- [Security and privacy recommendations](#security-and-privacy-recommendations)
- [FAQ](#faq)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

`FilePicker` is an Android library that provides a simple UI for selecting:

- Files
- Directories
- Files and directories together
- Single item
- Multiple items
- Specific file extensions
- Hidden files, when explicitly enabled

The library returns selected paths as a `String[]`.

```java
dialog.setDialogSelectionListener(files -> {
    for (String path : files) {
        // Use selected path
    }
});
```

> **Important:** This library is a **path-based file picker**. Android storage rules changed heavily from Android 10 onward. On Android 11+ full shared-storage browsing requires special handling, and for Google Play apps, `MANAGE_EXTERNAL_STORAGE` is allowed only for specific eligible app categories.

---

## What is new in v10

Version `10.X.X` focuses on modern Android support, stability, clearer storage behavior, dialog customization, and advanced file filtering.

### New in v10.1.3

- From `v10.1.3`, FilePicker supports checkbox color customization through `DialogProperties`.

### New in v10.1.2

- Added file size filtering support using `min_file_size` and `max_file_size`.
- Added support for selecting files below a specific size.
- Added support for selecting files above a specific size.
- Added support for selecting files between a minimum and maximum size range.
- Added support for combining file size filtering with extension filtering.
- Added examples for selecting video files below a specific file size.
- Added examples for selecting PDF files below a specific file size.
- Added examples for selecting large files such as videos between 100 MB and 2 GB.
- Added README documentation explaining that file size filtering works with all file types, not only videos.

### New in v10.1.1

- Added programmatic dialog width and height customization.
- Added responsive percentage-based dialog sizing for tablets, foldables, landscape mode, and ultra-wide screens.
- Added direct layout-param based sizing support using `ViewGroup.LayoutParams.MATCH_PARENT`, `WRAP_CONTENT`, or exact pixel values.
- Documented the GitHub issue solution for changing dialog width and height before showing the picker.
- Improved README examples for dialog theme and dialog size customization.

### Core improvements

- Updated default storage handling.
- Improved Android 11+ behavior.
- Improved Android 13+ permission guidance.
- Added Android 14+ partial media access documentation.
- Safer extension filtering.
- Safer file size filtering.
- Safer checkbox handling.
- Safer selected item handling.
- More predictable selected path ordering.
- Better null checks for unreadable folders and invalid paths.
- Better documentation for Play Store compliance.

### Stability improvements

- Prevents checkbox listener `NullPointerException`.
- Prevents recycled-row checkbox state issues.
- Handles unreadable directories safely.
- Handles missing parent directories safely.
- Handles invalid offset/root configuration safely.
- Handles empty folders safely.
- Handles extension formats like `pdf`, `.pdf`, and `*`.
- Handles file size limits using `long` values to support large file sizes.

---

## Important Android storage reality

Android does **not** allow every app to freely browse the full device storage on modern Android versions.

This library returns direct file paths. Because of this:

- Android 6.0 to Android 10 can work with normal runtime storage permissions. Because this library now uses `minSdk 23`, runtime permission handling is required for every supported Android version below Android 11.
- Android 11+ full shared-storage browsing needs **All files access** (`MANAGE_EXTERNAL_STORAGE`) if you want raw path access to many non-media files.
- Android 13+ media permissions only cover images, videos, and audio.
- Android 14+ can allow users to grant only selected photos/videos instead of full media access.
- Google Play restricts `MANAGE_EXTERNAL_STORAGE` to apps where broad file access is the core functionality.

If your app is not a file manager, backup app, antivirus app, document-management app, or another Play-approved category, consider using Android's Storage Access Framework instead of asking for All files access.

Official references:

- Android all files access: https://developer.android.com/training/data-storage/manage-all-files
- Google Play all files access policy: https://support.google.com/googleplay/android-developer/answer/10467955
- Android 13 granular media permissions: https://developer.android.com/about/versions/13/behavior-changes-13
- Android 14 selected photos access: https://developer.android.com/about/versions/14/changes/partial-photo-video-access

---

## Requirements

| Requirement | Value |
|---|---|
| Minimum SDK | API 23+ / Android 6.0+ |
| Recommended compile SDK | Latest stable Android SDK |
| Language | Java compatible |
| AndroidX | Required |
| Supports Android 13+ | Yes, with correct media/all-files permission handling |
| Supports Android 14+ | Yes, with partial media access awareness |
| Returns | Direct file paths |
| Best use case | File manager, document manager, backup/restore, local file tools, developer utilities |

> **minSdk note:** FilePicker v10.1.1 is documented for `minSdk 23`. Android 5.0 and 5.1 support has been removed from this README and should not be advertised in badges, Gradle metadata, or release notes.

---

## Installation

FilePicker v10.1.3 is available through **Maven Central** and **JitPack**.

Recommended installation method: **Maven Central**  
Alternative installation method: **JitPack**

> Minimum supported SDK: **API 23 / Android 6.0+**

---

## Option 1: Installation using Maven Central

Maven Central is the recommended way to use this library.

### Step 1: Add Maven Central repository

Open your project-level `settings.gradle` file.

Usually this file is located here:

```text
YourProject/settings.gradle
````

or, if you are using Kotlin DSL:

```text
YourProject/settings.gradle.kts
```

Add `mavenCentral()` inside `dependencyResolutionManagement`.

### Groovy DSL - settings.gradle

```gradle
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "YourProjectName"
include ':app'
```

If your project already has `dependencyResolutionManagement`, do not duplicate the full block. Just make sure `mavenCentral()` is added inside `repositories`.

Example:

```gradle
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}
```

### Kotlin DSL - settings.gradle.kts

```kotlin
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "YourProjectName"
include(":app")
```

---

### Step 2: Add FilePicker dependency

Open your app-level Gradle file.

Usually this file is located here:

```text
YourProject/app/build.gradle
```

or, if you are using Kotlin DSL:

```text
YourProject/app/build.gradle.kts
```

Add the FilePicker dependency inside the `dependencies` block.

### Groovy DSL - app/build.gradle

```gradle
dependencies {
    implementation "io.github.tutorialsandroid:filepicker:10.1.3"
}
```

### Kotlin DSL - app/build.gradle.kts

```kotlin
dependencies {
    implementation("io.github.tutorialsandroid:filepicker:10.1.3")
}
```

---

### Step 3: Set minimum SDK to 23 or higher

FilePicker v10.1.1 requires `minSdk 23` or higher.

Open your app-level Gradle file:

```text
YourProject/app/build.gradle
```

Inside the `android` block, add or update `minSdk`.

### Groovy DSL - app/build.gradle

```gradle
android {
    namespace "com.example.yourapp"
    compileSdk 35

    defaultConfig {
        applicationId "com.example.yourapp"
        minSdk 23
        targetSdk 35
        versionCode 1
        versionName "1.0"
    }
}
```

### Kotlin DSL - app/build.gradle.kts

```kotlin
android {
    namespace = "com.example.yourapp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.yourapp"
        minSdk = 23
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
    }
}
```

If your project already has `minSdk 23` or higher, no change is required.

---

### Step 4: Sync the project

After adding the dependency, click:

```text
Sync Now
```

in Android Studio.

You can also sync manually from:

```text
File > Sync Project with Gradle Files
```

---

## Option 2: Installation using JitPack

JitPack support is also available for developers who prefer GitHub-based dependency installation.

> Maven Central is still recommended for production apps.

---

### Step 1: Add JitPack repository

Open your project-level `settings.gradle` file.

Usually this file is located here:

```text
YourProject/settings.gradle
```

Add `maven { url 'https://jitpack.io' }` inside `dependencyResolutionManagement`.

### Groovy DSL - settings.gradle

```gradle
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}

rootProject.name = "YourProjectName"
include ':app'
```

### Kotlin DSL - settings.gradle.kts

```kotlin
pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}

rootProject.name = "YourProjectName"
include(":app")
```

---

### Step 2: Add JitPack dependency

Open your app-level Gradle file:

```text
YourProject/app/build.gradle
```

Add the dependency inside the `dependencies` block.

### Groovy DSL - app/build.gradle

```gradle
dependencies {
    implementation "com.github.TutorialsAndroid:FilePicker:v10.1.3"
}
```

### Kotlin DSL - app/build.gradle.kts

```kotlin
dependencies {
    implementation("com.github.TutorialsAndroid:FilePicker:v10.1.3")
}
```

---

### Step 3: Set minimum SDK

FilePicker v10.1.1 requires `minSdk 23` or higher.

### Groovy DSL

```gradle
android {
    defaultConfig {
        minSdk 23
    }
}
```

### Kotlin DSL

```kotlin
android {
    defaultConfig {
        minSdk = 23
    }
}
```

---

## Important Notes

### Maven Central vs JitPack

For production apps, Maven Central is recommended:

```gradle
implementation "io.github.tutorialsandroid:filepicker:10.1.3"
```

Use JitPack only if you specifically want to fetch the library directly from GitHub:

```gradle
implementation "com.github.TutorialsAndroid:FilePicker:v10.1.3"
```

---

### Do not add repositories inside app/build.gradle in modern Android projects

Older Android projects used this style:

```gradle
allprojects {
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

For modern Android Gradle Plugin versions, repositories should usually be added in:

```text
settings.gradle
```

inside:

```gradle
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}
```

Only use the old `allprojects` method if your project is very old and does not have `dependencyResolutionManagement`.

---

## Installation

### Maven Central

```gradle
dependencies {
    implementation "io.github.tutorialsandroid:filepicker:10.1.3"
}
```

### JitPack

```gradle
dependencies {
    implementation "com.github.TutorialsAndroid:FilePicker:v10.1.3"
}
```

### Minimum SDK

```gradle
android {
    defaultConfig {
        minSdk 23
    }
}
```

Small Note: use **one dependency only** in your project. Do not add both Maven Central and JitPack dependency at the same time, otherwise Gradle may pull duplicate versions.

---

## Permissions by Android version

### Quick permission table

| Android version | API | Files/images/videos/audio behavior | Recommended approach |
|---|---:|---|---|
| Android 6.0 - 9 | 23-28 | Runtime `READ_EXTERNAL_STORAGE` / limited legacy write behavior | Request runtime read permission; request write only if your app truly writes to shared storage on older Android |
| Android 10 | 29 | Scoped storage introduced; legacy flag can help old path-based apps | Use `requestLegacyExternalStorage="true"` only when needed |
| Android 11 - 12L | 30-32 | Scoped storage enforced; broad path access requires All files access | Use SAF/MediaStore, or `MANAGE_EXTERNAL_STORAGE` only if eligible |
| Android 13 | 33 | `READ_EXTERNAL_STORAGE` replaced for media by granular media permissions | Use `READ_MEDIA_IMAGES`, `READ_MEDIA_VIDEO`, `READ_MEDIA_AUDIO`; use All files access only if eligible |
| Android 14+ | 34+ | Partial photo/video access introduced | Handle selected media access; use SAF/MediaStore when possible |

---

## Google Play Store policy and compliance

This section is very important if you publish an app using this library on Google Play.

### 1. Do not blindly add `MANAGE_EXTERNAL_STORAGE`

`MANAGE_EXTERNAL_STORAGE` is a high-risk permission. Google Play allows it only when broad file access is required for the app's **core functionality**.

Usually permitted categories include apps such as:

- File managers
- Backup and restore apps
- Antivirus apps
- Document management apps
- Device migration apps
- Search/indexing tools where broad storage access is central
- Disk/file cleanup tools, if the policy requirements are satisfied

Usually rejected or risky cases include:

- Normal gallery apps that can use MediaStore
- Chat apps that only attach selected files
- Apps that only upload one selected document
- Apps that only need a user-selected folder
- Apps that can use Android Photo Picker, SAF, or MediaStore
- Apps that request All files access only for convenience

### 2. Complete the Play Console Permissions Declaration Form

If your app declares `MANAGE_EXTERNAL_STORAGE`, you may need to complete the Permissions Declaration Form in Play Console and explain:

- Why your app needs broad file access.
- Why SAF or MediaStore is not enough.
- What user-facing core feature depends on it.
- How users benefit from this access.
- How you protect user data.

### 3. Keep the permission off if not needed

If your app does not truly need full shared-storage browsing, remove this permission:

```xml
<uses-permission android:name="android.permission.MANAGE_EXTERNAL_STORAGE" />
```

Use one of these alternatives:

- Android Photo Picker for user-selected images/videos.
- Storage Access Framework for user-selected documents/folders.
- MediaStore for images, videos, and audio.
- App-specific storage for private app files.

### 4. Privacy policy and Data safety

If your app accesses user files, your privacy policy should clearly explain:

- What types of files are accessed.
- Whether files are uploaded or processed locally.
- Whether file paths, file names, or metadata are collected.
- Whether files are shared with third parties.
- How users can delete their data.
- Why the permission is necessary.

In Play Console Data safety, declare file/media access truthfully.

### 5. Recommended Play Store wording

Use a simple user-facing explanation:

> This app needs file access so you can browse, select, manage, and organize files stored on your device. The app does not access files in the background and only uses selected files for user-requested actions.

For All files access:

> All files access is required because the app's core file manager feature lets users browse, search, select, and manage documents and folders across shared storage. Without this access, the core file-management feature cannot function correctly.

Do not claim you need All files access if your app only selects one file occasionally.

---

## Manifest setup

Use only the permissions required by your app. Do not copy every permission blindly.

### Full path-based file picker manifest

Use this only if your app is eligible for broad file access.

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools">

    <!-- Android 6.0 to Android 12L read access. minSdk is 23, so no API 21/22 handling is required. -->
    <uses-permission
        android:name="android.permission.READ_EXTERNAL_STORAGE"
        android:maxSdkVersion="32" />

    <!-- Write permission is meaningful only for older Android versions -->
    <uses-permission
        android:name="android.permission.WRITE_EXTERNAL_STORAGE"
        android:maxSdkVersion="28" />

    <!-- Android 13+ media permissions -->
    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
    <uses-permission android:name="android.permission.READ_MEDIA_VIDEO" />
    <uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />

    <!-- Android 14+ selected photo/video access awareness -->
    <uses-permission android:name="android.permission.READ_MEDIA_VISUAL_USER_SELECTED" />

    <!-- Android 11+ broad shared-storage access.
         Use only if your app is eligible under Google Play policy. -->
    <uses-permission
        android:name="android.permission.MANAGE_EXTERNAL_STORAGE"
        tools:ignore="ScopedStorage" />

    <application
        android:theme="@style/AppTheme"
        android:requestLegacyExternalStorage="true">
        ...
    </application>

</manifest>
```

### Play Store safer manifest

For apps that only allow users to pick a document or media item occasionally, do not use `MANAGE_EXTERNAL_STORAGE`.

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <uses-permission
        android:name="android.permission.READ_EXTERNAL_STORAGE"
        android:maxSdkVersion="32" />

    <uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
    <uses-permission android:name="android.permission.READ_MEDIA_VIDEO" />
    <uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />

    <application
        android:theme="@style/AppTheme">
        ...
    </application>

</manifest>
```

---

## Basic usage

### 1. Create `DialogProperties`

```java
DialogProperties properties = new DialogProperties();
```

### 2. Configure picker behavior

```java
properties.selection_mode = DialogConfigs.SINGLE_MODE;
properties.selection_type = DialogConfigs.FILE_SELECT;
properties.root = new File(DialogConfigs.DEFAULT_DIR);
properties.offset = new File(DialogConfigs.DEFAULT_DIR);
properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
properties.extensions = null;
properties.show_hidden_files = false;
```

### 3. Create dialog

```java
FilePickerDialog dialog = new FilePickerDialog(this, properties);
dialog.setTitle("Select a File");
dialog.setPositiveBtnName("Select");
dialog.setNegativeBtnName("Cancel");
```

### 4. Receive selected paths

```java
dialog.setDialogSelectionListener(files -> {
    for (String path : files) {
        File file = new File(path);
        // Use the selected file
    }
});
```

### 5. Show dialog

```java
dialog.show();
```

> On Android 11+, make sure required storage access is already granted before calling `dialog.show()`.

---

## Customizing dialog theme

This section answers a common GitHub issue/question: **"How to change Dialog theme?"**

`FilePickerDialog` supports custom dialog themes through the constructor that accepts `themeResId`:

```java
FilePickerDialog dialog = new FilePickerDialog(
        MainActivity.this,
        properties,
        R.style.YourCustomFilePickerTheme
);
```

Use this constructor when you want to change the dialog colors, typography, shape, background, or Material/AppCompat dialog styling.

### Complete themed dialog example

```java
DialogProperties properties = new DialogProperties();
properties.selection_mode = DialogConfigs.SINGLE_MODE;
properties.selection_type = DialogConfigs.FILE_SELECT;
properties.root = new File(DialogConfigs.DEFAULT_DIR);
properties.offset = new File(DialogConfigs.DEFAULT_DIR);
properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
properties.extensions = null;
properties.show_hidden_files = false;

FilePickerDialog dialog = new FilePickerDialog(
        MainActivity.this,
        properties,
        R.style.YourCustomFilePickerTheme
);

dialog.setTitle("Select a File");
dialog.setPositiveBtnName("Select");
dialog.setNegativeBtnName("Cancel");

dialog.setDialogSelectionListener(files -> {
    for (String path : files) {
        // Use selected path
    }
});

dialog.show();
```

### Example theme using AppCompat

Add this in `res/values/styles.xml`:

```xml
<style name="YourCustomFilePickerTheme" parent="Theme.AppCompat.Light.Dialog.Alert">
    <item name="colorPrimary">#6750A4</item>
    <item name="colorPrimaryDark">#4F378B</item>
    <item name="colorAccent">#6750A4</item>
    <item name="android:fontFamily">sans</item>
</style>
```

### Example theme using Material Components

Use this only if your app already depends on Material Components and your app theme is Material-compatible.

```xml
<style name="YourCustomFilePickerTheme" parent="Theme.MaterialComponents.DayNight.Dialog.Alert">
    <item name="colorPrimary">#6750A4</item>
    <item name="colorPrimaryVariant">#4F378B</item>
    <item name="colorSecondary">#6750A4</item>
    <item name="colorAccent">#6750A4</item>
    <item name="android:fontFamily">sans</item>
</style>
```

### Dark theme example

```xml
<style name="YourDarkFilePickerTheme" parent="Theme.AppCompat.Dialog.Alert">
    <item name="android:windowBackground">#121212</item>
    <item name="android:textColorPrimary">#FFFFFF</item>
    <item name="android:textColorSecondary">#BDBDBD</item>
    <item name="colorPrimary">#BB86FC</item>
    <item name="colorAccent">#BB86FC</item>
</style>
```

Then use it:

```java
FilePickerDialog dialog = new FilePickerDialog(
        MainActivity.this,
        properties,
        R.style.YourDarkFilePickerTheme
);
```

### Important theming notes

- The theme must be a dialog-compatible theme.
- If you use a Material Components parent theme, your app should include Material Components and use a Material-compatible application theme.
- `setTitle()`, `setPositiveBtnName()`, and `setNegativeBtnName()` only change text, not the full dialog style.
- Button and checkbox colors are controlled mainly by `colorAccent` and related theme color attributes.
- For deep UI changes such as row layout, icon size, text sizes, or spacing, customize the library layout resources in your app or fork the library.
- Do not use a full-screen activity theme as the dialog theme unless you intentionally want full-screen behavior.

---

## Customizing checkbox colors

From `v10.1.3`, FilePicker supports checkbox color customization through `DialogProperties`.

This is useful when the default checkbox colors do not match your app theme, or when the unchecked checkbox blends into a white/light background.

```java
import android.graphics.Color;

DialogProperties properties = new DialogProperties();

properties.selection_mode = DialogConfigs.MULTI_MODE;
properties.selection_type = DialogConfigs.DIR_SELECT;

// Checked checkbox fill color
properties.checkbox_checked_color = Color.parseColor("#6750A4");

// Unchecked checkbox outer/background color
properties.checkbox_unchecked_color = Color.parseColor("#6750A4");

// Tick/checkmark color shown when checked
properties.checkbox_checkmark_color = Color.WHITE;

// Inner color shown when unchecked
properties.checkbox_unchecked_inner_color = Color.parseColor("#F3E8FF");

FilePickerDialog dialog = new FilePickerDialog(MainActivity.this, properties);
dialog.setTitle("Select Directory");
dialog.show();
```

### Notes

- `checkbox_checked_color` controls the selected checkbox fill color.
- `checkbox_unchecked_color` controls the unchecked checkbox outer/background color.
- `checkbox_checkmark_color` controls the tick/checkmark color.
- `checkbox_unchecked_inner_color` controls the inner unchecked color.
- If `checkbox_checked_color` is not set, FilePicker uses the existing `R.color.colorAccent` fallback.
- This works for file selection, directory selection, and file + directory selection.

## Changing dialog width and height

This section answers a common GitHub issue/question: **"Change Width and Height"**.

`FilePickerDialog` supports programmatic dialog sizing. This is useful for:

- Ultra-wide screens
- Landscape mode
- Tablets
- Foldables
- ChromeOS devices
- Custom kiosk-style layouts
- Apps that need a wider picker UI than the default Android dialog width

You should call the size method **before** `dialog.show()`.

### Set size using screen percentage

Use this when you want responsive sizing across different screen sizes.

```java
DialogProperties properties = new DialogProperties();

FilePickerDialog dialog = new FilePickerDialog(MainActivity.this, properties);

dialog.setTitle("Select File");
dialog.setPositiveBtnName("Select");
dialog.setNegativeBtnName("Cancel");

// 85% of screen width and 75% of screen height
dialog.setDialogSizeByPercent(0.85f, 0.75f);

dialog.show();
```

### Set width only and keep default height

```java
dialog.setDialogSizeByPercent(0.90f, -1f);
dialog.show();
```

### Set height only and keep default width

```java
dialog.setDialogSizeByPercent(-1f, 0.75f);
dialog.show();
```

### Use direct layout params

Use this when you want exact Android layout behavior.

```java
dialog.setDialogSize(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
);

dialog.show();
```

Required import:

```java
import android.view.ViewGroup;
```

### Use exact pixel values

```java
dialog.setDialogSize(1200, 700);
dialog.show();
```

> Pixel values are not recommended for normal apps because they may look different on different screen densities. Prefer `setDialogSizeByPercent()` for responsive UI.

### Complete width/height example

```java
DialogProperties properties = new DialogProperties();
properties.selection_mode = DialogConfigs.SINGLE_MODE;
properties.selection_type = DialogConfigs.FILE_SELECT;
properties.root = new File(DialogConfigs.DEFAULT_DIR);
properties.offset = new File(DialogConfigs.DEFAULT_DIR);
properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
properties.extensions = null;
properties.show_hidden_files = false;

FilePickerDialog dialog = new FilePickerDialog(MainActivity.this, properties);

dialog.setTitle("Select a File");
dialog.setPositiveBtnName("Select");
dialog.setNegativeBtnName("Cancel");

// Makes the dialog wider on landscape, tablet, foldable, and ultra-wide displays.
dialog.setDialogSizeByPercent(0.85f, 0.75f);

dialog.setDialogSelectionListener(files -> {
    for (String path : files) {
        // Use selected path
    }
});

dialog.show();
```

### Dialog size API

| Method | Description |
|---|---|
| `setDialogSize(int width, int height)` | Sets dialog width and height using Android layout params or exact pixel values |
| `setDialogSizeByPercent(float widthPercent, float heightPercent)` | Sets dialog width and height using screen percentage |
| `ViewGroup.LayoutParams.MATCH_PARENT` | Makes the dialog use maximum available width/height |
| `ViewGroup.LayoutParams.WRAP_CONTENT` | Keeps default content-based size |
| `-1f` in percent method | Keeps that dimension unchanged/default |

### Recommended values

| Use case | Recommended value |
|---|---|
| Normal phones | Default dialog size |
| Landscape phones | `setDialogSizeByPercent(0.90f, 0.80f)` |
| Tablets | `setDialogSizeByPercent(0.75f, 0.80f)` |
| Ultra-wide screens | `setDialogSizeByPercent(0.85f, 0.75f)` |
| Full-width picker | `setDialogSize(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)` |

### Important notes

- Call `setDialogSize()` or `setDialogSizeByPercent()` before `dialog.show()`.
- If called after `dialog.show()`, the dialog should update immediately.
- Use percentage-based sizing for responsive UI.
- Use direct layout params only when you need exact Android layout behavior.
- Avoid hardcoded pixels unless you are building for a fixed-size device.
- Dialog sizing changes the outer dialog window size, not the internal row layout.
- For deeper layout changes such as row height, icon size, padding, or text size, customize the library XML layout resources or fork the UI layer.


---

## Complete Java example

This sample uses modern `ActivityResultLauncher` APIs instead of deprecated `startActivityForResult()`.

```java
package com.example.filepickerdemo;

import static android.provider.Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.Settings;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.developer.filepicker.model.DialogConfigs;
import com.developer.filepicker.model.DialogProperties;
import com.developer.filepicker.view.FilePickerDialog;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private FilePickerDialog filePickerDialog;

    private ActivityResultLauncher<String[]> permissionLauncher;
    private ActivityResultLauncher<Intent> allFilesAccessLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setupPermissionLaunchers();
        setupFilePicker();

        Button showDialog = findViewById(R.id.show_dialog);
        showDialog.setOnClickListener(view -> openPickerSafely());
    }

    private void setupFilePicker() {
        DialogProperties properties = new DialogProperties();

        properties.selection_mode = DialogConfigs.SINGLE_MODE;
        properties.selection_type = DialogConfigs.FILE_SELECT;
        properties.root = new File(DialogConfigs.DEFAULT_DIR);
        properties.offset = new File(DialogConfigs.DEFAULT_DIR);
        properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);

        // null = show all allowed files
        properties.extensions = null;

        // false = hide dot files
        properties.show_hidden_files = false;

        filePickerDialog = new FilePickerDialog(this, properties);
        filePickerDialog.setTitle("Select a File");
        filePickerDialog.setPositiveBtnName("Select");
        filePickerDialog.setNegativeBtnName("Cancel");

        // Optional v10.1.1 feature:
        // Make the dialog wider/taller on landscape, tablet, foldable, or ultra-wide screens.
        filePickerDialog.setDialogSizeByPercent(0.85f, 0.75f);

        filePickerDialog.setDialogSelectionListener(files -> {
            for (String path : files) {
                Toast.makeText(this, "Selected: " + path, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupPermissionLaunchers() {
        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    if (hasRequiredAccess()) {
                        filePickerDialog.show();
                    } else {
                        showPermissionDeniedMessage();
                    }
                }
        );

        allFilesAccessLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (hasRequiredAccess()) {
                        filePickerDialog.show();
                    } else {
                        showPermissionDeniedMessage();
                    }
                }
        );
    }

    private void openPickerSafely() {
        if (hasRequiredAccess()) {
            filePickerDialog.show();
            return;
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            showAllFilesAccessDialog();
        } else {
            requestLegacyStoragePermissions();
        }
    }

    private boolean hasRequiredAccess() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            return Environment.isExternalStorageManager();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_EXTERNAL_STORAGE
            ) == PackageManager.PERMISSION_GRANTED;
        }

        return true;
    }

    private void requestLegacyStoragePermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissionLauncher.launch(new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE
            });
        }
    }

    private void showAllFilesAccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("Storage Permission Required")
                .setMessage("To browse all files and folders, please allow All files access for this app. Use this only if broad file access is required for your app's main feature.")
                .setPositiveButton("Open Settings", (dialog, which) -> openAllFilesAccessSettings())
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void openAllFilesAccessSettings() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            try {
                Intent intent = new Intent(
                        ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
                        Uri.parse("package:" + getPackageName())
                );
                allFilesAccessLauncher.launch(intent);
            } catch (Exception exception) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
                allFilesAccessLauncher.launch(intent);
            }
        }
    }

    private void showPermissionDeniedMessage() {
        Toast.makeText(
                this,
                "Storage permission is required to browse files.",
                Toast.LENGTH_LONG
        ).show();
    }
}
```

---

## DialogProperties reference

`DialogProperties` controls how the picker behaves.

```java
DialogProperties properties = new DialogProperties();
```

| Property | Type | Default | Description |
|---|---|---|---|
| `selection_mode` | `int` | `DialogConfigs.SINGLE_MODE` | Single or multiple selection |
| `selection_type` | `int` | `DialogConfigs.FILE_SELECT` | Select files, directories, or both |
| `root` | `File` | `DialogConfigs.DEFAULT_DIR` | Highest accessible directory for picker navigation |
| `offset` | `File` | `DialogConfigs.DEFAULT_DIR` | Initial directory opened when dialog starts |
| `error_dir` | `File` | `DialogConfigs.DEFAULT_DIR` | Fallback directory when root/offset is invalid |
| `extensions` | `String[]` | `null` | Allowed file extensions |
| `show_hidden_files` | `boolean` | `false` | Whether hidden dot-files are visible |

### Recommended defaults

```java
DialogProperties properties = new DialogProperties();
properties.selection_mode = DialogConfigs.SINGLE_MODE;
properties.selection_type = DialogConfigs.FILE_SELECT;
properties.root = new File(DialogConfigs.DEFAULT_DIR);
properties.offset = new File(DialogConfigs.DEFAULT_DIR);
properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
properties.extensions = null;
properties.show_hidden_files = false;
```

### Root, offset, and error directory rules

- `root` should be an existing readable directory.
- `offset` should be inside `root`.
- `error_dir` should be an existing readable fallback directory.
- Do not pass file paths as `root`, `offset`, or `error_dir`.
- Avoid hardcoded `/sdcard` paths in new apps.
- Prefer `DialogConfigs.DEFAULT_DIR` or `Environment.getExternalStorageDirectory()`.

---

## DialogConfigs reference

### Selection mode

```java
properties.selection_mode = DialogConfigs.SINGLE_MODE;
```

| Constant | Description |
|---|---|
| `DialogConfigs.SINGLE_MODE` | User can select only one item |
| `DialogConfigs.MULTI_MODE` | User can select multiple items |

### Selection type

```java
properties.selection_type = DialogConfigs.FILE_SELECT;
```

| Constant | Description |
|---|---|
| `DialogConfigs.FILE_SELECT` | Files only |
| `DialogConfigs.DIR_SELECT` | Directories only |
| `DialogConfigs.FILE_AND_DIR_SELECT` | Files and directories |

---

## Extension filtering

### Show all files

```java
properties.extensions = null;
```

### Show only selected extensions

```java
properties.extensions = new String[]{"pdf", "docx", "xlsx"};
```

### Dot prefix is also supported in v10

```java
properties.extensions = new String[]{".pdf", ".docx", ".xlsx"};
```

### Wildcard

```java
properties.extensions = new String[]{"*"};
```

### Best practices

- Use lowercase extensions where possible.
- Do not include MIME types here.
- Use `pdf`, not `application/pdf`.
- Directories remain visible if readable, because users need to navigate through folders.
- If `selection_type` is `DIR_SELECT`, files are not selectable.

--- 

## File size filtering

FilePicker supports file size filtering using `min_file_size` and `max_file_size`.

This feature is not limited to video files. It works with **any file type** because the picker checks the actual file size in bytes.

You can use file size filtering for:

- Videos such as `mp4`, `mkv`, `avi`, `mov`
- Documents such as `pdf`, `doc`, `docx`, `xls`, `xlsx`
- Images such as `jpg`, `jpeg`, `png`, `webp`
- Audio files such as `mp3`, `wav`, `aac`
- Archives such as `zip`, `rar`, `7z`
- Any other readable file type

File size filtering can be used in three ways:

1. Allow files below a specific size.
2. Allow files above a specific size.
3. Allow files between a minimum and maximum size.

---

### File size values are in bytes

`min_file_size` and `max_file_size` accept values in **bytes**.

```java
properties.min_file_size = 5L * 1024L * 1024L;   // 5 MB
properties.max_file_size = 50L * 1024L * 1024L;  // 50 MB
```

The `L` is important because file sizes can become large. It tells Java to calculate the value as a `long`.

---

### Disable file size filtering

By default, file size filtering is disabled.

```java
properties.min_file_size = -1L;
properties.max_file_size = -1L;
```

`-1L` means there is no limit.

---

### Select any file below 50 MB

This example allows the user to select any readable file below 50 MB.

```java
DialogProperties properties = new DialogProperties();

properties.selection_mode = DialogConfigs.SINGLE_MODE;
properties.selection_type = DialogConfigs.FILE_SELECT;

// null means all file extensions are allowed
properties.extensions = null;

// Allow files up to 50 MB
properties.max_file_size = 50L * 1024L * 1024L;

// No minimum size limit
properties.min_file_size = -1L;

FilePickerDialog dialog = new FilePickerDialog(MainActivity.this, properties);
dialog.setTitle("Select File Below 50 MB");
dialog.show();
```

---

### Select video files below 100 MB

This example allows only video files below 100 MB.

```java
DialogProperties properties = new DialogProperties();

properties.selection_mode = DialogConfigs.SINGLE_MODE;
properties.selection_type = DialogConfigs.FILE_SELECT;

// Only video files
properties.extensions = new String[]{
        "mp4",
        "mkv",
        "avi",
        "mov",
        "webm",
        "3gp"
};

// Allow videos up to 100 MB
properties.max_file_size = 100L * 1024L * 1024L;

// No minimum size limit
properties.min_file_size = -1L;

FilePickerDialog dialog = new FilePickerDialog(MainActivity.this, properties);
dialog.setTitle("Select Video Below 100 MB");
dialog.show();
```

---

### Select PDF files below 10 MB

```java
DialogProperties properties = new DialogProperties();

properties.selection_mode = DialogConfigs.SINGLE_MODE;
properties.selection_type = DialogConfigs.FILE_SELECT;

// Only PDF files
properties.extensions = new String[]{"pdf"};

// Allow PDF files up to 10 MB
properties.max_file_size = 10L * 1024L * 1024L;

FilePickerDialog dialog = new FilePickerDialog(MainActivity.this, properties);
dialog.setTitle("Select PDF Below 10 MB");
dialog.show();
```

---

### Select files larger than 20 MB

You can also allow only files larger than a specific size by using `min_file_size`.

```java
DialogProperties properties = new DialogProperties();

properties.selection_mode = DialogConfigs.SINGLE_MODE;
properties.selection_type = DialogConfigs.FILE_SELECT;

// Allow all file types
properties.extensions = null;

// Only show files larger than 20 MB
properties.min_file_size = 20L * 1024L * 1024L;

// No maximum size limit
properties.max_file_size = -1L;

FilePickerDialog dialog = new FilePickerDialog(MainActivity.this, properties);
dialog.setTitle("Select File Larger Than 20 MB");
dialog.show();
```

---

### Select files between 5 MB and 200 MB

This example allows any file type between 5 MB and 200 MB.

```java
DialogProperties properties = new DialogProperties();

properties.selection_mode = DialogConfigs.SINGLE_MODE;
properties.selection_type = DialogConfigs.FILE_SELECT;

// Allow all file types
properties.extensions = null;

// Minimum size: 5 MB
properties.min_file_size = 5L * 1024L * 1024L;

// Maximum size: 200 MB
properties.max_file_size = 200L * 1024L * 1024L;

FilePickerDialog dialog = new FilePickerDialog(MainActivity.this, properties);
dialog.setTitle("Select File Between 5 MB and 200 MB");
dialog.show();
```

---

### Select large video files between 100 MB and 2 GB

FilePicker also supports large file size limits because `min_file_size` and `max_file_size` use `long`.

```java
DialogProperties properties = new DialogProperties();

properties.selection_mode = DialogConfigs.SINGLE_MODE;
properties.selection_type = DialogConfigs.FILE_SELECT;

// Only video files
properties.extensions = new String[]{
        "mp4",
        "mkv",
        "avi",
        "mov",
        "webm"
};

// Minimum size: 100 MB
properties.min_file_size = 100L * 1024L * 1024L;

// Maximum size: 2 GB
properties.max_file_size = 2L * 1024L * 1024L * 1024L;

FilePickerDialog dialog = new FilePickerDialog(MainActivity.this, properties);
dialog.setTitle("Select Large Video File");
dialog.show();
```

---

### Helper method for converting MB to bytes

For cleaner code, you can create a helper method:

```java
private long mbToBytes(long mb) {
    return mb * 1024L * 1024L;
}
```

Usage:

```java
properties.max_file_size = mbToBytes(50);   // 50 MB
properties.min_file_size = mbToBytes(5);    // 5 MB
```

---

### Helper method for converting GB to bytes

```java
private long gbToBytes(long gb) {
    return gb * 1024L * 1024L * 1024L;
}
```

Usage:

```java
properties.max_file_size = gbToBytes(2);    // 2 GB
```

---

### Important notes

- File size values must be provided in bytes.
- Use `L` in size calculations to avoid integer overflow.
- `-1L` means no limit.
- `min_file_size` checks the minimum allowed file size.
- `max_file_size` checks the maximum allowed file size.
- File size filtering works with all file types, not only videos.
- Extension filtering and file size filtering can be combined.
- Directories are not blocked by file size filters because users still need to browse folders.
- File size filtering only applies to files.
- If both `min_file_size` and `max_file_size` are set, the file must be inside that size range.
- If `min_file_size` is greater than `max_file_size`, no file may match your filter, so validate your values before showing the dialog.
- On Android 11 and above, storage access rules still apply. File size filtering does not bypass Android scoped storage or Play Store permission policies.

---

## Single and multiple selection

### Single file selection

```java
properties.selection_mode = DialogConfigs.SINGLE_MODE;
properties.selection_type = DialogConfigs.FILE_SELECT;
```

### Multiple file selection

```java
properties.selection_mode = DialogConfigs.MULTI_MODE;
properties.selection_type = DialogConfigs.FILE_SELECT;
```

### Multiple files and folders

```java
properties.selection_mode = DialogConfigs.MULTI_MODE;
properties.selection_type = DialogConfigs.FILE_AND_DIR_SELECT;
```

---

## Selecting directories

To allow directory selection:

```java
properties.selection_type = DialogConfigs.DIR_SELECT;
```

To allow both files and directories:

```java
properties.selection_type = DialogConfigs.FILE_AND_DIR_SELECT;
```

---

## Pre-selecting files

You can mark files before opening the dialog.

```java
List<String> selectedPaths = new ArrayList<>();
selectedPaths.add("/storage/emulated/0/Download/example.pdf");

dialog.markFiles(selectedPaths);
```

Rules:

- In `SINGLE_MODE`, only the first valid path is used.
- In `MULTI_MODE`, all valid paths are used.
- Invalid paths are ignored.
- Selection type rules are respected.

---

## Handling Android 11+ all-files access

Android 11 introduced stricter scoped storage enforcement. If your app truly needs direct path access across shared storage, check All files access before showing the picker.

```java
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
    if (Environment.isExternalStorageManager()) {
        dialog.show();
    } else {
        // Redirect user to All files access settings
    }
}
```

Open settings:

```java
Intent intent = new Intent(
        Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION,
        Uri.parse("package:" + getPackageName())
);
startActivity(intent);
```

Fallback:

```java
Intent intent = new Intent(Settings.ACTION_MANAGE_ALL_FILES_ACCESS_PERMISSION);
startActivity(intent);
```

### Important warning

Only request All files access if your app's core functionality requires it. Google Play may reject apps that request this permission without a policy-approved reason.

---

## Handling Android 13+ media permissions

Android 13 introduced granular media permissions.

```xml
<uses-permission android:name="android.permission.READ_MEDIA_IMAGES" />
<uses-permission android:name="android.permission.READ_MEDIA_VIDEO" />
<uses-permission android:name="android.permission.READ_MEDIA_AUDIO" />
```

Use them based on what your app needs.

```java
if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
    permissionLauncher.launch(new String[]{
            Manifest.permission.READ_MEDIA_IMAGES,
            Manifest.permission.READ_MEDIA_VIDEO,
            Manifest.permission.READ_MEDIA_AUDIO
    });
}
```

### Important

These permissions are only for media files:

- Images
- Videos
- Audio

They do not provide complete access to:

- PDF
- ZIP
- TXT
- DOC/DOCX
- XLS/XLSX
- APK
- Arbitrary folders

For those files on Android 11+, use SAF or eligible All files access.

---

## Handling Android 14+ partial photo/video access

Android 14 allows users to grant access only to selected photos/videos.

Add this permission if your app handles visual media selection behavior on Android 14+:

```xml
<uses-permission android:name="android.permission.READ_MEDIA_VISUAL_USER_SELECTED" />
```

Important notes:

- Users may grant partial access instead of full media library access.
- Your app should not assume it can see every image or video.
- For broad file browsing, this does not replace All files access.
- For media-only apps, consider Android Photo Picker.

---

## Play Store safe integration options

### Option A: File manager/document manager app

Use this library with `MANAGE_EXTERNAL_STORAGE` only if broad file access is your main feature.

Recommended for:

- File manager
- Document manager
- Backup/restore
- Device migration
- Antivirus/file scanning
- File search/indexing

You must:

- Explain the permission clearly to users.
- Complete Play Console permission declaration.
- Maintain a privacy policy.
- Avoid accessing files in the background without user action.
- Avoid uploading files without explicit user consent.

### Option B: Normal app that only needs user-selected files

Do not use All files access.

Use:

- Storage Access Framework
- Android Photo Picker
- MediaStore

Recommended for:

- Chat attachment picker
- Profile photo picker
- Upload document feature
- Import/export one file
- Select a backup file manually

### Option C: Internal/private/distribution outside Play Store

If your APK is used internally, enterprise-side-loaded, or outside Google Play, you can use All files access depending on your distribution policy and user consent. Still explain clearly why the permission is needed.

---

## Troubleshooting

### Dialog looks too small on tablets, landscape, or ultra-wide screens

Use the dialog sizing API before showing the picker:

```java
dialog.setDialogSizeByPercent(0.85f, 0.75f);
dialog.show();
```

For full-width behavior:

```java
dialog.setDialogSize(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
);
```

### Dialog opens but folder is empty

Possible causes:

- Permission not granted.
- Android 11+ All files access not granted.
- Root path is invalid.
- Offset path is outside root.
- Folder is unreadable.
- Extension filter hides files.

Fix:

```java
properties.extensions = null;
properties.root = new File(DialogConfigs.DEFAULT_DIR);
properties.offset = new File(DialogConfigs.DEFAULT_DIR);
properties.error_dir = new File(DialogConfigs.DEFAULT_DIR);
```

### PDF/DOC/ZIP files not visible on Android 13+

`READ_MEDIA_*` permissions do not grant access to all document types. Use SAF or All files access if eligible.

### App rejected on Google Play because of `MANAGE_EXTERNAL_STORAGE`

Remove the permission unless your app qualifies under Play policy. Use SAF, MediaStore, or Android Photo Picker.

### Selected file path cannot be read

Check:

```java
File file = new File(path);
boolean exists = file.exists();
boolean readable = file.canRead();
```

On Android 11+, direct file paths may still be restricted without correct access.

### Permission dialog does not appear

Possible causes:

- Permission already denied with "Don't ask again".
- Permission is not valid for current Android version.
- Permission missing from manifest.
- You are requesting `READ_EXTERNAL_STORAGE` on Android 13+, where media permissions are required for media.
- You are trying to request `MANAGE_EXTERNAL_STORAGE` as a runtime permission. It must be granted from Settings.

### `WRITE_EXTERNAL_STORAGE` not working

`WRITE_EXTERNAL_STORAGE` is no longer useful for modern Android shared storage. It should be limited to older versions:

```xml
<uses-permission
    android:name="android.permission.WRITE_EXTERNAL_STORAGE"
    android:maxSdkVersion="28" />
```

### `requestLegacyExternalStorage` not working

`android:requestLegacyExternalStorage="true"` helps only for Android 10 legacy behavior. It does not bypass scoped storage on Android 11+ for apps targeting modern SDKs.

---

## Migration guide from v9.x to v10.1.3

### 1. Update dependency

```gradle
implementation "io.github.tutorialsandroid:filepicker:10.1.2"
```

### 2. Replace old permission code

Old code often used:

```java
ActivityCompat.requestPermissions(...)
onRequestPermissionsResult(...)
startActivityForResult(...)
```

Recommended v10 code:

```java
registerForActivityResult(new ActivityResultContracts.RequestMultiplePermissions(), ...)
registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), ...)
```

### 3. Review manifest

Change old write permission:

```xml
<uses-permission
    android:name="android.permission.WRITE_EXTERNAL_STORAGE"
    android:maxSdkVersion="28" />
```

Do not use:

```xml
<uses-permission
    android:name="android.permission.WRITE_EXTERNAL_STORAGE"
    android:maxSdkVersion="32" />
```

### 4. Decide your storage strategy

Ask yourself:

- Is my app a file manager or document manager?
- Is broad storage access the core functionality?
- Can I use SAF instead?
- Can I use MediaStore instead?
- Can I use Android Photo Picker instead?

Only use `MANAGE_EXTERNAL_STORAGE` when the answer is policy-safe.

### 5. Update dialog size usage if needed

If your old app looked too narrow on landscape, tablet, foldable, or ultra-wide screens, use the v10.1.3 sizing API:

```java
dialog.setDialogSizeByPercent(0.85f, 0.75f);
```

or:

```java
dialog.setDialogSize(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
);
```

### 6. Test on real devices

Test at least:

- Android 8 or 9
- Android 10
- Android 11 or 12
- Android 13
- Android 14 or newer

Check:

- Permission grant
- Permission deny
- "Don't ask again"
- Empty folders
- Hidden files
- Multiple selection
- Directory selection
- Extension filtering
- Back navigation
- Screen rotation
- Large folders

---

## Security and privacy recommendations

- Access files only after a clear user action.
- Do not scan the whole device silently.
- Do not upload selected files without explicit consent.
- Do not collect file names, paths, or metadata unless required.
- Do not log sensitive file paths in production.
- Handle unreadable files gracefully.
- Provide a clear privacy policy.
- Explain permissions before redirecting users to Settings.
- Request the minimum permission needed.
- Prefer SAF/Photo Picker/MediaStore where possible.

---

## FAQ

### Does this library support Android 13?

Yes. Android 13 media permissions are documented and supported. For non-media files, Android 13 still follows scoped storage restrictions.

### Does this library support Android 14?

Yes, but Android 14 partial media access means users may grant only selected photos/videos. Do not assume full media library access.

### Can I select PDF files on Android 13?

Yes, but not with only `READ_MEDIA_*` permissions. For direct path browsing of PDFs on Android 11+, your app needs eligible All files access or should use SAF.

### Can I publish an app with this library on Google Play?

Yes, but your permission usage must follow Google Play policy. The library itself is not the problem; the permissions your app declares and how you use them are what matter.

### Will Google Play approve `MANAGE_EXTERNAL_STORAGE`?

Only if your app has a policy-approved core use case and you complete the required declaration. If your app can use SAF, MediaStore, or Photo Picker instead, approval is unlikely.

### Why not use only Android Photo Picker?

Android Photo Picker is excellent for images and videos, but this library is for file/folder picking and direct path workflows.

### Why are some Android/data folders not visible?

Modern Android restricts access to many app-private and protected directories. Even All files access does not mean every protected location is freely accessible.

### Is root access required?

No.

### Can I change dialog width and height?

Yes. From v10.1.2, use:

```java
dialog.setDialogSizeByPercent(0.85f, 0.75f);
```

or:

```java
dialog.setDialogSize(
        ViewGroup.LayoutParams.MATCH_PARENT,
        ViewGroup.LayoutParams.WRAP_CONTENT
);
```

### Does this library upload files?

No. This library only provides local file/folder selection UI. Your app decides what to do with selected paths.

---

## Contributing

Contributions are welcome.

Helpful contribution areas:

- Storage Access Framework support
- URI-based picker mode
- Compose sample
- Kotlin sample
- Better Material UI
- Android 14/15 behavior testing
- Accessibility improvements
- Large-folder performance improvements
- Unit tests and instrumentation tests

Before opening a pull request:

1. Test on multiple Android versions.
2. Keep backward compatibility where possible.
3. Do not add unnecessary permissions.
4. Update README when behavior changes.
5. Explain Play Store impact if storage permissions change.

---

## License

```text
Copyright (C) 2019 FilePicker

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

   http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```
