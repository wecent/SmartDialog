package com.wecent.smart.widget;

import android.content.Context;
import android.os.Build;

import com.wecent.smart.SmartParams;
import com.wecent.smart.params.ButtonParams;
import com.wecent.smart.params.DialogParams;
import com.wecent.smart.params.MessageParams;
import com.wecent.smart.params.TitleParams;
import com.wecent.smart.resource.drawable.CircleDrawable;
import com.wecent.smart.widget.listener.OnCreateMessageListener;

/**
 * 对话框纯文本视图
 * Created by wecent on 2017/3/30.
 */
final class BodyTextView extends ScaleTextView {
    private SmartParams mParams;

    public BodyTextView(Context context, SmartParams params) {
        super(context);
        this.mParams = params;
        init(params);
    }

    private void init(SmartParams params) {
        DialogParams dialogParams = params.dialogParams;
        TitleParams titleParams = params.titleParams;
        MessageParams textParams = params.messageParams;
        ButtonParams negativeParams = params.negativeParams;
        ButtonParams positiveParams = params.positiveParams;
        ButtonParams neutralParams = params.neutralParams;

        setGravity(textParams.gravity);

        //如果标题没有背景色，则使用默认色
        int backgroundColor = textParams.backgroundColor != 0
                ? textParams.backgroundColor : dialogParams.backgroundColor;

        //有标题没按钮则底部圆角
        if (titleParams != null && negativeParams == null && positiveParams == null
                && neutralParams == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(new CircleDrawable(backgroundColor, 0, 0, dialogParams.radius,
                        dialogParams.radius));
            } else {
                setBackgroundDrawable(new CircleDrawable(backgroundColor, 0, 0, dialogParams.radius,
                        dialogParams.radius));
            }
        }
        //没标题有按钮则顶部圆角
        else if (titleParams == null
                && (negativeParams != null || positiveParams != null || neutralParams != null)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(new CircleDrawable(backgroundColor, dialogParams.radius, dialogParams
                        .radius, 0, 0));
            } else {
                setBackgroundDrawable(new CircleDrawable(backgroundColor, dialogParams.radius,
                        dialogParams.radius, 0, 0));
            }
        }
        //没标题没按钮则全部圆角
        else if (titleParams == null && negativeParams == null && positiveParams == null
                && neutralParams == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(new CircleDrawable(backgroundColor, dialogParams.radius));
            } else {
                setBackgroundDrawable(new CircleDrawable(backgroundColor, dialogParams.radius));
            }
        }
        //有标题有按钮则不用考虑圆角
        else setBackgroundColor(backgroundColor);

//        setHeight(textParams.height);
        setMinHeight(textParams.height);
        setTextColor(textParams.messageColor);
        setTextSize(textParams.messageSize);
        setText(textParams.message);
        setTypeface(getTypeface(), textParams.styleText);

        int[] padding = textParams.padding;
        if (padding != null) setAutoPadding(padding[0], padding[1], padding[2], padding[3]);

        OnCreateMessageListener createTextListener = params.createMessageListener;
        if (createTextListener != null) {
            createTextListener.onCreateText(this);
        }
    }

    public void refreshText() {
        if (mParams.messageParams == null) return;
        post(new Runnable() {
            @Override
            public void run() {
                setText(mParams.messageParams.message);
            }
        });
    }
}
