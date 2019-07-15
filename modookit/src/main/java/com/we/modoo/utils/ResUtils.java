package com.we.modoo.utils;

import android.content.Context;

public class ResUtils {
    public static int getLayoutId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "layout",
                paramContext.getPackageName());
    }

    public static int getStringId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString, "string",
                paramContext.getPackageName());
    }

    public static String getString(Context paramContext, String paramString) {
        try {
            int id = getStringId(paramContext, paramString);
            return paramContext.getResources().getString(id);
        } catch (Exception e) {
            return "";
        }

    }

    public static int getDrawableId(Context paramContext, String paramString) {
        return paramContext.getResources().getIdentifier(paramString,
                "drawable", paramContext.getPackageName());
    }
}
