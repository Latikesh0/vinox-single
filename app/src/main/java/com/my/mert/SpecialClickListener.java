package com.my.mert;

import android.view.View;

/**
 * Separate OnClick listener containing default base code.
 * Executes default logic first, then calls whatever custom code is passed in.
 */
public class SpecialClickListener implements View.OnClickListener {

    private final MainActivity activity;
    private final View.OnClickListener customListener;

    public SpecialClickListener(MainActivity activity, View.OnClickListener customListener) {
        this.activity = activity;
        this.customListener = customListener;
    }

    @Override
    public void onClick(View v) {
        // Execute logic ONLY if unlocked (duple == "0")
        if (activity != null && "0".equals(activity.getDuple())) {
            activity.setDuple("1"); // Lock until equal button is clicked again
            if (customListener != null) {
                customListener.onClick(v);
            }
        }
    }
}
