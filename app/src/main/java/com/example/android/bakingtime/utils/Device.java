package com.example.android.bakingtime.utils;

import android.content.Context;

import com.example.android.bakingtime.R;

public class Device {

    public static boolean isLandscape(Context context) {
        return context
                .getResources()
                .getBoolean(R.bool.is_orientation_landscape);
    }

    public static boolean isTablet(Context context) {
        return context
                .getResources()
                .getBoolean(R.bool.is_tablet);
    }
}
