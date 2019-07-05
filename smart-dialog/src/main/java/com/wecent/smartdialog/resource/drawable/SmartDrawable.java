package com.wecent.smartdialog.resource.drawable;

import android.graphics.drawable.ShapeDrawable;

/**
 * desc: 圆角图片
 * author: wecent
 * date: 2018/3/29
 */
public class SmartDrawable extends ShapeDrawable {

    public SmartDrawable(int backgroundColor, int radius) {
        this(backgroundColor, radius, radius, radius, radius);
    }

    public SmartDrawable(int backgroundColor, int leftTopRadius, int rightTopRadius, int rightBottomRadius, int leftBottomRadius) {
        getPaint().setColor(backgroundColor);//内部填充颜色
        //圆角半径
        setShape(DrawableHelper.getRoundRectShape(leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius));
    }

}
