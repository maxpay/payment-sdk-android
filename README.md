# Maxpay Payment Android SDK

The Maxpay Payment Android SDK makes it quick and easy to build a payment screen in your Android app. We provide customizable SDKFacade that can be used out-of-the-box.

## Content
* [Requirements](#requirements)
* [Installation](#installation)
* [Integration with SDK](#integration-with-sdk)

## Requirements
Android 5.0, Android studio 4.0.1, kotlin_version = "1.3.72"+

## Installation
To integrate the Maxpay Payment Android SDK, you need to perform a few basic tasks to prepare your Android Studio project:

### 1. Unzip the SDK
Unzip the SDK supplement you downloaded from our releases folder. Make sure the path to the SDK supplement aligns with the path you configure in your Gradle configuration.

### 2. Edit your project Gradle file
Add rules to your root-level build.gradle file, to include the app/libs directory:
```
allprojects {
    // ...
    repositories {
        // ...
        maven { url "file:/path/to/your/repo/m2repository/" }  // Local path to the folder into which you unzipped the SDK
    }
}
```

### 3. Edit your module Gradle file
In your module Gradle file (usually the app/build.gradle), add the implementation(...) line in your dependencies to import the SDK:
```
apply plugin: 'com.android.application'

android {
  // ...
}

dependencies {
  // ...
  implementation 'com.maxpay.android:sdk-payment:1.0.0'
  // Getting a "Could not find" error? Make sure you have added the unzipped SDK
  // location to your root build.gradle file as a local maven dependency
}
```

> **_NOTE:_** The SDK is distributed as a .m2repo, so adding the SDK to your own private Maven repository will also work.


## Integration with SDK

[How integrate SDK to your project see here](readme/INTEGRATION.md).
