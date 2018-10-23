package com.wecent.smart.widget;

import android.content.Context;
import android.widget.RelativeLayout;

import com.wecent.smart.scale.ScaleLayoutConfig;


/**
 * Created by wecent on 2018/6/13.
 */
class ScaleRelativeLayout extends RelativeLayout {

    public ScaleRelativeLayout(Context context) {
        super(context);
        ScaleLayoutConfig.init(context.getApplicationContext());
    }
}
