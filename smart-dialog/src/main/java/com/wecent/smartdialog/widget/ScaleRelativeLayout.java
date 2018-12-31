package com.wecent.smartdialog.widget;

import android.content.Context;
import android.widget.RelativeLayout;

import com.wecent.smartdialog.scale.ScaleLayoutConfig;


/**
 * Created by wecent on 2018/6/13.
 */
public class ScaleRelativeLayout extends RelativeLayout {

    public ScaleRelativeLayout(Context context) {
        super(context);
        ScaleLayoutConfig.init(context.getApplicationContext());
    }
}
