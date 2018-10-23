package com.wecent.smart.params;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;

import com.wecent.smart.resource.values.CircleColor;
import com.wecent.smart.resource.values.CircleDimen;

/**
 * 副标题参数
 * Created by wecent on 2018/4/12.
 */
public class SubtitleParams implements Parcelable {
    public static final Creator<SubtitleParams> CREATOR = new Creator<SubtitleParams>() {
        @Override
        public SubtitleParams createFromParcel(Parcel source) {
            return new SubtitleParams(source);
        }

        @Override
        public SubtitleParams[] newArray(int size) {
            return new SubtitleParams[size];
        }
    };
    /**
     * 标题
     */
    public String text;
    /**
     * 内间距 [left, top, right, bottom]
     */
    public int[] padding = CircleDimen.SUBTITLE_PADDING;
    /**
     * 标题高度
     */
    public int height;
    /**
     * 标题字体大小
     */
    public int textSize = CircleDimen.SUBTITLE_TEXT_SIZE;
    /**
     * 标题字体颜色
     */
    public int textColor = CircleColor.SUBTITLE_TEXT;
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

    public SubtitleParams() {
    }

    protected SubtitleParams(Parcel in) {
        this.text = in.readString();
        this.padding = in.createIntArray();
        this.height = in.readInt();
        this.textSize = in.readInt();
        this.textColor = in.readInt();
        this.backgroundColor = in.readInt();
        this.gravity = in.readInt();
        this.styleText = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeIntArray(this.padding);
        dest.writeInt(this.height);
        dest.writeInt(this.textSize);
        dest.writeInt(this.textColor);
        dest.writeInt(this.backgroundColor);
        dest.writeInt(this.gravity);
        dest.writeInt(this.styleText);
    }
}
