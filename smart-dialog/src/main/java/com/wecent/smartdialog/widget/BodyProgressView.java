package com.wecent.smartdialog.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.wecent.smartdialog.SmartParams;
import com.wecent.smartdialog.params.ButtonParams;
import com.wecent.smartdialog.params.DialogParams;
import com.wecent.smartdialog.params.ProgressParams;
import com.wecent.smartdialog.params.TitleParams;
import com.wecent.smartdialog.resource.drawable.SmartDrawable;
import com.wecent.smartdialog.resource.values.SmartDimen;
import com.wecent.smartdialog.widget.listener.OnCreateProgressListener;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

/**
 * desc: Alert弹框进度条
 * author: wecent
 * date: 2018/3/29
 */
public final class BodyProgressView extends LinearLayout {
    private ProgressParams mProgressParams;
    private ProgressBar mProgressBar;
    private Handler mViewUpdateHandler;

    public BodyProgressView(Context context, SmartParams params) {
        super(context);
        init(context, params);
    }

    @SuppressLint("HandlerLeak")
    private void init(Context context, SmartParams params) {
        setOrientation(LinearLayout.VERTICAL);
        DialogParams dialogParams = params.dialogParams;
        TitleParams titleParams = params.titleParams;
        ButtonParams negativeParams = params.negativeParams;
        ButtonParams positiveParams = params.positiveParams;
        mProgressParams = params.progressParams;

        //如果没有背景色，则使用默认色
        int backgroundColor = mProgressParams.backgroundColor != 0
                ? mProgressParams.backgroundColor : dialogParams.backgroundColor;

        //有标题没按钮则底部圆角
        if (titleParams != null && negativeParams == null && positiveParams == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(new SmartDrawable(backgroundColor, 0, 0, dialogParams.radius,
                        dialogParams.radius));
            } else {
                setBackgroundDrawable(new SmartDrawable(backgroundColor, 0, 0, dialogParams.radius,
                        dialogParams.radius));
            }
        }
        //没标题有按钮则顶部圆角
        else if (titleParams == null && (negativeParams != null || positiveParams != null)) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(new SmartDrawable(backgroundColor, dialogParams.radius, dialogParams
                        .radius, 0, 0));
            } else {
                setBackgroundDrawable(new SmartDrawable(backgroundColor, dialogParams.radius,
                        dialogParams.radius, 0, 0));
            }
        }
        //没标题没按钮则全部圆角
        else if (titleParams == null && negativeParams == null && positiveParams == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(new SmartDrawable(backgroundColor, dialogParams.radius));
            } else {
                setBackgroundDrawable(new SmartDrawable(backgroundColor, dialogParams.radius));
            }
        }
        //有标题有按钮则不用考虑圆角
        else setBackgroundColor(backgroundColor);

        //自定义样式
        int progressDrawableId = mProgressParams.progressDrawableId;
        //水平样式
        if (mProgressParams.style == ProgressParams.STYLE_HORIZONTAL) {
            if (progressDrawableId != 0) {
                mProgressBar = new ProgressBar(getContext());
                setFieldValue(mProgressBar, "mOnlyIndeterminate", new Boolean(false));
                mProgressBar.setIndeterminate(false);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    mProgressBar.setProgressDrawableTiled(context.getDrawable(progressDrawableId));
                else
                    mProgressBar.setProgressDrawable(context.getResources().getDrawable(progressDrawableId));
            } else {
                mProgressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyleHorizontal);
            }
            mProgressParams.progressHeight = SmartDimen.PROGRESS_HEIGHT_HORIZONTAL;
        }
        //旋转样式
        else {
            if (progressDrawableId != 0) {
                mProgressBar = new ProgressBar(getContext());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
                    mProgressBar.setIndeterminateDrawableTiled(context.getDrawable(progressDrawableId));
                else
                    mProgressBar.setIndeterminateDrawable(context.getResources().getDrawable(progressDrawableId));
            } else {
                mProgressBar = new ProgressBar(getContext(), null, android.R.attr.progressBarStyle);
            }
            mProgressParams.progressHeight = SmartDimen.PROGRESS_HEIGHT_SPINNER;
        }

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, mProgressParams
                .progressHeight);
        int[] margins = mProgressParams.margins;
        if (margins != null)
            layoutParams.setMargins(margins[0], margins[1], margins[2], margins[3]);
        addView(mProgressBar, layoutParams);

        //构建文本
        final SmartTextView textView = new SmartTextView(getContext());
        textView.setTextSize(mProgressParams.textSize);
        textView.setTextColor(mProgressParams.textColor);
        textView.setTypeface(textView.getTypeface(), mProgressParams.styleText);
        int[] padding = mProgressParams.padding;
        if (padding != null)
            textView.setAutoPadding(padding[0], padding[1], padding[2], padding[3]);
        addView(textView);

        if (mProgressParams.style == ProgressParams.STYLE_HORIZONTAL
                && !TextUtils.isEmpty(mProgressParams.text)) {
            mViewUpdateHandler = new Handler() {
                @Override
                public void handleMessage(Message msg) {
                    super.handleMessage(msg);
                    int progress = mProgressBar.getProgress();
                    int max = mProgressBar.getMax();
                    int percent = (int) (((float) progress / (float) max) * 100);
                    String args = percent + "%";
                    if (mProgressParams.text.contains("%s")) {
                        if (max == progress) {
                            textView.setText(String.format(mProgressParams.text));
                        } else {
                            textView.setText(String.format(mProgressParams.text, args));
                        }
                    } else {
                        if (max == progress) {
                            textView.setText(mProgressParams.text);
                        } else {
                            textView.setText(mProgressParams.text + args);
                        }
                    }
                }
            };
        } else {
            textView.setText(mProgressParams.text);
        }

        OnCreateProgressListener createProgressListener = params.createProgressListener;
        if (createProgressListener != null) {
            createProgressListener.onCreateProgressView(mProgressBar, textView);
        }
    }

    /**
     * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
     */
    private static void setFieldValue(final Object object, final String fieldName, final Object
            value) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null)
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on " +
                    "target [" + object + "]");
        makeAccessible(field);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     */
    protected static Field getDeclaredField(final Object object, final String fieldName) {
        return getDeclaredField(object.getClass(), fieldName);
    }

    /**
     * 强制转换fileld可访问.
     */
    protected static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field
                .getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 循环向上转型,获取类的DeclaredField.
     */
    @SuppressWarnings("unchecked")
    protected static Field getDeclaredField(final Class clazz, final String fieldName) {
        for (Class superClass = clazz; superClass != Object.class; superClass = superClass
                .getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                e.printStackTrace();// Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    public void refreshProgress() {
        mProgressBar.setMax(mProgressParams.max);
        mProgressBar.setProgress(mProgressParams.progress);
        mProgressBar.setSecondaryProgress(mProgressParams.progress + 10);
        onProgressChanged();
    }

    private void onProgressChanged() {
        if (mViewUpdateHandler != null && !mViewUpdateHandler.hasMessages(0))
            mViewUpdateHandler.sendEmptyMessage(0);
    }
}
