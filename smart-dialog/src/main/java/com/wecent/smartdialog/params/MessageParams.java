package com.wecent.smartdialog.params;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;

import com.wecent.smartdialog.resource.values.SmartColor;
import com.wecent.smartdialog.resource.values.SmartDimen;

/**
 * desc: MessageParams
 * author: wecent
 * date: 2018/3/30
 */
public class MessageParams implements Parcelable {

    public static final Creator<MessageParams> CREATOR = new Creator<MessageParams>() {
        @Override
        public MessageParams createFromParcel(Parcel source) {
            return new MessageParams(source);
        }

        @Override
        public MessageParams[] newArray(int size) {
            return new MessageParams[size];
        }
    };

    /**
     * body文本内间距 [left, top, right, bottom]
     */
    public int[] padding = SmartDimen.MESSAGE_PADDING;
    /**
     * 文本
     */
    public String message;
    /**
     * 文本高度
     */
    public int height = SmartDimen.MESSAGE_HEIGHT;
    /**
     * 文本背景颜色
     */
    public int backgroundColor;
    /**
     * 文本字体颜色
     */
    public int messageColor = SmartColor.CONTENT;
    /**
     * 文本字体大小
     */
    public int messageSize = SmartDimen.MESSAGE_TEXT_SIZE;
    public int gravity = Gravity.CENTER;
    /**
     * 字样式
     * {@linkplain Typeface#NORMAL NORMAL}
     * {@linkplain Typeface#BOLD BOLD}
     * {@linkplain Typeface#ITALIC ITALIC}
     * {@linkplain Typeface#BOLD_ITALIC BOLD_ITALIC}
     */
    public int styleText = Typeface.NORMAL;

    public MessageParams() {
    }

    protected MessageParams(Parcel in) {
        this.padding = in.createIntArray();
        this.message = in.readString();
        this.height = in.readInt();
        this.backgroundColor = in.readInt();
        this.messageColor = in.readInt();
        this.messageSize = in.readInt();
        this.gravity = in.readInt();
        this.styleText = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeIntArray(this.padding);
        dest.writeString(this.message);
        dest.writeInt(this.height);
        dest.writeInt(this.backgroundColor);
        dest.writeInt(this.messageColor);
        dest.writeInt(this.messageSize);
        dest.writeInt(this.gravity);
        dest.writeInt(this.styleText);
    }
}
