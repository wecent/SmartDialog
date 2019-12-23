package com.wecent.smartdialog;

import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.wecent.smartdialog.params.ButtonParams;
import com.wecent.smartdialog.params.DialogParams;
import com.wecent.smartdialog.params.InputParams;
import com.wecent.smartdialog.params.ItemsParams;
import com.wecent.smartdialog.params.ProgressParams;
import com.wecent.smartdialog.params.SubtitleParams;
import com.wecent.smartdialog.params.MessageParams;
import com.wecent.smartdialog.params.TitleParams;
import com.wecent.smartdialog.widget.listener.OnCreateButtonListener;
import com.wecent.smartdialog.widget.listener.OnCreateInputListener;
import com.wecent.smartdialog.widget.listener.OnCreateProgressListener;
import com.wecent.smartdialog.widget.listener.OnCreateCustomListener;
import com.wecent.smartdialog.widget.listener.OnCreateMessageListener;
import com.wecent.smartdialog.widget.listener.OnCreateTitleListener;
import com.wecent.smartdialog.widget.listener.OnInputClickListener;
import com.wecent.smartdialog.widget.listener.OnInputChangeListener;
import com.wecent.smartdialog.widget.listener.OnItemsClickListener;

/**
 * desc:
 * author: wecent
 * date: 2018/3/30
 */
public class SmartParams implements Parcelable {

    /**
     * 确定按钮点击事件
     */
    public View.OnClickListener clickPositiveListener;
    /**
     * 中间按钮点击事件
     */
    public View.OnClickListener clickNeutralListener;
    /**
     * 取消按钮点击事件
     */
    public View.OnClickListener clickNegativeListener;
    /**
     * 输入框确定事件
     */
    public OnInputClickListener inputListener;
    /**
     * RecyclerView Item点击事件
     */
    public OnItemsClickListener itemListener;
    /**
     * dialog 关闭事件
     */
    public DialogInterface.OnDismissListener dismissListener;
    /**
     * dialog 取消事件
     */
    public DialogInterface.OnCancelListener cancelListener;
    /**
     * dialog 显示事件
     */
    public DialogInterface.OnShowListener showListener;

    public DialogParams dialogParams;
    public TitleParams titleParams;
    public SubtitleParams subtitleParams;
    public MessageParams messageParams;
    public ButtonParams negativeParams;
    public ButtonParams positiveParams;
    public ItemsParams itemsParams;
    public ProgressParams progressParams;
    public InputParams inputParams;
    public ButtonParams neutralParams;
    public int customViewId;
    public OnCreateCustomListener createCustomListener;
    public OnCreateProgressListener createProgressListener;
    public OnCreateTitleListener createTitleListener;
    public OnCreateMessageListener createMessageListener;
    public OnCreateInputListener createInputListener;
    public OnCreateButtonListener createButtonListener;
    public OnInputChangeListener inputCounterChangeListener;

    public SmartParams() {

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.dialogParams, flags);
        dest.writeParcelable(this.titleParams, flags);
        dest.writeParcelable(this.subtitleParams, flags);
        dest.writeParcelable(this.messageParams, flags);
        dest.writeParcelable(this.negativeParams, flags);
        dest.writeParcelable(this.positiveParams, flags);
        dest.writeParcelable(this.itemsParams, flags);
        dest.writeParcelable(this.progressParams, flags);
        dest.writeParcelable(this.inputParams, flags);
        dest.writeParcelable(this.neutralParams, flags);
        dest.writeInt(this.customViewId);
    }

    protected SmartParams(Parcel in) {
        this.dialogParams = in.readParcelable(DialogParams.class.getClassLoader());
        this.titleParams = in.readParcelable(TitleParams.class.getClassLoader());
        this.subtitleParams = in.readParcelable(SubtitleParams.class.getClassLoader());
        this.messageParams = in.readParcelable(MessageParams.class.getClassLoader());
        this.negativeParams = in.readParcelable(ButtonParams.class.getClassLoader());
        this.positiveParams = in.readParcelable(ButtonParams.class.getClassLoader());
        this.itemsParams = in.readParcelable(ItemsParams.class.getClassLoader());
        this.progressParams = in.readParcelable(ProgressParams.class.getClassLoader());
        this.inputParams = in.readParcelable(InputParams.class.getClassLoader());
        this.neutralParams = in.readParcelable(ButtonParams.class.getClassLoader());
        this.customViewId = in.readInt();
    }

    public static final Creator<SmartParams> CREATOR = new Creator<SmartParams>() {
        @Override
        public SmartParams createFromParcel(Parcel source) {
            return new SmartParams(source);
        }

        @Override
        public SmartParams[] newArray(int size) {
            return new SmartParams[size];
        }
    };
}
