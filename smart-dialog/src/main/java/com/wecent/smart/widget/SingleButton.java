package com.wecent.smart.widget;

import android.content.Context;
import android.os.Build;
import android.view.View;

import com.wecent.smart.SmartParams;
import com.wecent.smart.params.ButtonParams;
import com.wecent.smart.resource.drawable.SelectorBtn;

/**
 * 对话框单个按钮的视图
 * Created by wecent on 2017/3/30.
 *
 * @deprecated
 */
final class SingleButton extends ScaleTextView implements Controller.OnClickListener {
    private SmartParams mSmartParams;
    private ButtonParams mButtonParams;

    public SingleButton(Context context, SmartParams params) {
        super(context);
        init(params);
    }

    private void init(SmartParams params) {
        mSmartParams = params;
        mButtonParams = params.negativeParams != null ? params.negativeParams : params
                .positiveParams;

        setTextSize(mButtonParams.textSize);
        setHeight(mButtonParams.height);
        handleStyle();

        //如果取消按钮没有背景色，则使用默认色
        int backgroundColor = mButtonParams.backgroundColor != 0
                ? mButtonParams.backgroundColor : params.dialogParams.backgroundColor;

        int radius = params.dialogParams.radius;
        SelectorBtn selectorBtn = new SelectorBtn(backgroundColor
                , mButtonParams.backgroundColorPress != 0
                ? mButtonParams.backgroundColorPress : params.dialogParams.backgroundColorPress
                , 0, 0, radius, radius);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(selectorBtn);
        } else {
            setBackgroundDrawable(selectorBtn);
        }
    }

    private void handleStyle() {
        setText(mButtonParams.text);
        setEnabled(!mButtonParams.disable);
        //禁用按钮则改变文字颜色
        setTextColor(mButtonParams.disable ? mButtonParams.textColorDisable : mButtonParams.textColor);
    }

    public void regOnClickListener(OnClickListener onClickListener) {
        setOnClickListener(onClickListener);
    }

    public void refreshText() {
        if (mButtonParams == null) return;
        post(new Runnable() {
            @Override
            public void run() {
                handleStyle();
            }
        });
    }

    @Override
    public void onClick(View view, int which) {
        if (which == Controller.BUTTON_NEGATIVE) {
            if (mSmartParams.clickNegativeListener != null) {
                mSmartParams.clickNegativeListener.onClick(this);
            }
        } else if (which == Controller.BUTTON_POSITIVE) {
            if (mSmartParams.clickPositiveListener != null) {
                mSmartParams.clickPositiveListener.onClick(this);
            }
        }
    }
}
