package com.wecent.smart.widget;

import android.content.Context;
import android.view.View;
import android.widget.LinearLayout;

import com.wecent.smart.resource.values.SmartColor;

/**
 * 分隔线，默认垂直
 * Created by wecent on 2017/3/30.
 */
final class SmartDividerView extends View {
    public SmartDividerView(Context context) {
        super(context);
        init();
    }

    private void init() {
        setLayoutParams(new LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT));
        setBackgroundColor(SmartColor.divider);
    }

    /**
     * 将分隔线设置为水平分隔
     */
    public void setVertical() {
        setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1));
    }
}
