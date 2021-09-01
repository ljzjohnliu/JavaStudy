#!/bin/sh
./gradlew clean
./gradlew :app:assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
adb shell am start -n com.study.java/com.study.java.event.TestEventActivity2
