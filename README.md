# 🎮 HitKlack on KorGE
[![Kotlin](https://img.shields.io/badge/Kotlin-1.5.0-blue.svg?style=flat&logo=kotlin&logoColor=white)](http://kotlinlang.org)
[![KorGE](https://img.shields.io/badge/KorGE-2.1.1.1-836DAC.svg)](https://korge.soywiz.com/)
[![BCH compliance](https://bettercodehub.com/edge/badge/TobseF/HitKlack?branch=master)](https://bettercodehub.com/)

[![Screenshot](https://tobsef.github.io/HitKlack/images/hitklack_console_and_app.png)](https://tobsef.github.io/HitKlack/webstart/index.html)


### [🌍 Official Website](https://tobsef.github.io/HitKlack/)

### [🕹 Instant Play - Web](https://tobsef.github.io/HitKlack/webstart/index.html)

This is a work in progress game remake, just for fun. It's based on a German console, the Hit Klack from Mephisto. It
runs on multiple platforms: Android, IOs, Windows, Linux, Mac and Web. For me it's a way to test the latest features
of [Kotlin 1.5](https://kotlinlang.org/). It uses the [KorGE](https://korge.soywiz.com/) - Modern Multiplatform Game
Engine for Kotlin.

You can find my older version based on the [LibGDX](https://libgdx.badlogicgames.com/)
framework [here](https://github.com/TobseF/HitKlack_LibGDX).

## 💿 Downloads

📦 [Windows exe - One Klick Run](https://github.com/TobseF/HitKlack/releases/download/v0.2-alpha/HitKlack_v0.2-aplha_win.zip)  
📦 [Java - Windows, Linux, Max](https://github.com/TobseF/HitKlack/releases/download/v0.2-alpha/HitKlack_v0.2-alpha_jvm.zip)  
🌍 [Web App - Download](https://github.com/TobseF/HitKlack/releases/download/v0.2-alpha/HitKlack_v0.2-aplha_web.zip)  
📱 [Android - apk](https://github.com/TobseF/HitKlack/releases/download/v0.2-alpha/HitKlack_v0.2-alpha.apk)

## Build & Run

To build and run you need at least **Gradle 5.5**:

Check that Gradle is running with a `1.8 JVM`:   
In IntelliJ _Settings/Build, Execution, Deployment > Build Tools/Gradle > **Gradle JVM**_

For _Windows_, change all the `./gradlew` for `gradlew.bat`.

The inital setup is bases on the [korge-hello-world template](https://github.com/korlibs/korge-hello-world).

## Compiling for the JVM (Desktop)

Inside IntelliJ you can go to the `src/commonMain/kotlin/main.kt` file and press the green ▶️ icon
that appears to the left of the `suspend fun main()` line.

Using gradle tasks on the terminal:

```bash
./gradlew runJvm                    # Runs the program
./gradlew packageJvmFatJar          # Creates a FAT Jar with the program
./gradlew packageJvmFatJarProguard  # Creates a FAT Jar with the program and applies Proguard to reduce the size
```

Fat JARs are stored in the `/build/libs` folder.

## Compiling for the Web

Using gradle tasks on the terminal:

```bash
./gradlew jsWeb                     # Outputs to /build/web
./gradlew jsWebMin                  # Outputs to /build/web-min (applying Dead Code Elimination)
./gradlew jsWebMinWebpack           # Outputs to /build/web-min-webpack (minimizing and grouping into a single bundle.js file)
./gradlew runJs                     # Outputs to /build/web, creates a small http server and opens a browser
```

You can use any HTTP server to serve the files in your browser.
For example using: `npm -g install http-server` and then executing `hs build/web`.

You can also use `./gradlew -t jsWeb` to continuously building the JS sources and running `hs build/web` in another terminal.
Here you can find a `testJs.sh` script doing exactly this for convenience.

You can run your tests using Node.JS by calling `jsTest` or in a headless chrome with `jsTestChrome`.

## Compiling for Native Desktop (Windows, Linux and macOS)

Using gradle tasks on the terminal:

```bash
./gradlew linkMainDebugExecutableMacosX64         # Outputs to /build/bin/macosX64/mainDebugExecutable/main.kexe
./gradlew linkMainDebugExecutableLinuxX64         # Outputs to /build/bin/linuxX64/mainDebugExecutable/main.kexe
./gradlew linkMainDebugExecutableMingwX64         # Outputs to /build/bin/mingwX64/mainDebugExecutable/main.exe
```

Note that windows executables doesn't have icons bundled.
You can use [ResourceHacker](http://www.angusj.com/resourcehacker/) to add an icon to the executable for the moment.
Later this will be done automatically.

### Cross-Compiling for Linux/Windows

If you have docker installed, you can generate native executables for linux and windows
using the cross-compiling gradle wrappers:

```bash
./gradlew_linux linkMainDebugExecutableLinuxX64   # Outputs to /build/web
./gradlew_win   linkMainDebugExecutableMingwX64   # Outputs to /build/web
```

### Generating MacOS `.app`

```bash
./gradlew packageMacosX64AppDebug             # Outputs to /build/unnamed-debug.app
```

You can change `Debug` for `Release` in all the tasks to generate Release executables.

You can use the `strip` tool from your toolchain (or in the case of windows found in the ``~/.konan` toolchain)
to further reduce Debug and Release executables size by removing debug information (in some cases this will shrink the EXE size by 50%).

In windows this exe is at: `%USERPROFILE%\.konan\dependencies\msys2-mingw-w64-x86_64-gcc-7.3.0-clang-llvm-lld-6.0.1\bin\strip.exe`.

### Linux notes

Since linux doesn't provide standard multimedia libraries out of the box,
you will need to have installed the following packages: `freeglut3-dev` and `libopenal-dev`.

In ubuntu you can use `apt-get`: `sudo apt-get -y install freeglut3-dev libopenal-dev`.

## Compiling for Android

You will need to have installed the Android SDK in the default path for your operating system
or to provide the `ANDROID_SDK` environment variable. The easiest way is to install Android Studio.

Using gradle tasks on the terminal:

### Native Android (JVM)

```bash
./gradlew installAndroidDebug             # Installs an APK in all the connected devices
./gradlew runAndroidEmulatorDebug         # Runs the application in an emulator
```

Triggering these tasks, it generates a separate android project into `build/platforms/android`.
You can open it in `Android Studio` for debugging and additional tasks. The KorGE plugin just
delegates gradle tasks to that gradle project.

### Apache Cordova (JS)

```bash
./gradlew compileCordovaAndroid           # Just compiles cordova from Android
./gradlew runCordovaAndroid               # Runs the application (dce'd, minimized and webpacked) in an Android device
./gradlew runCordovaAndroidNoMinimized    # Runs the application in Android without minimizing (so you can use `chrome://inspect` to debug the application easier)
```



## Compiling for iOS

You will need XCode (minimal spport version 11.0) and to download the iOS SDKs using Xcode.

Using gradle tasks on the terminal:

### Native iOS (Kotlin/Native) + Objective-C

Note that the necessary bridges are built using Objective-C instead of Swift, so the application
won't include Swift's runtime.

```bash
./gradlew iosBuildSimulatorDebug          # Creates an APP file
./gradlew iosInstallSimulatorDebug        # Installs an APP file in the simulator
./gradlew iosRunSimulatorDebug            # Runs the APP in the simulator

```

These tasks generate a xcode project in `build/platforms/ios`, so you can also open the project
with XCode and do additional tasks there.

It uses [XCodeGen](https://github.com/yonaskolb/XcodeGen) for the project generation
and [ios-deploy](https://github.com/ios-control/ios-deploy) for deploying to real devices.

### Apache Cordova (JS)

```bash
./gradlew compileCordovaIos               # Just compiles cordova from iOS
./gradlew runCordovaIos                   # Runs the application (dce'd, minimized and webpacked) in an iOS device
./gradlew runCordovaIosNoMinimized        # Runs the application in iOS without minimizing (so you can use Safari on macOS to debug the application easier)
```

