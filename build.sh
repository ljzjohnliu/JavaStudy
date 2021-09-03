#!/bin/sh
./gradlew clean
./gradlew :app:assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
#事件分发
#adb shell am start -n com.study.java/com.study.java.event.TestEventActivity2
#Handler
adb shell am start -n com.study.java/com.study.java.handler.HandlerActivity2
