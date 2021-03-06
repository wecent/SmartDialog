package com.wecent.smartdialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wecent.smartdialog.widget.BodyInputView;
import com.wecent.smartdialog.widget.BodyItemsView;
import com.wecent.smartdialog.widget.BodyProgressView;
import com.wecent.smartdialog.widget.BodyMessageView;
import com.wecent.smartdialog.widget.SmartDividerView;
import com.wecent.smartdialog.widget.SmartMultipleButton;
import com.wecent.smartdialog.widget.SmartSingleButton;
import com.wecent.smartdialog.widget.SmartTitleView;
import com.wecent.smartdialog.widget.listener.ButtonView;
import com.wecent.smartdialog.widget.listener.InputView;
import com.wecent.smartdialog.widget.listener.ItemsView;

/**
 * desc: BuildViewImpl
 * author: wecent
 * date: 2018/3/29
 */
public final class BuildViewImpl implements BuildView {

    private Context mContext;
    private SmartParams mParams;
    private LinearLayout mRoot;
    private SmartTitleView mSmartTitleView;
    private BodyMessageView mBodyTextView;
    private ItemsView mItemsView;
    private BodyProgressView mBodyProgressView;
    private InputView mBodyInputView;
    private SmartSingleButton mSmartSingleButton;
    private ButtonView mMultipleButton;
    private View mCustomView;

    public BuildViewImpl(Context context, SmartParams params) {
        this.mContext = context;
        this.mParams = params;
    }

    @Override
    public View buildRoot() {
        if (mRoot == null) {
            mRoot = new LinearLayout(mContext);
            mRoot.setOrientation(LinearLayout.VERTICAL);
        }
        return mRoot;
    }

    @Override
    public View buildTitle() {
        if (mSmartTitleView == null) {
            mSmartTitleView = new SmartTitleView(mContext, mParams);
            mRoot.addView(mSmartTitleView);
            if (mParams.itemListener != null) {
                SmartDividerView smartDividerView = new SmartDividerView(mContext, true);
                mRoot.addView(smartDividerView);
            }
        }
        return mSmartTitleView;
    }

    @Override
    public View buildCustom() {
        if (mCustomView == null) {
            View bodyView = LayoutInflater.from(mContext).inflate(mParams.customViewId, mRoot, false);
            this.mCustomView = bodyView;
            mRoot.addView(mCustomView);
        }
        return mCustomView;
    }

    @Override
    public View buildMessage() {
        if (mBodyTextView == null) {
            mBodyTextView = new BodyMessageView(mContext, mParams);
            mRoot.addView(mBodyTextView);
        }
        return mBodyTextView;
    }

    @Override
    public View refreshText() {
        if (mBodyTextView != null) mBodyTextView.refreshText();
        return mBodyTextView;
    }

    @Override
    public ItemsView buildItems() {
        if (mItemsView == null) {
            mItemsView = new BodyItemsView(mContext, mParams);
            mRoot.addView(mItemsView.getView());
        }
        return mItemsView;
    }

    @Override
    public ButtonView buildItemsButton() {
        if (mSmartSingleButton == null) {
            mSmartSingleButton = new SmartSingleButton(mContext, mParams);
            mRoot.addView(mSmartSingleButton);
        }
        return mSmartSingleButton;
    }

    @Override
    public ItemsView refreshItems() {
        if (mItemsView != null) mItemsView.refreshItems();
        return mItemsView;
    }

    @Override
    public View buildProgress() {
        if (mBodyProgressView == null) {
            mBodyProgressView = new BodyProgressView(mContext, mParams);
            mRoot.addView(mBodyProgressView);
        }
        return mBodyProgressView;
    }

    @Override
    public View refreshProgress() {
        if (mBodyProgressView != null) mBodyProgressView.refreshProgress();
        return mBodyProgressView;
    }

    @Override
    public InputView buildInput() {
        if (mBodyInputView == null) {
            mBodyInputView = new BodyInputView(mContext, mParams);
            mRoot.addView(mBodyInputView.getView());
        }
        return mBodyInputView;
    }

    @Override
    public ButtonView buildMultipleButton() {
        if (mMultipleButton == null) {
            mMultipleButton = new SmartMultipleButton(mContext, mParams);
            if (!mMultipleButton.isEmpty()) {
                SmartDividerView smartDividerView = new SmartDividerView(mContext, true);
                mRoot.addView(smartDividerView);
            }
            mRoot.addView(mMultipleButton.getView());
        }
        return mMultipleButton;
    }

    @Override
    public ButtonView refreshMultipleButtonText() {
        if (mMultipleButton != null)
            mMultipleButton.refreshText();
        return mMultipleButton;
    }

    @Override
    public View getView() {
        return mRoot;
    }

    @Override
    public InputView getInputView() {
        return mBodyInputView;
    }
}
