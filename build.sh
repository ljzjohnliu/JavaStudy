#!/bin/sh
./gradlew clean
./gradlew :app:assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
#事件分发
#adb shell am start -n com.study.android/com.study.android.event.TestEventActivity2
#Handler
#adb shell am start -n com.study.android/com.study.android.handler.HandlerActivity2
#Handler Leak
#adb shell am start -n com.study.android/com.study.android.handler.LeakHandlerActivity
#ANRActivity
#adb shell am start -n com.study.android/com.study.android.anr.ANRActivity
#ThreadCommunicateActivity
#adb shell am start -n com.study.android/com.study.android.communicate.ThreadCommunicateActivity
#Remote Process
adb shell am start -n com.study.android/com.study.android.communicate.RemoteActivity
#验证Fragment切换生命周期
adb shell am start -n com.study.android/com.study.android.testfrag.MainActivity