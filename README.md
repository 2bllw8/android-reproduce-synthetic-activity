# Activity issue example

If an app uses permission `android.permission.FOREGROUND_SERVICE` and has an
activity that handles `android.intent.category.LAUNCHER`, disabling said activity,
will enable the synthetic `android.app.AppDetailsActivity`, making impossible to hide the app icon
from the launcher.

This doesn't happen if either `android:enabled="false"` is set for the activity or
the `android.permission.FOREGROUND_SERVICE` permission is removed.

Test:
1. Compile the app and open the app, click the button to enable/disable the launcher activity.
   At the top there will be shown a list of available launcher activities (from
   `LauncherApps#getActivityList`).
2. Observe how when the activity is disabled, `android.app.AppDetailsActivity` is listed as a
   launch-able activity (this is reflected in the launcher, which open the settings page if the
   icon is clicked).
3. Now modify the manifest to set the `android:enabled="false"` attribute to the `MainActivity`
   and launch the `TestActivity` manually.
4. Toggle the `MainActivity` status with the button as before and notice how when the activity
   is disabled, nothing is listed at the top (this is reflected in the launcher, in fact the
   icon now correctly disappears).
5. Now remove `<uses-permission android:name="android.permission.FOREGROUND_SERVICE" />` from
   the manifest and observe how `android.app.AppDetailsActivity` never appears regardless
   of the `android:enabled` value.
