package com.wecent.smartdialog;

import android.content.DialogInterface;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.FloatRange;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Gravity;
import android.view.View;

import com.wecent.smartdialog.callback.ConfigButton;
import com.wecent.smartdialog.callback.ConfigDialog;
import com.wecent.smartdialog.callback.ConfigInput;
import com.wecent.smartdialog.callback.ConfigItems;
import com.wecent.smartdialog.callback.ConfigMessage;
import com.wecent.smartdialog.callback.ConfigProgress;
import com.wecent.smartdialog.callback.ConfigSubtitle;
import com.wecent.smartdialog.callback.ConfigTitle;
import com.wecent.smartdialog.params.ButtonParams;
import com.wecent.smartdialog.params.DialogParams;
import com.wecent.smartdialog.params.InputParams;
import com.wecent.smartdialog.params.ItemsParams;
import com.wecent.smartdialog.params.MessageParams;
import com.wecent.smartdialog.params.ProgressParams;
import com.wecent.smartdialog.params.SubtitleParams;
import com.wecent.smartdialog.params.TitleParams;
import com.wecent.smartdialog.resource.values.SmartColor;
import com.wecent.smartdialog.widget.listener.OnCreateCustomListener;
import com.wecent.smartdialog.widget.listener.OnCreateButtonListener;
import com.wecent.smartdialog.widget.listener.OnCreateInputListener;
import com.wecent.smartdialog.widget.listener.OnCreateProgressListener;
import com.wecent.smartdialog.widget.listener.OnCreateMessageListener;
import com.wecent.smartdialog.widget.listener.OnCreateTitleListener;
import com.wecent.smartdialog.widget.listener.OnInputClickListener;
import com.wecent.smartdialog.widget.listener.OnInputChangeListener;
import com.wecent.smartdialog.widget.listener.OnItemsClickListener;

/**
 * desc: SmartDialog
 * author: wecent
 * date: 2018/3/30
 */
public final class SmartDialog {

    private AbsSmartDialog mDialog;

    private SmartDialog() {

    }

    public DialogFragment create(SmartParams params) {
        if (mDialog == null) {
            mDialog = AbsSmartDialog.newAbsSmartDialog(params);
        } else {
            if (mDialog.getDialog() != null && mDialog.getDialog().isShowing()) {
                mDialog.refreshView();
            }
        }
        return mDialog;
    }

    @Deprecated
    public void show(FragmentActivity activity) {
        if (activity == null) {
            throw new NullPointerException("please call constructor builder(FragmentActivity)");
        }
        mDialog.show(activity.getSupportFragmentManager(), "SmartDialog");
    }

    public void show(FragmentManager manager) {
        mDialog.show(manager, "SmartDialog");
    }

    public static class Builder {
        private FragmentActivity mActivity;
        private SmartDialog mSmartDialog;
        private SmartParams mSmartParams;

        public Builder() {
            init();
        }

        private void init() {
            mSmartParams = new SmartParams();
            mSmartParams.dialogParams = new DialogParams();
        }

        @Deprecated
        public Builder(@NonNull FragmentActivity activity) {
            this.mActivity = activity;
            init();
        }

        /**
         * 设置对话框位置
         *
         * @param gravity 位置
         * @return this Builder
         */
        public Builder setGravity(int gravity) {
            mSmartParams.dialogParams.gravity = gravity;
            return this;
        }

        /**
         * 设置对话框是否允许点击外部关闭
         *
         * @param cancel 否允许点
         * @return this Builder
         */
        public Builder setCanceledOnTouchOutside(boolean cancel) {
            mSmartParams.dialogParams.canceledOnTouchOutside = cancel;
            return this;
        }

        /**
         * 设置对话框返回键是否
         *
         * @param cancel true允许
         * @return this Builder
         */
        public Builder setCancelable(boolean cancel) {
            mSmartParams.dialogParams.cancelable = cancel;
            return this;
        }

