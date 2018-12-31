package com.wecent.smartdialog.params;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.wecent.smartdialog.resource.values.SmartColor;
import com.wecent.smartdialog.resource.values.SmartDimen;

/**
 * items 内容参数
 * Created by wecent on 2017/3/30.
 */
public class ItemsParams implements Parcelable {

    public static final Creator<ItemsParams> CREATOR = new Creator<ItemsParams>() {
        @Override
        public ItemsParams createFromParcel(Parcel source) {
            return new ItemsParams(source);
        }

        @Override
        public ItemsParams[] newArray(int size) {
            return new ItemsParams[size];
        }
    };
    /**
     * 数据源：array or list
     */
    public Object items;
    /**
     * item高度
     */
    public int itemHeight = SmartDimen.ITEM_HEIGHT;
    public int dividerHeight = 1;
    /**
     * item内间距
     */
    public int[] padding;
    /**
     * item背景色
     */
    public int backgroundColor;
    /**
     * item字体色
     */
    public int textColor = SmartColor.ITEM_CONTENT_TEXT;
    /**
     * item字体大小
     */
    public int textSize = SmartDimen.ITEM_CONTENT_TEXT_SIZE;
    /**
     * 按下颜色值
     */
    public int backgroundColorPress;
    /**
     * 是否触发自动关闭对话框
     */
    public boolean isManualClose;
    /**
     * RecyclerView 适配器
     */
    public RecyclerView.Adapter adapter;
    /**
     * RecyclerView 布局管理
     */
    public RecyclerView.LayoutManager layoutManager;
    public int linearLayoutManagerOrientation = LinearLayoutManager.VERTICAL;
    /**
     * RecyclerView 分隔线
     */
    public RecyclerView.ItemDecoration itemDecoration;
    /**
     * 列表与底部按钮的距离
     */
    public int bottomMargin = -1;

    public ItemsParams() {
    }

    protected ItemsParams(Parcel in) {
        this.itemHeight = in.readInt();
        this.dividerHeight = in.readInt();
        this.padding = in.createIntArray();
        this.backgroundColor = in.readInt();
        this.textColor = in.readInt();
        this.textSize = in.readInt();
        this.backgroundColorPress = in.readInt();
        this.isManualClose = in.readByte() != 0;
        this.linearLayoutManagerOrientation = in.readInt();
        this.bottomMargin = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.itemHeight);
        dest.writeInt(this.dividerHeight);
        dest.writeIntArray(this.padding);
        dest.writeInt(this.backgroundColor);
        dest.writeInt(this.textColor);
        dest.writeInt(this.textSize);
        dest.writeInt(this.backgroundColorPress);
        dest.writeByte(this.isManualClose ? (byte) 1 : (byte) 0);
        dest.writeInt(this.linearLayoutManagerOrientation);
        dest.writeInt(this.bottomMargin);
    }
}
