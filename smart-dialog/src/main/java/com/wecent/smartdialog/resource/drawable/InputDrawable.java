package com.wecent.smartdialog.resource.drawable;

import android.graphics.drawable.GradientDrawable;

/**
 * 输入框背景
 * Created by wecent on 2017/3/31.
 */

public class InputDrawable extends GradientDrawable {

    public InputDrawable(int strokeWidth, int strokeColor, int backgroundColor, int radius) {
        setColor(backgroundColor);//内部填充颜色
        setStroke(strokeWidth, strokeColor);//边框宽度,边框颜色
        setCornerRadius(radius);
    }

}