        /**
         * 设置对话框宽度
         *
         * @param width 0.0 - 1.0
         * @return this Builder
         */
        public Builder setWidth(@FloatRange(from = 0.0, to = 1.0) float width) {
            mSmartParams.dialogParams.width = width;
            return this;
        }

        /**
         * 设置对话框最大高度
         *
         * @param maxHeight 0.0 - 1.0
         * @return this Builder
         */
        public Builder setMaxHeight(@FloatRange(from = 0.0, to = 1.0) float maxHeight) {
            mSmartParams.dialogParams.maxHeight = maxHeight;
            return this;
        }

        /**
         * 设置对话框圆角
         *
         * @param radius 半径
         * @return this Builder
         */
        public Builder setRadius(int radius) {
            mSmartParams.dialogParams.radius = radius;
            return this;
        }

        /**
         * 对话框消失的接口回调
         *
         * @param listener
         * @return
         */
        public Builder setOnDismissListener(DialogInterface.OnDismissListener listener) {
            mSmartParams.dismissListener = listener;
            return this;
        }

        /**
         * 对话框取消的接口回调
         *
         * @param listener
         * @return
         */
        public Builder setOnCancelListener(DialogInterface.OnCancelListener listener) {
            mSmartParams.cancelListener = listener;
            return this;
        }

        /**
         * 对话框显示的接口回调
         *
         * @param listener
         * @return
         */
        public Builder setOnShowListener(DialogInterface.OnShowListener listener) {
            mSmartParams.showListener = listener;
            return this;
        }

        /**
         * 对话框默认属性
         *
         * @param configDialog
         * @return
         */
        public Builder configDialog(@NonNull ConfigDialog configDialog) {
            configDialog.onConfig(mSmartParams.dialogParams);
            return this;
        }

        /**
         * 设置对话框标题
         *
         * @param text
         * @return
         */
        public Builder setTitle(@NonNull String text) {
            newTitleParams();
            mSmartParams.titleParams.text = text;
            return this;
        }

        private void newTitleParams() {
            if (mSmartParams.titleParams == null)
                mSmartParams.titleParams = new TitleParams();
        }

        /**
         * 设置对话框标题图标
         *
         * @param icon
         * @return
         */
        public Builder setTitleIcon(@DrawableRes int icon) {
            newTitleParams();
            mSmartParams.titleParams.icon = icon;
            return this;
        }

        /**
         * 设置对话框标题颜色
         *
         * @param color
         * @return
         */
        public Builder setTitleColor(@ColorInt int color) {
            newTitleParams();
            mSmartParams.titleParams.textColor = color;
            return this;
        }

        /**
         * 对话框标题文本属性
         *
         * @param configTitle
         * @return
         */
        public Builder configTitle(@NonNull ConfigTitle configTitle) {
            newTitleParams();
            configTitle.onConfig(mSmartParams.titleParams);
            return this;
        }

        /**
         * 对话框标题更新内容接口回调
         *
         * @param listener
         * @return
         */
        public Builder setOnCreateTitleListener(@NonNull OnCreateTitleListener listener) {
            mSmartParams.createTitleListener = listener;
            return this;
        }

        /**
         * 设置对话框副标题
         *
         * @param text
         * @return
         */
        public Builder setSubtitle(@NonNull String text) {
            newSubtitleParams();
            mSmartParams.subtitleParams.text = text;
            return this;
        }

        private void newSubtitleParams() {
            if (mSmartParams.subtitleParams == null)
                mSmartParams.subtitleParams = new SubtitleParams();
        }

        /**
         * 设置对话框副标题颜色
         *
         * @param color
         * @return
         */
        public Builder setSubtitleColor(@ColorInt int color) {
            newSubtitleParams();
            mSmartParams.subtitleParams.textColor = color;
            return this;
        }

