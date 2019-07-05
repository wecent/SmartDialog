package com.wecent.smartdialog.params;

import android.graphics.Typeface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.Gravity;

import com.wecent.smartdialog.resource.values.SmartColor;
import com.wecent.smartdialog.resource.values.SmartDimen;

/**
 * desc: TitleParams
 * author: wecent
 * date: 2018/3/30
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
     * 标题文本
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
    /**
     * 标题权重位置
     */
    public int gravity = Gravity.CENTER;
    /**
     * 标题字体样式
     * {@linkplain Typeface#NORMAL 默认}
     * {@linkplain Typeface#BOLD 加粗}
     * {@linkplain Typeface#ITALIC 斜体}
     * {@linkplain Typeface#BOLD_ITALIC 加粗并斜体}
     */
    public int styleText = Typeface.NORMAL;
    /**
     * 标题左边图标
     */
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
