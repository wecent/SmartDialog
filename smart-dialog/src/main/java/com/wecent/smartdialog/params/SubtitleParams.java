package com.wecent.smartdialog.params;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;

import com.wecent.smartdialog.resource.values.SmartColor;
import com.wecent.smartdialog.resource.values.SmartDimen;

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
     * 副标题文字
     */
    public String text;
    /**
     * 副标题文字内间距 [left, top, right, bottom]
     */
    public int[] padding = SmartDimen.SUBTITLE_PADDING;
    /**
     * 副标题高度
     */
    public int height;
    /**
     * 副标题字体大小
     */
    public int textSize = SmartDimen.SUBTITLE_TEXT_SIZE;
    /**
     * 副标题字体颜色
     */
    public int textColor = SmartColor.SUBTITLE_TEXT;
    /**
     * 副标题背景颜色
     */
    public int backgroundColor;
    /**
     * 副标题权重位置
     */
    public int gravity = Gravity.CENTER;
    /**
     * 副标题字体样式
     * {@linkplain Typeface#NORMAL 默认}
     * {@linkplain Typeface#BOLD 加粗}
     * {@linkplain Typeface#ITALIC 斜体}
     * {@linkplain Typeface#BOLD_ITALIC 加粗并斜体}
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
