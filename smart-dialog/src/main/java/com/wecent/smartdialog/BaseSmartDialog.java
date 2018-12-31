package com.wecent.smartdialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.FloatRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.FrameLayout;

import com.wecent.smartdialog.resource.drawable.SmartDrawable;
import com.wecent.smartdialog.resource.values.SmartDimen;
import com.wecent.smartdialog.scale.ScaleHelper;

/**
 * Created by wecent on 2017/3/29.
 */

public abstract class BaseSmartDialog extends DialogFragment {

    private static final String SAVED_GRAVITY = "circle:baseGravity";
    private static final String SAVED_TOUCH_OUT = "circle:baseTouchOut";
    private static final String SAVED_CANCELED_BACK = "circle:baseCanceledBack";
    private static final String SAVED_WIDTH = "circle:baseWidth";
    private static final String SAVED_HEIGHT_MAX = "circle:baseMaxHeight";
    private static final String SAVED_PADDING = "circle:basePadding";
    private static final String SAVED_ANIM_STYLE = "circle:baseAnimStyle";
    private static final String SAVED_DIM_ENABLED = "circle:baseDimEnabled";
    private static final String SAVED_BACKGROUND_COLOR = "circle:baseBackgroundColor";
    private static final String SAVED_RADIUS = "circle:baseRadius";
    private static final String SAVED_ALPHA = "circle:baseAlpha";
    private static final String SAVED_X = "circle:baseX";
    private static final String SAVED_Y = "circle:baseY";
    private int mGravity = Gravity.CENTER;//对话框的位置
    private boolean mCanceledOnTouchOutside = true;//是否触摸外部关闭
    private boolean mCanceledBack = true;//是否返回键关闭
    private float mWidth = 0.9f;//对话框宽度，范围：0-1；1整屏宽
    private float mMaxHeight;//对话框高度，范围：0-1；1整屏高
    private int[] mPadding;//对话框与屏幕边缘距离
    private int mAnimStyle;//显示动画
    private boolean isDimEnabled = true;
    private int mBackgroundColor = Color.TRANSPARENT;//对话框的背景色
    private int mRadius = SmartDimen.DIALOG_RADIUS;//对话框的圆角半径
    private float mAlpha = 1f;//对话框透明度，范围：0-1；1不透明
    private int mX, mY;
    private View.OnLayoutChangeListener mOnLayoutChangeListener;

