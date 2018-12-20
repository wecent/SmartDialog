package com.wecent.smart.params;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;

import com.wecent.smart.resource.values.SmartColor;
import com.wecent.smart.resource.values.SmartDimen;

/**
 * 标题参数
 * Created by wecent on 2017/3/30.
 */
public class TitleParams implements Parcelable {

    public static final Creator<TitleParams> CREATOR = new Creator<TitleParams>() {
        @Override
        public TitleParams createFromParcel(Parcel source) {
            return new TitleParams(source);
        }

        @Override
        public TitleParams[] newArray(int size) {
            return new TitleParams[size];
        }
    };
    /**
     * 标题
     */
    public String text;
    /**
     * 标题高度
     */
    public int height = SmartDimen.TITLE_HEIGHT;
    /**
     * 标题字体大小
     */
    public int textSize = SmartDimen.TITLE_TEXT_SIZE;
    /**
     * 标题字体颜色
     */
    public int textColor = SmartColor.TITLE;
    /**
     * 标题背景颜色
     */
    public int backgroundColor;
    public int gravity = Gravity.CENTER;
    /**
     * 字样式
     * {@linkplain Typeface#NORMAL NORMAL}
     * {@linkplain Typeface#BOLD BOLD}
     * {@linkplain Typeface#ITALIC ITALIC}
     * {@linkplain Typeface#BOLD_ITALIC BOLD_ITALIC}
     */
    public int styleText = Typeface.NORMAL;
    public int icon;

    public TitleParams() {
    }

    protected TitleParams(Parcel in) {
        this.text = in.readString();
        this.height = in.readInt();
        this.textSize = in.readInt();
        this.textColor = in.readInt();
        this.backgroundColor = in.readInt();
        this.gravity = in.readInt();
        this.styleText = in.readInt();
        this.icon = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeInt(this.height);
        dest.writeInt(this.textSize);
        dest.writeInt(this.textColor);
        dest.writeInt(this.backgroundColor);
        dest.writeInt(this.gravity);
        dest.writeInt(this.styleText);
        dest.writeInt(this.icon);
    }
}
