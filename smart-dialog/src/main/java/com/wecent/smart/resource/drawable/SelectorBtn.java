package com.wecent.smart.resource.drawable;

import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;

/**
 * 按钮的背景，有点击效果
 * Created by wecent on 2017/3/30.
 */

public class SelectorBtn extends StateListDrawable {

    public SelectorBtn(int backgroundColor, int backgroundColorPress, int leftTopRadius
            , int rightTopRadius, int rightBottomRadius, int leftBottomRadius) {
        //按下
        ShapeDrawable drawablePress = new ShapeDrawable(DrawableHelper.getRoundRectShape(
                leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius));
        drawablePress.getPaint().setColor(backgroundColorPress);
        //默认
        ShapeDrawable defaultDrawable = new ShapeDrawable(DrawableHelper.getRoundRectShape(
                leftTopRadius, rightTopRadius, rightBottomRadius, leftBottomRadius));
        defaultDrawable.getPaint().setColor(backgroundColor);

        addState(new int[]{android.R.attr.state_pressed}, drawablePress);
        addState(new int[]{-android.R.attr.state_pressed}, defaultDrawable);
    }
}
