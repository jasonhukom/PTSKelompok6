package com.example.ptskelompok6;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FontHelper {

    private static final String PREF_NAME = "font_pref";
    private static final String KEY_FONT = "selected_font";

    public static void saveFont(Context context, String fontName) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        pref.edit().putString(KEY_FONT, fontName).apply();
    }

    public static String getFont(Context context) {
        SharedPreferences pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE);
        return pref.getString(KEY_FONT, "Monospace");
    }

    public static Typeface getTypeface(String fontName) {
        switch (fontName) {
            case "Serif":
                return Typeface.SERIF;
            case "Cursive":
                return Typeface.create("cursive", Typeface.NORMAL);
            case "Sans-Serif":
                return Typeface.SANS_SERIF;
            case "Monospace":
            default:
                return Typeface.MONOSPACE;
        }
    }

    public static void applyFontToActivity(Activity activity) {
        if (activity == null) return;
        String fontName = getFont(activity);
        Typeface typeface = getTypeface(fontName);
        View rootView = activity.findViewById(android.R.id.content);
        if (rootView != null) {
            applyFontToViewHierarchy(rootView, typeface);
        }
    }

    private static void applyFontToViewHierarchy(View view, Typeface typeface) {
        if (view instanceof TextView) {
            TextView tv = (TextView) view;
            int style = tv.getTypeface() != null ? tv.getTypeface().getStyle() : Typeface.NORMAL;
            tv.setTypeface(typeface, style);
        }

        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            for (int i = 0; i < vg.getChildCount(); i++) {
                applyFontToViewHierarchy(vg.getChildAt(i), typeface);
            }
        }
    }
}
