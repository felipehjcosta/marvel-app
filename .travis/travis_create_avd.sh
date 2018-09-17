#!/usr/bin/env bash

# Creates and starts an emulator.
# Avoid the tempation to skip the boot animation. CI scripts use that to figure out when the device is ready.
# The emulator is started in the background. This is to avoid holding up the build until we need it.

$HOME/android-sdk/tools/bin/avdmanager create avd -n test -k "system-images;android-21;default;armeabi-v7a"
emulator -avd test -no-skin -no-audio -no-window -gpu off &

exit 0