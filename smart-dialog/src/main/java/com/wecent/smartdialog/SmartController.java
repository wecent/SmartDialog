package com.wecent.smartdialog;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;

import com.wecent.smartdialog.widget.listener.ButtonView;
import com.wecent.smartdialog.widget.listener.InputView;
import com.wecent.smartdialog.widget.listener.ItemsView;
import com.wecent.smartdialog.widget.listener.OnItemsClickListener;

/**
 * desc: SmartController
 * author: wecent
 * date: 2018/3/30
 */
public class SmartController {

    public static final int MSG_DISMISS_DIALOG = -1;
    /**
     * The identifier for the positive button.
     */
    public static final int BUTTON_POSITIVE = -2;
    /**
     * The identifier for the negative button.
     */
    public static final int BUTTON_NEGATIVE = -3;
    /**
     * The identifier for the neutral button.
     */
    public static final int BUTTON_NEUTRAL = -4;

    private Context mContext;
    private SmartParams mParams;
    private BuildView mCreateView;
    private ButtonHandler mHandler;
    private BaseSmartDialog mDialog;

    public SmartController(Context context, SmartParams params, BaseSmartDialog mDialog) {
        this.mContext = context;
        this.mParams = params;
        this.mDialog = mDialog;
        mHandler = new ButtonHandler();
        mCreateView = new BuildViewImpl(mContext, mParams);
    }

    public void createView() {
        applyRoot();
        applyHeader();
        applyBody();
    }

    private void applyRoot() {
        mCreateView.buildRoot();
    }

    private void applyHeader() {
        if (mParams.titleParams != null)
            mCreateView.buildTitle();
    }

    private void applyBody() {
        // 自定义
        if (mParams.customViewId != 0) {
            View bodyView = mCreateView.buildCustom();
            ButtonView buttonView = mCreateView.buildMultipleButton();
            applyButton(buttonView, null);
            if (mParams.createBodyViewListener != null)
                mParams.createBodyViewListener.onCreateCustom(bodyView);
        }
        // 消息
        else if (mParams.messageParams != null) {
            mCreateView.buildMessage();
            ButtonView buttonView = mCreateView.buildMultipleButton();
            applyButton(buttonView, null);
        }
        // 列表
        else if (mParams.itemsParams != null) {
            final ItemsView itemsView = mCreateView.buildItems();
//            if (mParams.itemListener != null) {
//                itemsView.regOnItemClickListener(new AdapterView.OnItemsClickListener() {
//                    @Override
//                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                        mHandler.obtainMessage(position, itemsView).sendToTarget();
//                        if (!mParams.itemsParams.isManualClose)
//                            mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog).sendToTarget();
//                    }
//                });
//            } else
            if (mParams.itemListener != null) {
                itemsView.regOnItemClickListener(new OnItemsClickListener() {
                    @Override
                    public void onItemsClick(View view, int position) {
                        mHandler.obtainMessage(position, itemsView).sendToTarget();
                        if (!mParams.itemsParams.isManualClose)
                            mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog).sendToTarget();
                    }
                });
            }
            final ButtonView itemsButton = mCreateView.buildItemsButton();
            applyButton(itemsButton, null);
        }
        // 进度条
        else if (mParams.progressParams != null) {
            mCreateView.buildProgress();
            ButtonView buttonView = mCreateView.buildMultipleButton();
            applyButton(buttonView, null);
        }
        // 输入框
        else if (mParams.inputParams != null) {
            InputView inputView = mCreateView.buildInput();
            ButtonView buttonView = mCreateView.buildMultipleButton();
            applyButton(buttonView, (View) inputView);
        }
    }

    private void applyButton(final ButtonView viewButton, final View viewClick) {
        viewButton.regNegativeListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.obtainMessage(BUTTON_NEGATIVE, viewClick == null
                        ? viewButton : viewClick).sendToTarget();
                mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog)
                        .sendToTarget();
            }
        });
        viewButton.regPositiveListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.obtainMessage(BUTTON_POSITIVE, viewClick == null
                        ? viewButton : viewClick).sendToTarget();
                if (mParams.inputParams == null || !mParams.inputParams.isManualClose) {
                    mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog).sendToTarget();
                }
            }
        });
        viewButton.regNeutralListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mHandler.obtainMessage(BUTTON_NEUTRAL, viewClick == null
                        ? viewButton : viewClick).sendToTarget();
                mHandler.obtainMessage(MSG_DISMISS_DIALOG, mDialog)
                        .sendToTarget();
            }
        });
    }

    public EditText getInputEdit() {
        if (mCreateView == null) return null;
        return mCreateView.getInputView().getInput();
    }

    public void refreshView() {
        mCreateView.refreshText();
        mCreateView.refreshItems();
        mCreateView.refreshProgress();
        mCreateView.refreshMultipleButtonText();
        //刷新时带动画
        if (mParams.dialogParams.refreshAnimation != 0 && getView() != null)
            getView().post(new Runnable() {
                @Override
                public void run() {
                    Animation animation = AnimationUtils.loadAnimation(mContext, mParams
                            .dialogParams
                            .refreshAnimation);
                    if (animation != null) getView().startAnimation(animation);
                }
            });
    }

    public View getView() {
        return mCreateView.getView();
    }

    /**
     * Interface used to allow the creator of a dialog to run some code when an
     * item on the dialog is clicked..
     */
    public interface OnClickListener {
        /**
         * dialog中可以点击的空间需要继承的接口，通过这个接口调用各自的监听事件
         *
         * @param view  实现了OnClickListener的view
         * @param which 点击事件对应的id，如果是列表中的item 则是对应的下标
         */
        void onClick(View view, int which);
    }

    public static class ButtonHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case BUTTON_POSITIVE:
                case BUTTON_NEGATIVE:
                case BUTTON_NEUTRAL:
                    ((OnClickListener) msg.obj).onClick((View) msg.obj, msg.what);
                    break;
                case MSG_DISMISS_DIALOG:
                    ((BaseSmartDialog) msg.obj).dismiss();
                    break;
                default:
                    ((OnClickListener) msg.obj).onClick((View) msg.obj, msg.what);
                    break;
            }
        }
    }
}
