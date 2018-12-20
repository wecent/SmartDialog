package com.wecent.smart.params;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.InputType;
import android.view.Gravity;

import com.wecent.smart.resource.values.SmartColor;
import com.wecent.smart.resource.values.SmartDimen;

/**
 * 输入框参数
 * Created by wecent on 2017/3/31.
 */
public class InputParams implements Parcelable {

    public static final Creator<InputParams> CREATOR = new Creator<InputParams>() {
        @Override
        public InputParams createFromParcel(Parcel source) {
            return new InputParams(source);
        }

        @Override
        public InputParams[] newArray(int size) {
            return new InputParams[size];
        }
    };
    /**
     * 输入框与body视图的距离
     */
    public int[] margins = SmartDimen.INPUT_MARGINS;
    /**
     * 输入框的高度
     */
    public int inputHeight = SmartDimen.INPUT_HEIGHT;
    /**
     * 输入框提示语
     */
    public String hintText;
    /**
     * 输入框提示语颜色
     */
    public int hintTextColor = SmartColor.INPUT_TEXT_HINT;
    /**
     * 输入框背景资源文件
     */
    public int inputBackgroundResourceId;
    /**
     * 输入框边框线条粗细
     */
    public int strokeWidth = 2;
    /**
     * 输入框边框线四周圆角
     */
    public int strokeRadius = 10;
    /**
     * 输入框边框线条颜色
     */
    public int strokeColor = SmartColor.INPUT_STROKE;
    /**
     * 输入框的背景
     */
    public int inputBackgroundColor;
    /**
     * body视图的背景色
     */
    public int backgroundColor;
    /**
     * 输入框字体大小
     */
    public int textSize = SmartDimen.INPUT_TEXT_SIZE;
    /**
     * 输入框字体颜色
     */
    public int textColor = SmartColor.INPUT_TEXT;
    /**
     * 输入类型
     */
    public int inputType = InputType.TYPE_NULL;
    /**
     * 文字对齐方式，默认左上角
     */
    public int gravity = Gravity.LEFT | Gravity.TOP;
    /**
     * 是否触发自动关闭对话框
     */
    public boolean isManualClose;
    /**
     * 文本
     */
    public String text;
    /**
     * 内间距 [left, top, right, bottom]
     */
    public int[] padding = SmartDimen.INPUT_PADDING;
    /**
     * 字样式
     * {@linkplain Typeface#NORMAL NORMAL}
     * {@linkplain Typeface#BOLD BOLD}
     * {@linkplain Typeface#ITALIC ITALIC}
     * {@linkplain Typeface#BOLD_ITALIC BOLD_ITALIC}
     */
    public int styleText = Typeface.NORMAL;
    /**
     * 输入框限制字符数量，如counter=50：中(占2个)英(1个)文总字符数
     */
    public int maxLen;
    /**
     * 外边距 [右，下]
     */
    public int[] counterMargins = SmartDimen.INPUT_COUNTER_MARGINS;
    public int counterColor = SmartColor.INPUT_COUNTER_TEXT;
    /**
     * 显示软键盘
     */
    public boolean showSoftKeyboard;

    public InputParams() {
    }

    protected InputParams(Parcel in) {
        this.margins = in.createIntArray();
        this.inputHeight = in.readInt();
        this.hintText = in.readString();
        this.hintTextColor = in.readInt();
        this.inputBackgroundResourceId = in.readInt();
        this.strokeWidth = in.readInt();
        this.strokeColor = in.readInt();
        this.inputBackgroundColor = in.readInt();
        this.backgroundColor = in.readInt();
        this.textSize = in.readInt();
        this.textColor = in.readInt();
        this.inputType = in.readInt();
        this.gravity = in.readInt();
        this.isManualClose = in.readByte() != 0;
        this.text = in.readString();
        this.padding = in.createIntArray();
        this.styleText = in.readInt();
        this.maxLen = in.readInt();
        this.counterMargins = in.createIntArray();
        this.counterColor = in.readInt();
        this.showSoftKeyboard = in.readByte() != 0;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(this.margins);
        dest.writeInt(this.inputHeight);
        dest.writeString(this.hintText);
        dest.writeInt(this.hintTextColor);
        dest.writeInt(this.inputBackgroundResourceId);
        dest.writeInt(this.strokeWidth);
        dest.writeInt(this.strokeColor);
        dest.writeInt(this.inputBackgroundColor);
        dest.writeInt(this.backgroundColor);
        dest.writeInt(this.textSize);
        dest.writeInt(this.textColor);
        dest.writeInt(this.inputType);
        dest.writeInt(this.gravity);
        dest.writeByte(this.isManualClose ? (byte) 1 : (byte) 0);
        dest.writeString(this.text);
        dest.writeIntArray(this.padding);
        dest.writeInt(this.styleText);
        dest.writeInt(this.maxLen);
        dest.writeIntArray(this.counterMargins);
        dest.writeInt(this.counterColor);
        dest.writeByte(this.showSoftKeyboard ? (byte) 1 : (byte) 0);
    }
}