        /**
         * 对话框副标题文本属性
         *
         * @param configSubtitle
         * @return
         */
        public Builder configSubtitle(@NonNull ConfigSubtitle configSubtitle) {
            newSubtitleParams();
            configSubtitle.onConfig(mSmartParams.subtitleParams);
            return this;
        }

        /**
         * 设置对话框消息文本内容
         *
         * @param message
         * @return
         */
        public Builder setMessage(@NonNull String message) {
            newMessageParams();
            mSmartParams.messageParams.message = message;
            return this;
        }

        /**
         * 设置对话框消息文本颜色
         *
         * @param color
         * @return
         */
        public Builder setMessageColor(@ColorInt int color) {
            newMessageParams();
            mSmartParams.messageParams.messageColor = color;
            return this;
        }

        /**
         * 对话框消息文本属性
         *
         * @param configMessage
         * @return
         */
        public Builder configMessage(@NonNull ConfigMessage configMessage) {
            newMessageParams();
            configMessage.onConfig(mSmartParams.messageParams);
            return this;
        }

        private void newMessageParams() {
            //判断是否已经设置过
            if (mSmartParams.dialogParams.gravity == Gravity.NO_GRAVITY)
                mSmartParams.dialogParams.gravity = Gravity.CENTER;
            if (mSmartParams.messageParams == null)
                mSmartParams.messageParams = new MessageParams();
        }

        /**
         * 对话框消息更新内容接口回调
         *
         * @param listener
         * @return
         */
        public Builder setOnCreateMessageListener(OnCreateMessageListener listener) {
            mSmartParams.createMessageListener = listener;
            return this;
        }

//        public Builder setItems(@NonNull Object items, AdapterView.OnItemsClickListener listener) {
//            newItemsParams();
//            ItemsParams params = mSmartParams.itemsParams;
//            params.items = items;
//            mSmartParams.itemListener = listener;
//            return this;
//        }

        private void newItemsParams() {
            //设置列表特殊的参数
            DialogParams dialogParams = mSmartParams.dialogParams;
            //判断是否已经设置过
            if (dialogParams.gravity == Gravity.NO_GRAVITY)
                dialogParams.gravity = Gravity.BOTTOM;//默认底部显示
            //判断是否已经设置过
            if (dialogParams.yOff == -1)
                dialogParams.yOff = 20;//底部与屏幕的距离

            if (mSmartParams.itemsParams == null)
                mSmartParams.itemsParams = new ItemsParams();
        }

        public Builder setItems(@NonNull Object items, OnItemsClickListener listener) {
            newItemsParams();
            ItemsParams params = mSmartParams.itemsParams;
            params.items = items;
            mSmartParams.itemListener = listener;
            return this;
        }

        /**
         * 设置items否触发自动关闭对话框，默认自动
         *
         * @param manualClose true=手动；false=自动
         * @return this Builder
         */
        public Builder setItemsManualClose(boolean manualClose) {
            newItemsParams();
            mSmartParams.itemsParams.isManualClose = manualClose;
            return this;
        }

        public Builder configItems(@NonNull ConfigItems configItems) {
            newItemsParams();
            configItems.onConfig(mSmartParams.itemsParams);
            return this;
        }

        /**
         * 设置进度条文本
         *
         * @param text 进度条文本，style = 水平样式时，支持String.format() 例如：已经下载%s
         * @return this Builder
         */
        public Builder setProgressText(@NonNull String text) {
            newProgressParams();
            mSmartParams.progressParams.text = text;
            return this;
        }

        private void newProgressParams() {
            //判断是否已经设置过
            if (mSmartParams.dialogParams.gravity == Gravity.NO_GRAVITY)
                mSmartParams.dialogParams.gravity = Gravity.CENTER;
            if (mSmartParams.progressParams == null)
                mSmartParams.progressParams = new ProgressParams();
        }

