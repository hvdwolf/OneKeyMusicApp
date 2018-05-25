@echo off

REM check permissions: running as Administrator?
fsutil dirty query C: >nul 2>&1
if %errorLevel% NEQ 0 (
	echo.
	echo You did not start this script in an Administrator command box.
	echo Open an Administrator command prompt and restart this script.
	echo.
	GOTO END
)

REM check for ip-address
if "%1"=="" (
	echo.
	echo no argument given. I need the ip-address. Restart the script with "install.bat ip-address".
	echo.
	GOTO END
)

REM Now do the rooting actions
adb kill-server
adb connect %1
timeout 1 > NUL
adb root
timeout 1 > NUL
adb connect $1

timeout 3 > NUL

REM Push update APK file to sdcard then /system/app
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

echo.
echo "You need to reboot your device now."
echo.

:END

