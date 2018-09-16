#!/usr/bin/env bash

# Creates and starts an emulator.
# Avoid the tempation to skip the boot animation. CI scripts use that to figure out when the device is ready.
# The emulator is started in the background. This is to avoid holding up the build until we need it.

android-update-sdk --components=sys-img-v7a-android-21 --accept-licenses='android-sdk-license-[0-9a-f]{8}'
echo no | android create avd --force -n test -t android-21 --abi armeabi-v7a
emulator -avd test -no-skin -no-audio -no-window -gpu off &

exit 0