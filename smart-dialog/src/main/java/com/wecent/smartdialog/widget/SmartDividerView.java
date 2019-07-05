package com.wecent.smartdialog.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.wecent.smartdialog.resource.values.SmartColor;

/**
 * desc: Alert弹框分隔线
 * author: wecent
 * date: 2018/3/29
 */
@SuppressLint("ViewConstructor")
public final class SmartDividerView extends View {

    public SmartDividerView(Context context, boolean isHorizontal) {
        super(context);
        init(isHorizontal);
    }

    private void init(boolean isHorizontal) {
        if (isHorizontal) {
            setHorizontal();
        } else {
            setVertical();
        }
        setBackgroundColor(SmartColor.divider);
    }

    /**
     * 将分隔线设置为水平分隔
     */
    private void setHorizontal() {
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
    }

    /**
     * 将分隔线设置为垂直分隔
     */
    private void setVertical() {
        setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));

    }
}