    public BaseSmartDialog() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View view = createView(getContext(), inflater, container);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            view.setBackground(new SmartDrawable(mBackgroundColor, mRadius));
        } else {
            view.setBackgroundDrawable(new SmartDrawable(mBackgroundColor, mRadius));
        }
        view.setAlpha(mAlpha);
        return view;
    }

    @Override
    public void onViewCreated(final View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mMaxHeight > 0) {
            mOnLayoutChangeListener = new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    int height = v.getHeight();
                    DisplayMetrics dm = getDisplayMetrics();
                    int maxHeight = (int) (dm.heightPixels * mMaxHeight);
                    if (height > maxHeight) {
                        view.setLayoutParams(new FrameLayout.LayoutParams(
                                FrameLayout.LayoutParams.MATCH_PARENT
                                , maxHeight));
                    }
                }
            };
            view.addOnLayoutChangeListener(mOnLayoutChangeListener);
        }
    }

    @NonNull
    private DisplayMetrics getDisplayMetrics() {
        //获取屏幕宽
        DisplayMetrics dm = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }

    public abstract View createView(Context context, LayoutInflater inflater, ViewGroup container);

    @Override
    public void show(FragmentManager manager, String tag) {
        FragmentTransaction transaction = manager.beginTransaction();
        if (isAdded()) {
            transaction.remove(this).commit();
        }
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
        transaction.add(this, tag);
        transaction.commitAllowingStateLoss();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置 无标题 无边框
        setStyle(DialogFragment.STYLE_NO_TITLE, 0);
        if (savedInstanceState != null) {
            mGravity = savedInstanceState.getInt(SAVED_GRAVITY);
            mCanceledOnTouchOutside = savedInstanceState.getBoolean(SAVED_TOUCH_OUT);
            mCanceledBack = savedInstanceState.getBoolean(SAVED_CANCELED_BACK);
            mWidth = savedInstanceState.getFloat(SAVED_WIDTH);
            mMaxHeight = savedInstanceState.getFloat(SAVED_HEIGHT_MAX);
            mPadding = savedInstanceState.getIntArray(SAVED_PADDING);
            mAnimStyle = savedInstanceState.getInt(SAVED_ANIM_STYLE);
            isDimEnabled = savedInstanceState.getBoolean(SAVED_DIM_ENABLED);
            mBackgroundColor = savedInstanceState.getInt(SAVED_BACKGROUND_COLOR);
            mRadius = savedInstanceState.getInt(SAVED_RADIUS);
            mAlpha = savedInstanceState.getFloat(SAVED_ALPHA);
            mX = savedInstanceState.getInt(SAVED_X);
            mY = savedInstanceState.getInt(SAVED_Y);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        View view = getView();
        if (view != null && mOnLayoutChangeListener != null) {
            view.removeOnLayoutChangeListener(mOnLayoutChangeListener);
        }
        super.onDismiss(dialog);
        remove();
    }

    @Override
    public void onStart() {
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setCanceledOnTouchOutside(mCanceledOnTouchOutside);
            dialog.setCancelable(mCanceledBack);
            setDialogGravity(dialog);//设置对话框布局
        }
        super.onStart();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(SAVED_GRAVITY, mGravity);
        outState.putBoolean(SAVED_TOUCH_OUT, mCanceledOnTouchOutside);
        outState.putBoolean(SAVED_CANCELED_BACK, mCanceledBack);
        outState.putFloat(SAVED_WIDTH, mWidth);
        outState.putFloat(SAVED_HEIGHT_MAX, mMaxHeight);
        if (mPadding != null) outState.putIntArray(SAVED_PADDING, mPadding);
        outState.putInt(SAVED_ANIM_STYLE, mAnimStyle);
        outState.putBoolean(SAVED_DIM_ENABLED, isDimEnabled);
        outState.putInt(SAVED_BACKGROUND_COLOR, mBackgroundColor);
        outState.putInt(SAVED_RADIUS, mRadius);
        outState.putFloat(SAVED_ALPHA, mAlpha);
        outState.putInt(SAVED_X, mX);
        outState.putInt(SAVED_Y, mY);
    }

    /**
     * 对话框配置
     *
     * @param dialog
     */
    private void setDialogGravity(Dialog dialog) {
        Window window = dialog.getWindow();
        window.setBackgroundDrawableResource(android.R.color.transparent);
        WindowManager.LayoutParams wlp = window.getAttributes();
        DisplayMetrics dm = getDisplayMetrics();
        wlp.width = (int) (dm.widthPixels * mWidth);//宽度按屏幕大小的百分比设置
        wlp.gravity = mGravity;
        wlp.x = mX;
        wlp.y = mY;
        //边距
        if (mPadding != null) {
            int[] padding = mPadding;
            wlp.width = WindowManager.LayoutParams.MATCH_PARENT;
            window.getDecorView().setPadding(ScaleHelper.scaleValue(padding[0]), ScaleHelper
                    .scaleValue(padding[1]), ScaleHelper.scaleValue(padding[2]), ScaleHelper
                    .scaleValue(padding[3]));
        }
        //动画
        if (mAnimStyle != 0) window.setWindowAnimations(mAnimStyle);

        if (isDimEnabled) window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        else window.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(wlp);
    }

    public void remove() {
        FragmentManager fragmentManager = getFragmentManager();
        if (fragmentManager == null) return;
        FragmentTransaction ft = fragmentManager.beginTransaction();
        ft.remove(this);
        ft.addToBackStack(null);
    }

    /**
     * 设置对话框位置
     * {@link Gravity#CENTER 默认}
     *
     * @param gravity 位置
     */
    protected void setGravity(int gravity) {
        mGravity = gravity;
    }

    /**
     * 设置对话框点击外部关闭
     *
     * @param cancel true允许
     */
    protected void setCanceledOnTouchOutside(boolean cancel) {
        mCanceledOnTouchOutside = cancel;
    }

    /**
     * 设置对话框返回键关闭关闭
     *
     * @param cancel true允许
     */
    protected void setCanceledBack(boolean cancel) {
        mCanceledBack = cancel;
    }

    /**
     * 设置对话框宽度
     *
     * @param width 0.0 - 1.0
     */
    protected void setWidth(@FloatRange(from = 0.0, to = 1.0) float width) {
        mWidth = width;
    }

    /**
     * 设置对话框最大高度
     *
     * @param maxHeight 0f - 1f 之间
     */
    protected void setMaxHeight(@FloatRange(from = 0.0, to = 1.0) float maxHeight) {
        mMaxHeight = maxHeight;
    }

    /**
     * 设置边距
     *
     * @param left   px
     * @param top    px
     * @param right  px
     * @param bottom px
     */
    protected void setPadding(int left, int top, int right, int bottom) {
        mPadding = new int[]{left, top, right, bottom};
    }

    /**
     * 弹出对话框的动画
     *
     * @param animStyle StyleRes
     */
    protected void setAnimations(int animStyle) {
        mAnimStyle = animStyle;
    }


    /**
     * 设置背景是否昏暗，默认true
     *
     * @param dimEnabled true昏暗
     */
    protected void setDimEnabled(boolean dimEnabled) {
        isDimEnabled = dimEnabled;
    }

    /**
     * 设置对话框背景色
     *
     * @param color 颜色值
     */
    protected void setBackgroundColor(@ColorInt int color) {
        mBackgroundColor = color;
    }

    /**
     * 设置对话框圆角
     *
     * @param radius 半径
     */
    protected void setRadius(int radius) {
        mRadius = radius;
    }

    /**
     * 设置对话框透明度
     *
     * @param alpha 0.0 - 1.0
     */
    protected void setAlpha(@FloatRange(from = 0.0, to = 1.0) float alpha) {
        mAlpha = alpha;
    }

    protected void setX(int x) {
        mX = x;
    }

    protected void setY(int y) {
        mY = y;
    }

    //显示键盘
    protected void showSoftInputView(final View view) {
        view.post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager manager = ((InputMethodManager) getActivity()
                        .getSystemService(Activity.INPUT_METHOD_SERVICE));
                if (getActivity().getCurrentFocus() != null)
                    manager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        });
    }
}