package com.app.ssoft.panache.Utils;

import android.content.Context;
import android.graphics.Typeface;
import android.widget.TextView;

/**
 * Created by shekharshrivastava on 06/04/18.
 */

public class Utils {

    public static void setTextFont (Context context, String fontName, TextView textView) {
        Typeface face = Typeface.createFromAsset(context.getAssets(),
                "fonts/"+ fontName);
        textView.setTypeface(face);
    }
}
