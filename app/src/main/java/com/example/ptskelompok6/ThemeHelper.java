package com.example.ptskelompok6;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.widget.Button;

import androidx.appcompat.app.AppCompatDelegate;

public class ThemeHelper {

    private static final String PREF_NAME = "theme_pref";
    private static final String KEY_NIGHT_MODE = "night_mode";

    public static void applyTheme(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        int mode = pref.getInt(KEY_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM);
        AppCompatDelegate.setDefaultNightMode(mode);
    }

    public static boolean isDarkMode(Context context) {
        int currentNightMode = context.getResources().getConfiguration().uiMode & Configuration.UI_MODE_NIGHT_MASK;
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES;
    }

    public static void toggleTheme(Context context) {
        boolean isDark = isDarkMode(context);
        int newMode = isDark ? AppCompatDelegate.MODE_NIGHT_NO : AppCompatDelegate.MODE_NIGHT_YES;

        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        pref.edit().putInt(KEY_NIGHT_MODE, newMode).apply();

        AppCompatDelegate.setDefaultNightMode(newMode);
    }

    public static void updateToggleIcon(Button button, Context context) {
        if (button == null) return;
        if (isDarkMode(context)) {
            button.setText(R.string.emoji_sun);
        } else {
            button.setText(R.string.emoji_moon);
        }
    }
}
