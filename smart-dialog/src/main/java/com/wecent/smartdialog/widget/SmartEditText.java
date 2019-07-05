package com.wecent.smartdialog.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.TypedValue;
import android.view.Gravity;

/**
 * desc: Alert弹框文本输入框
 * author: wecent
 * date: 2018/3/29
 */
public class SmartEditText extends AppCompatEditText {
    public SmartEditText(Context context) {
        super(context);
        config();
    }

    private void config() {
        requestFocus();
        setFocusable(true);
        setFocusableInTouchMode(true);
        setGravity(Gravity.TOP | Gravity.LEFT);
    }

    @Override
    public void setTextSize(float size) {
        int dimenTextSize = (int) size;
        setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenTextSize);
    }

    @Override
    public void setHeight(int pixels) {
        int dimenHeight = pixels;
        super.setHeight(dimenHeight);
    }

    public void setAutoPadding(int left, int top, int right, int bottom) {
        int dimenLeft = left;
        int dimenTop = top;
        int dimenRight = right;
        int dimenBottom = bottom;
        super.setPadding(dimenLeft, dimenTop, dimenRight, dimenBottom);
    }
}
