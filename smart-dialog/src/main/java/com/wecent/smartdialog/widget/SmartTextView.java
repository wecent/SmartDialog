package com.wecent.smartdialog.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.TypedValue;
import android.view.Gravity;

/**
 * desc: Alert弹框文本
 * author: wecent
 * date: 2018/3/29
 */
public class SmartTextView extends AppCompatTextView {
    public SmartTextView(Context context) {
        super(context);
        config();
    }

    private void config() {
        setGravity(Gravity.CENTER);
    }

    @Override
    public void setHeight(int pixels) {
        int dimenHeight = pixels;
        super.setHeight(dimenHeight);
    }

    @Override
    public void setTextSize(float size) {
        int dimenTextSize = (int) size;
        setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenTextSize);
    }

    public void setAutoPadding(int left, int top, int right, int bottom) {
        int dimenLeft = left;
        int dimenTop = top;
        int dimenRight = right;
        int dimenBottom = bottom;
        super.setPadding(dimenLeft, dimenTop, dimenRight, dimenBottom);
    }
}
