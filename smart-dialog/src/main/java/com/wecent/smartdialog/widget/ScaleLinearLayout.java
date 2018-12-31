package com.wecent.smartdialog.widget;

import android.content.Context;
import android.widget.LinearLayout;

import com.wecent.smartdialog.scale.ScaleLayoutConfig;

/**
 * Created by wecent on 2017/3/29.
 */
public class ScaleLinearLayout extends LinearLayout {

    public ScaleLinearLayout(Context context) {
        super(context);
        ScaleLayoutConfig.init(context.getApplicationContext());
    }
}
