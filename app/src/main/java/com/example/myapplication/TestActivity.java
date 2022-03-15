package com.example.myapplication;

import android.app.Activity;
import android.content.ComponentName;
import android.content.pm.LauncherApps;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Process;
import android.widget.Button;
import android.widget.TextView;

import java.util.stream.Collectors;

public final class TestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity);

        final Button btn = findViewById(android.R.id.button1);
        final TextView textView = findViewById(android.R.id.text1);

        textView.setText(getActivitiesList());
        btn.setText(isEnabled() ? "Enabled" : "Disabled");

        btn.setOnClickListener(v -> {
            final boolean newState = !isEnabled();
            setEnabled(newState);

            btn.setText(newState ? "Enabled" : "Disabled");
            textView.setText(getActivitiesList());
        });
    }

    private String getActivitiesList() {
        return getSystemService(LauncherApps.class)
                .getActivityList(getPackageName(), Process.myUserHandle())
                .stream()
                .map(info -> info.getComponentName().toString())
                .collect(Collectors.joining("\n"));
    }

    public boolean isEnabled() {
        final PackageManager packageManager = getPackageManager();
        final int status = packageManager.getComponentEnabledSetting(
                new ComponentName(this, MainActivity.class));
        return PackageManager.COMPONENT_ENABLED_STATE_DISABLED > status;
    }

    public void setEnabled(boolean enabled) {
        final PackageManager packageManager = getPackageManager();
        final int newStatus = enabled
                ? PackageManager.COMPONENT_ENABLED_STATE_ENABLED
                : PackageManager.COMPONENT_ENABLED_STATE_DISABLED;
        packageManager.setComponentEnabledSetting(
                new ComponentName(this, MainActivity.class),
                newStatus,
                PackageManager.DONT_KILL_APP);
    }
}
