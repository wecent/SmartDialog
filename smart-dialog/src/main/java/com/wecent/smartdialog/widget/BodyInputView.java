package com.wecent.smartdialog.widget;

import android.content.Context;
import android.os.Build;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wecent.smartdialog.SmartController;
import com.wecent.smartdialog.SmartParams;
import com.wecent.smartdialog.params.ButtonParams;
import com.wecent.smartdialog.params.DialogParams;
import com.wecent.smartdialog.params.InputParams;
import com.wecent.smartdialog.params.TitleParams;
import com.wecent.smartdialog.resource.drawable.SmartDrawable;
import com.wecent.smartdialog.resource.drawable.InputDrawable;
import com.wecent.smartdialog.widget.listener.InputView;
import com.wecent.smartdialog.widget.listener.OnCreateInputListener;

import static com.wecent.smartdialog.SmartController.BUTTON_POSITIVE;
import static com.wecent.smartdialog.resource.values.SmartDimen.INPUT_COUNTER_TEXT_SIZE;

/**
 * desc: Alert弹框文本编辑
 * author: wecent
 * date: 2018/3/29
 */
public final class BodyInputView extends RelativeLayout implements SmartController.OnClickListener, InputView {
    private SmartEditText mEditText;
    private SmartTextView mTvCounter;
    private SmartParams params;

    public BodyInputView(Context context, SmartParams params) {
        super(context);
        init(context, params);
    }