        /**
         * 设置进度条样式
         *
         * @param style {@link ProgressParams#STYLE_HORIZONTAL 水平样式} or
         *              {@link ProgressParams#STYLE_SPINNER}
         * @return this Builder
         */
        public Builder setProgressStyle(int style) {
            newProgressParams();
            mSmartParams.progressParams.style = style;
            return this;
        }

        public Builder setProgress(int max, int progress) {
            newProgressParams();
            ProgressParams progressParams = mSmartParams.progressParams;
            progressParams.max = max;
            progressParams.progress = progress;
            return this;
        }

        public Builder setProgressDrawable(@DrawableRes int progressDrawableId) {
            newProgressParams();
            mSmartParams.progressParams.progressDrawableId = progressDrawableId;
            return this;
        }

        public Builder setProgressHeight(int height) {
            newProgressParams();
            mSmartParams.progressParams.progressHeight = height;
            return this;
        }

        public Builder configProgress(@NonNull ConfigProgress configProgress) {
            newProgressParams();
            configProgress.onConfig(mSmartParams.progressParams);
            return this;
        }

        public Builder setOnCreateProgressListener(OnCreateProgressListener listener) {
            mSmartParams.createProgressListener = listener;
            return this;
        }

        /**
         * 设置自定义视图
         *
         * @param customViewId resLayoutId
         * @param listener     listener
         * @return Builder
         */
        public Builder setCustomView(@LayoutRes int customViewId, OnCreateCustomListener listener) {
            mSmartParams.customViewId = customViewId;
            mSmartParams.createCustomListener = listener;
            return this;
        }

        private void newInputParams() {
            //判断是否已经设置过
            if (mSmartParams.dialogParams.gravity == Gravity.NO_GRAVITY)
                mSmartParams.dialogParams.gravity = Gravity.CENTER;
            if (mSmartParams.inputParams == null)
                mSmartParams.inputParams = new InputParams();
        }

        public Builder autoInputShowKeyboard() {
            newInputParams();
            mSmartParams.inputParams.showSoftKeyboard = true;
            return this;
        }

        public Builder setInputText(@NonNull String text, @NonNull String hint) {
            newInputParams();
            mSmartParams.inputParams.text = text;
            mSmartParams.inputParams.hintText = hint;
            return this;
        }

        public Builder setInputText(@NonNull String text) {
            newInputParams();
            mSmartParams.inputParams.text = text;
            return this;
        }

        public Builder setInputHint(@NonNull String hint) {
            newInputParams();
            mSmartParams.inputParams.hintText = hint;
            return this;
        }

        public Builder setInputHeight(int height) {
            newInputParams();
            mSmartParams.inputParams.inputHeight = height;
            return this;
        }

        /**
         * 输入框最大字符数量
         *
         * @param maxLen 字符数量
         * @return Builder
         */
        public Builder setInputCounter(int maxLen) {
            newInputParams();
            mSmartParams.inputParams.maxLen = maxLen;
            return this;
        }

        /**
         * 输入框最大字符数量颜色
         *
         * @param color 色值
         * @return Builder
         */
        public Builder setInputCounterColor(@ColorInt int color) {
            newInputParams();
            mSmartParams.inputParams.counterColor = color;
            return this;
        }

        /**
         * 输入框最大字符数量
         *
         * @param maxLen   字符数量
         * @param listener 字符计数器改变事件
         * @return Builder
         */
        public Builder setInputCounter(int maxLen, OnInputChangeListener listener) {
            newInputParams();
            mSmartParams.inputParams.maxLen = maxLen;
            mSmartParams.inputCounterChangeListener = listener;
            return this;
        }

        /**
         * 设置是否触发自动关闭对话框，默认自动
         *
         * @param manualClose true=手动；false=自动
         * @return this Builder
         */
        public Builder setInputManualClose(boolean manualClose) {
            newInputParams();
            mSmartParams.inputParams.isManualClose = manualClose;
            return this;
        }

