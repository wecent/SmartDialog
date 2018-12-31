package com.wecent.smartdialog.widget;

import android.content.Context;
import android.util.TypedValue;
import android.view.Gravity;

import com.wecent.smartdialog.scale.ScaleHelper;

/**
 * Created by wecent on 2017/3/31.
 */
public class ScaleEditText extends android.support.v7.widget.AppCompatEditText {
    public ScaleEditText(Context context) {
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
        int dimenTextSize = ScaleHelper.scaleValue((int) size);
        setTextSize(TypedValue.COMPLEX_UNIT_PX, dimenTextSize);
    }

    @Override
    public void setHeight(int pixels) {
        int dimenHeight = ScaleHelper.scaleValue(pixels);
        super.setHeight(dimenHeight);
    }

    public void setAutoPadding(int left, int top, int right, int bottom) {
        int dimenLeft = ScaleHelper.scaleValue(left);
        int dimenTop = ScaleHelper.scaleValue(top);
        int dimenRight = ScaleHelper.scaleValue(right);
        int dimenBottom = ScaleHelper.scaleValue(bottom);
        super.setPadding(dimenLeft, dimenTop, dimenRight, dimenBottom);
    }
}
