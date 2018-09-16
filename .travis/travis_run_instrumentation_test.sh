#!/usr/bin/env bash

# We can build this part of the instrumentation tests before we need the emulator
./gradlew assembleAndroidTest --console=plain

# Now we absolutely need the emulator, so we check to see if we are on travis,
# and then we wait for it to start.
which android-wait-for-emulator > /dev/null

if [ "$?" -eq 0 ]; then
  echo "Waiting for emulator..."
  android-wait-for-emulator
  adb devices
  adb shell input keyevent 82 &
fi

# Run the instrumentation tests
./gradlew connectedAndroidTest --console=plain