        public Builder configInput(@NonNull ConfigInput configInput) {
            newInputParams();
            configInput.onConfig(mSmartParams.inputParams);
            return this;
        }

        public Builder setOnCreateInputListener(OnCreateInputListener listener) {
            mSmartParams.createInputListener = listener;
            return this;
        }

        /**
         * 确定按钮
         *
         * @param text     按钮文本
         * @param listener 事件
         * @return this Builder
         */
        public Builder setPositive(@NonNull String text, View.OnClickListener listener) {
            newPositiveParams();
            ButtonParams params = mSmartParams.positiveParams;
            params.text = text;
            mSmartParams.clickPositiveListener = listener;
            return this;
        }

        private void newPositiveParams() {
            if (mSmartParams.positiveParams == null)
                mSmartParams.positiveParams = new ButtonParams();
        }

        /**
         * 输入框的确定按钮
         *
         * @param text     按钮文本
         * @param listener 事件
         * @return this Builder
         */
        public Builder setPositiveInput(@NonNull String text, OnInputClickListener listener) {
            newPositiveParams();
            ButtonParams params = mSmartParams.positiveParams;
            params.text = text;
            mSmartParams.inputListener = listener;
            return this;
        }

        /**
         * 配置确定按钮
         *
         * @param configButton configButton
         * @return this Builder
         */
        public Builder configPositive(@NonNull ConfigButton configButton) {
            newPositiveParams();
            configButton.onConfig(mSmartParams.positiveParams);
            return this;
        }

        /**
         * 取消按钮
         *
         * @param text     按钮文本
         * @param listener 事件
         * @return this Builder
         */
        public Builder setNegative(@NonNull String text, View.OnClickListener listener) {
            newNegativeParams();
            ButtonParams params = mSmartParams.negativeParams;
            params.text = text;
            mSmartParams.clickNegativeListener = listener;
            return this;
        }

        private void newNegativeParams() {
            if (mSmartParams.negativeParams == null) {
                mSmartParams.negativeParams = new ButtonParams();
                mSmartParams.negativeParams.textColor = SmartColor.FOOTER_BUTTON_TEXT_NEGATIVE;
            }
        }

        /**
         * 配置取消按钮
         *
         * @param configButton configButton
         * @return this Builder
         */
        public Builder configNegative(@NonNull ConfigButton configButton) {
            newNegativeParams();
            configButton.onConfig(mSmartParams.negativeParams);
            return this;
        }

        /**
         * 中间按钮
         *
         * @param text     按钮文本
         * @param listener 事件
         * @return this Builder
         */
        public Builder setNeutral(@NonNull String text, View.OnClickListener listener) {
            newNeutralParams();
            ButtonParams params = mSmartParams.neutralParams;
            params.text = text;
            mSmartParams.clickNeutralListener = listener;
            return this;
        }

        private void newNeutralParams() {
            if (mSmartParams.neutralParams == null)
                mSmartParams.neutralParams = new ButtonParams();
        }

        /**
         * 配置中间按钮
         *
         * @param configButton configButton
         * @return this Builder
         */
        public Builder configNeutral(@NonNull ConfigButton configButton) {
            newNeutralParams();
            configButton.onConfig(mSmartParams.neutralParams);
            return this;
        }

        public Builder setOnCreateButtonListener(OnCreateButtonListener listener) {
            mSmartParams.createButtonListener = listener;
            return this;
        }

        @Deprecated
        public DialogFragment show() {
            DialogFragment dialogFragment = create();
            mSmartDialog.show(mActivity);
            return dialogFragment;
        }

        public DialogFragment create() {
            if (mSmartDialog == null)
                mSmartDialog = new SmartDialog();
            return mSmartDialog.create(mSmartParams);
        }

        public DialogFragment show(FragmentManager manager) {
            DialogFragment dialogFragment = create();
            mSmartDialog.show(manager);
            return dialogFragment;
        }
    }
}