    private void init(Context context, SmartParams params) {
        this.params = params;
        DialogParams dialogParams = params.dialogParams;
        TitleParams titleParams = params.titleParams;
        InputParams inputParams = params.inputParams;
        ButtonParams negativeParams = params.negativeParams;
        ButtonParams positiveParams = params.positiveParams;

        //如果标题没有背景色，则使用默认色
        int backgroundColor = inputParams.backgroundColor != 0
                ? inputParams.backgroundColor : dialogParams.backgroundColor;

        //有标题没按钮则底部圆角
        if (titleParams != null && negativeParams == null && positiveParams == null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                setBackground(new SmartDrawable(backgroundColor, 0, 0, dialogParams.radius,
                        dialogParams.radius));
            } else {
                setBackgroundDrawable(new SmartDrawable(backgroundColor, 0, 0, dialogParams
                        .radius, dialogParams.radius));
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

        mEditText = new SmartEditText(context);
        mEditText.setId(android.R.id.input);
        int inputType = inputParams.inputType;
        if (inputType != InputType.TYPE_NULL) {
            mEditText.setInputType(inputParams.inputType);
        }
        mEditText.setHint(inputParams.hintText);
        mEditText.setHintTextColor(inputParams.hintTextColor);
        mEditText.setTextSize(inputParams.textSize);
        mEditText.setTextColor(inputParams.textColor);
        mEditText.setHeight(inputParams.inputHeight);
        mEditText.setGravity(inputParams.gravity);
        if (!TextUtils.isEmpty(inputParams.text)) {
            mEditText.setText(inputParams.text);
            mEditText.setSelection(inputParams.text.length());
        }

        int backgroundResourceId = inputParams.inputBackgroundResourceId;
        if (backgroundResourceId == 0) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                mEditText.setBackground(new InputDrawable(inputParams.strokeWidth, inputParams
                        .strokeColor, inputParams.inputBackgroundColor, inputParams.strokeRadius));
            } else {
                mEditText.setBackgroundDrawable(new InputDrawable(inputParams.strokeWidth,
                        inputParams.strokeColor, inputParams.inputBackgroundColor, inputParams.strokeRadius));
            }
        } else mEditText.setBackgroundResource(backgroundResourceId);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT,
                LayoutParams.WRAP_CONTENT);
        int[] margins = inputParams.margins;
        if (margins != null) {
            layoutParams.setMargins(margins[0], margins[1], margins[2], margins[3]);
        }
        int[] padding = inputParams.padding;
        if (padding != null)
            mEditText.setAutoPadding(padding[0], padding[1], padding[2], padding[3]);
        mEditText.setTypeface(mEditText.getTypeface(), inputParams.styleText);

        addView(mEditText, layoutParams);

        if (inputParams.maxLen > 0) {
            LayoutParams layoutParamsCounter = new LayoutParams(LayoutParams.WRAP_CONTENT,
                    LayoutParams.WRAP_CONTENT);
            //右下
            layoutParamsCounter.addRule(ALIGN_RIGHT, android.R.id.input);
            layoutParamsCounter.addRule(ALIGN_BOTTOM, android.R.id.input);
            if (inputParams.counterMargins != null) {
                layoutParamsCounter.setMargins(0, 0
                        , inputParams.counterMargins[0]
                        , inputParams.counterMargins[1]);
            }
            mTvCounter = new SmartTextView(context);
            mTvCounter.setTextSize(INPUT_COUNTER_TEXT_SIZE);
            mTvCounter.setTextColor(inputParams.counterColor);

            mEditText.addTextChangedListener(new MaxLengthWatcher(inputParams.maxLen
                    , mEditText, mTvCounter));

            addView(mTvCounter, layoutParamsCounter);
        }

        OnCreateInputListener createInputListener = params.createInputListener;
        if (createInputListener != null) {
            createInputListener.onCreateText(this, mEditText, mTvCounter);
        }
    }

    public static int chineseLength(String str) {
        int valueLength = 0;
        if (!TextUtils.isEmpty(str)) {
            // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                // 获取一个字符
                String temp = str.substring(i, i + 1);
                // 判断是否为中文字符
                if (isChinese(temp)) {
                    // 中文字符长度为2
                    valueLength += 2;
                } else {
                    // 其他字符长度为1
                    valueLength += 1;
                }
            }
        }
        return valueLength;
    }

    public static boolean isChinese(String str) {
        Boolean isChinese = true;
        String chinese = "[\u0391-\uFFE5]";
        if (!TextUtils.isEmpty(str)) {
            // 获取字段值的长度，如果含中文字符，则每个中文字符长度为2，否则为1
            for (int i = 0; i < str.length(); i++) {
                // 获取一个字符
                String temp = str.substring(i, i + 1);
                // 判断是否为中文字符
                if (temp.matches(chinese)) {
                } else {
                    isChinese = false;
                }
            }
        }
        return isChinese;
    }

    @Override
    public void onClick(View view, int which) {
        if (view instanceof InputView && which == BUTTON_POSITIVE) {
            InputView inputView = (InputView) view;
            String text = inputView.getInput().getText().toString();
            if (params.inputListener != null) {
                params.inputListener.onClick(text, inputView.getInput());
            }
        }
    }

    @Override
    public EditText getInput() {
        return mEditText;
    }

    @Override
    public View getView() {
        return this;
    }

    public class MaxLengthWatcher implements TextWatcher {
        private int mMaxLen;
        private EditText mEditText;
        private TextView mTvCounter;

        public MaxLengthWatcher(int maxLen, EditText editText, TextView textView) {
            this.mMaxLen = maxLen;
            this.mEditText = editText;
            this.mTvCounter = textView;
            if (mEditText != null) {
                String defText = mEditText.getText().toString();
                int currentLen = maxLen - chineseLength(defText);
                if (params.inputCounterChangeListener != null) {
                    String counterText = params.inputCounterChangeListener
                            .onChange(maxLen, currentLen);
                    mTvCounter.setText(counterText == null ? "" : counterText);
                } else {
                    mTvCounter.setText(String.valueOf(currentLen));
                }
            }
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable editable) {
            int editStart = mEditText.getSelectionStart();
            int editEnd = mEditText.getSelectionEnd();
            // 先去掉监听器，否则会出现栈溢出
            mEditText.removeTextChangedListener(this);
            if (!TextUtils.isEmpty(editable)) {
                //循环删除多出的字符
                while (chineseLength(editable.toString()) > mMaxLen) {
                    editable.delete(editStart - 1, editEnd);
                    editStart--;
                    editEnd--;
                }
            }
            int currentLen = mMaxLen - chineseLength(editable.toString());
            if (params.inputCounterChangeListener != null) {
                String counterText = params.inputCounterChangeListener
                        .onChange(mMaxLen, currentLen);
                mTvCounter.setText(counterText == null ? "" : counterText);
            } else {
                mTvCounter.setText(String.valueOf(currentLen));
            }

            mEditText.setSelection(editStart);
            // 恢复监听器
            mEditText.addTextChangedListener(this);
        }
    }
}
