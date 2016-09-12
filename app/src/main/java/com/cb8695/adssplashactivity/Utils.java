package com.cb8695.adssplashactivity;

import android.content.res.Resources;
import android.util.TypedValue;

/**
 * Created by lqynydyxf on 16/9/9.
 */

public class Utils {
    public static float dp2px(Resources res, float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, res.getDisplayMetrics());

    }

    public static float sp2px(Resources res, float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, res.getDisplayMetrics());
    }

    public static int dip2px(Resources res, float dpValue) {
        final float scale = res.getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
