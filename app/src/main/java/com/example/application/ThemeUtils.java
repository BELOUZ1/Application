package com.example.application;

import android.app.Activity;
import android.content.Intent;

public class ThemeUtils {

    private static int cTheme;

    public final static int BLACK = 0;

    public final static int BLUE = 1;

    public static void changeToTheme(Activity activity, int theme)
    {
        cTheme = theme;
        activity.finish();
        activity.startActivity(new Intent(activity, activity.getClass()));
    }

    public static void onActivityCreateSetTheme(Activity activity)

    {

        switch (cTheme)

        {

            default:
                activity.setTheme(R.style.nightModeSwitch);
                break;
            case BLACK:
                activity.setTheme(R.style.nightModeSwitch);
                break;
            case BLUE:
                activity.setTheme(R.style.BlueTheme);
                break;

        }

    }


}
