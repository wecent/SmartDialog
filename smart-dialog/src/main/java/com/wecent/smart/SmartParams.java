package com.wecent.smart;

import android.content.DialogInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.view.View;

import com.wecent.smart.params.ButtonParams;
import com.wecent.smart.params.DialogParams;
import com.wecent.smart.params.InputParams;
import com.wecent.smart.params.ItemsParams;
import com.wecent.smart.params.LottieParams;
import com.wecent.smart.params.ProgressParams;
import com.wecent.smart.params.SubtitleParams;
import com.wecent.smart.params.MessageParams;
import com.wecent.smart.params.TitleParams;
import com.wecent.smart.widget.listener.OnCreateButtonListener;
import com.wecent.smart.widget.listener.OnCreateInputListener;
import com.wecent.smart.widget.listener.OnCreateLottieListener;
import com.wecent.smart.widget.listener.OnCreateProgressListener;
import com.wecent.smart.widget.listener.OnCreateBodyViewListener;
import com.wecent.smart.widget.listener.OnCreateMessageListener;
import com.wecent.smart.widget.listener.OnCreateTitleListener;
import com.wecent.smart.widget.listener.OnInputClickListener;
import com.wecent.smart.widget.listener.OnInputCounterChangeListener;
import com.wecent.smart.widget.listener.OnItemClickListener;

/**
 * desc: .
 * author: wecent .
 * date: 2017/3/30 .
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
    public OnItemClickListener itemListener;
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
    public LottieParams lottieParams;
    public InputParams inputParams;
    public ButtonParams neutralParams;
    public int bodyViewId;
    public OnCreateBodyViewListener createBodyViewListener;
    public OnCreateProgressListener createProgressListener;
    public OnCreateLottieListener createLottieListener;
    public OnCreateTitleListener createTitleListener;
    public OnCreateMessageListener createMessageListener;
    public OnCreateInputListener createInputListener;
    public OnCreateButtonListener createButtonListener;
    public OnInputCounterChangeListener inputCounterChangeListener;

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
        dest.writeParcelable(this.lottieParams, flags);
        dest.writeParcelable(this.inputParams, flags);
        dest.writeParcelable(this.neutralParams, flags);
        dest.writeInt(this.bodyViewId);
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
        this.lottieParams = in.readParcelable(LottieParams.class.getClassLoader());
        this.inputParams = in.readParcelable(InputParams.class.getClassLoader());
        this.neutralParams = in.readParcelable(ButtonParams.class.getClassLoader());
        this.bodyViewId = in.readInt();
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
