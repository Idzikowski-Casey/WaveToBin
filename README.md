## WaveToBin

This is a small application that allows user to upload a WAV file from device and extract the "data" section of the byte array, and download it as a pure PCM file.

There are some drawbacks of this approach including loss of format metadata - Number of channels, sample rate, etc. These need to be tracked externally by the user. 

I am currently using the desktop app to create PCM files from WAV files for another side project where PCM files will be part of the repository ease only. 


This is a Kotlin Multiplatform project targeting Android, iOS, Desktop.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html)…