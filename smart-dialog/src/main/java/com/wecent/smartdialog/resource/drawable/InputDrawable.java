package com.wecent.smartdialog.resource.drawable;

import android.graphics.drawable.GradientDrawable;

/**
 * desc: 输入框背景
 * author: wecent
 * date: 2018/3/29
 */
public class InputDrawable extends GradientDrawable {

    public InputDrawable(int strokeWidth, int strokeColor, int backgroundColor, int radius) {
        setColor(backgroundColor);//内部填充颜色
        setStroke(strokeWidth, strokeColor);//边框宽度,边框颜色
        setCornerRadius(radius);
    }

}
