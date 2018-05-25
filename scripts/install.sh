#!/bin/sh

# ipaddress given?
if [ "$1" = "" ]
then
	echo "Provide IP Address to connect to for adb"
	echo "for example:  install 10.0.0.52"
	exit
fi
set -x

# no old "left over" connections
adb kill-server

adb connect $1
sleep 1
adb root
sleep 1
adb connect $1
sleep 1

# Push update APK file to sdcard then /system/app
adb push OneKeyMusic.apk /sdcard/
adb shell "am force-stop com.syu.music"
adb shell "sleep 1"
adb shell "rm -rf /data/dalvik-cache/profiles/com.syu.music"
adb shell "mount -o remount,rw /system"
adb shell "mv /system/app/JY-1-C9-MUSIC-V1.0/JY-1-C9-MUSIC-V1.0.apk /system/app/JY-1-C9-MUSIC-V1.0/JJY-1-C9-MUSIC-V1.0.apk.old"
adb shell "mkdir -p /system/app/OneKeyMusic"
adb shell "chmod 0755 /system/app/OneKeyMusic"
adb shell "cp /sdcard/OneKeyMusic.apk /system/app/OneKeyMusic"
adb shell "chmod 644 /system/app/OneKeyMusic/OneKeyMusic.apk"

adb kill-server

echo "######### PLEASE REBOOT YOUR JOYING NOW ###########"

