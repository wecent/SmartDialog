package com.wecent.smartdialog.widget;

import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;

import com.wecent.smartdialog.SmartController;
import com.wecent.smartdialog.SmartParams;
import com.wecent.smartdialog.params.ButtonParams;
import com.wecent.smartdialog.resource.drawable.ButtonDrawable;
import com.wecent.smartdialog.scale.ScaleHelper;
import com.wecent.smartdialog.widget.listener.ButtonView;
import com.wecent.smartdialog.widget.listener.OnCreateButtonListener;

/**
 * desc: 仿IOS SheetDialog弹框底部取消按钮 .
 * author: wecent .
 * date: 2017/3/17 .
 */
public final class SmartSingleButton extends ScaleLinearLayout implements SmartController.OnClickListener
        , ButtonView {

    private SmartParams mSmartParams;
    private ButtonParams mNegativeParams;
    private ButtonParams mPositiveParams;
    private ButtonParams mNeutralParams;
    private ScaleTextView mNegativeButton;
    private ScaleTextView mPositiveButton;
    private ScaleTextView mNeutralButton;

    public SmartSingleButton(Context context, SmartParams params) {
        super(context);
        init(params);
    }

    private void init(SmartParams params) {
        mSmartParams = params;

        mNegativeParams = params.negativeParams;
        mPositiveParams = params.positiveParams;
        mNeutralParams = params.neutralParams;

        int radius = params.dialogParams.radius;

        int backgroundNegative = 0;
        int backgroundNeutral = 0;
        int backgroundPositive = 0;
        if (mNegativeParams != null) {
            //取消按钮
            createNegative();
            //如果取消按钮没有背景色，则使用默认色
            backgroundNegative = mNegativeParams.backgroundColor != 0
                    ? mNegativeParams.backgroundColor : params.dialogParams.backgroundColor;
        }
        if (mNeutralParams != null) {
            if (mNegativeButton != null) {
                //分隔线 当且仅当前面有按钮这个按钮不为空的时候才需要添加分割线
                createDivider();
            }
            createNeutral();
            //如果取消按钮没有背景色，则使用默认色
            backgroundNeutral = mNeutralParams.backgroundColor != 0
                    ? mNeutralParams.backgroundColor : params.dialogParams.backgroundColor;

        }
        if (mPositiveParams != null) {
            if (mNeutralButton != null || mNegativeButton != null) {
                //分隔线 当且仅当前面有按钮这个按钮不为空的时候才需要添加分割线
                createDivider();
            }
            //确定按钮
            createPositive();
            //如果取消按钮没有背景色，则使用默认色
            backgroundPositive = mPositiveParams.backgroundColor != 0
                    ? mPositiveParams.backgroundColor : params.dialogParams.backgroundColor;
        }

        if (mNegativeButton != null && mNegativeParams != null) {
            //右边没按钮则右下是圆角
            int rightRadius = (mNeutralButton == null && mPositiveButton == null) ? radius : 0;
            ButtonDrawable buttonDrawable = new ButtonDrawable(backgroundNegative
                    , mNegativeParams.backgroundColorPress != 0
                    ? mNegativeParams.backgroundColorPress : params.dialogParams.backgroundColorPress
                    , radius, rightRadius, rightRadius, radius);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mNegativeButton.setBackground(buttonDrawable);
            } else {
                mNegativeButton.setBackgroundDrawable(buttonDrawable);
            }
        }
        if (mPositiveButton != null && mPositiveParams != null) {
            //左边没按钮则左下是圆角
            int leftRadius = (mNegativeButton == null && mNeutralButton == null) ? radius : 0;
            ButtonDrawable buttonDrawable = new ButtonDrawable(backgroundPositive
                    , mPositiveParams.backgroundColorPress != 0
                    ? mPositiveParams.backgroundColorPress : params.dialogParams.backgroundColorPress
                    , leftRadius, radius, radius, leftRadius);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mPositiveButton.setBackground(buttonDrawable);
            } else {
                mPositiveButton.setBackgroundDrawable(buttonDrawable);
            }
        }
        if (mNeutralButton != null && mNeutralParams != null) {
            //左右没按钮则左下右下是圆角
            int leftRadius = mNegativeButton == null ? radius : 0;
            int rightRadius = mPositiveButton == null ? radius : 0;
            ButtonDrawable buttonDrawable = new ButtonDrawable(backgroundNeutral
                    , mNeutralParams.backgroundColorPress != 0
                    ? mNeutralParams.backgroundColorPress : params.dialogParams.backgroundColorPress
                    , leftRadius, rightRadius, rightRadius, leftRadius);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mNeutralButton.setBackground(buttonDrawable);
            } else {
                mNeutralButton.setBackgroundDrawable(buttonDrawable);
            }
        }

        OnCreateButtonListener createButtonListener = mSmartParams.createButtonListener;
        if (createButtonListener != null) {
            createButtonListener.onCreateButton(mNegativeButton, mPositiveButton, mNeutralButton);
        }
    }

    private void createNegative() {
        mNegativeButton = new ScaleTextView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        //设置列表与按钮之间的上距离
        if (mNegativeParams.topMargin > 0) {
            params.topMargin = ScaleHelper.scaleValue(mNegativeParams.topMargin);
        }
        mNegativeButton.setLayoutParams(params);
        handleNegativeStyle();
        addView(mNegativeButton);
    }

    private void createDivider() {
        SmartDividerView smartDividerView = new SmartDividerView(getContext());
        addView(smartDividerView);
    }

    private void createNeutral() {
        mNeutralButton = new ScaleTextView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        //设置列表与按钮之间的上距离
        if (mNeutralParams.topMargin > 0) {
            params.topMargin = ScaleHelper.scaleValue(mNeutralParams.topMargin);
        }
        mNeutralButton.setLayoutParams(params);
        handleNeutralStyle();
        addView(mNeutralButton);
    }

    private void createPositive() {
        mPositiveButton = new ScaleTextView(getContext());
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT, 1);
        //设置列表与按钮之间的上距离
        if (mPositiveParams.topMargin > 0) {
            params.topMargin = ScaleHelper.scaleValue(mPositiveParams.topMargin);
        }
        mPositiveButton.setLayoutParams(params);
        handlePositiveStyle();
        addView(mPositiveButton);
    }

    private void handleNegativeStyle() {
        mNegativeButton.setText(mNegativeParams.text);
        mNegativeButton.setEnabled(!mNegativeParams.disable);
        mNegativeButton.setTextColor(mNegativeParams.disable ?
                mNegativeParams.textColorDisable : mNegativeParams.textColor);
        mNegativeButton.setTextSize(mNegativeParams.textSize);
        mNegativeButton.setHeight(mNegativeParams.height);
        mNegativeButton.setTypeface(mNegativeButton.getTypeface(), mNegativeParams.styleText);
    }

    private void handleNeutralStyle() {
        mNeutralButton.setText(mNeutralParams.text);
        mNeutralButton.setEnabled(!mNeutralParams.disable);
        mNeutralButton.setTextColor(mNeutralParams.disable ?
                mNeutralParams.textColorDisable : mNeutralParams.textColor);
        mNeutralButton.setTextSize(mNeutralParams.textSize);
        mNeutralButton.setHeight(mNeutralParams.height);
        mNeutralButton.setTypeface(mNeutralButton.getTypeface(), mNeutralParams.styleText);
    }

    private void handlePositiveStyle() {
        mPositiveButton.setText(mPositiveParams.text);
        mPositiveButton.setEnabled(!mPositiveParams.disable);
        mPositiveButton.setTextColor(mPositiveParams.disable ?
                mPositiveParams.textColorDisable : mPositiveParams.textColor);
        mPositiveButton.setTextSize(mPositiveParams.textSize);
        mPositiveButton.setHeight(mPositiveParams.height);
        mPositiveButton.setTypeface(mPositiveButton.getTypeface(), mPositiveParams.styleText);
    }

    public void regNegativeListener(OnClickListener onClickListener) {
        if (mNegativeButton != null) {
            mNegativeButton.setOnClickListener(onClickListener);
        }
    }

    public void regPositiveListener(OnClickListener onClickListener) {
        if (mPositiveButton != null) {
            mPositiveButton.setOnClickListener(onClickListener);
        }
    }

    public void regNeutralListener(OnClickListener onClickListener) {
        if (mNeutralButton != null) {
            mNeutralButton.setOnClickListener(onClickListener);
        }
    }


    public void refreshText() {
        if (mNegativeParams == null || mNegativeButton == null) return;
        post(new Runnable() {
            @Override
            public void run() {
                handleNegativeStyle();
            }
        });

        if (mPositiveParams == null || mPositiveButton == null) return;
        post(new Runnable() {
            @Override
            public void run() {
                handlePositiveStyle();
            }
        });


        if (mNeutralParams == null || mNeutralButton == null) return;
        post(new Runnable() {
            @Override
            public void run() {
                handleNeutralStyle();
            }
        });
    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return mNegativeParams == null && mPositiveParams == null && mNeutralParams == null;
    }

    @Override
    public void onClick(View view, int which) {
        if (which == SmartController.BUTTON_NEGATIVE) {
            if (mSmartParams.clickNegativeListener != null) {
                mSmartParams.clickNegativeListener.onClick(mNegativeButton);
            }
        } else if (which == SmartController.BUTTON_POSITIVE) {
            if (mSmartParams.clickPositiveListener != null) {
                mSmartParams.clickPositiveListener.onClick(mPositiveButton);
            }
        } else if (which == SmartController.BUTTON_NEUTRAL) {
            if (mSmartParams.clickNeutralListener != null) {
                mSmartParams.clickNeutralListener.onClick(mNeutralButton);
            }
        }

    }
}
