# Maya – Interest Tracking starter

This repository previously contained only a README and a released APK; it did not contain the original Maya Android source. This branch therefore adds a **new, minimal Android starter app**, not a modification of the released Maya APK.

## Interest tracking included
- Two repeatable switches: **Paid → Free** and **Free → Paid**.
- Every switch-on tap adds +1 to that option's local counter.
- Counts persist on the device using Android SharedPreferences.
- The counters are local to this installation. No user identity or personal data is collected, and no central/online aggregation is configured.

## Build
Open this repository in Android Studio, or run:

```bash
gradle assembleDebug
```

The GitHub Actions workflow builds the debug APK on pushes and pull requests.

## Important limitation
This starter app does not contain the features or source code of the existing third-party Maya APK (version 5.2.64). It is a foundation for implementing the requested interest-tracking feature once the original app source is available.
