package com.wecent.smart.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.wecent.smart.SmartParams;
import com.wecent.smart.widget.listener.ButtonView;
import com.wecent.smart.widget.listener.InputView;
import com.wecent.smart.widget.listener.ItemsView;

/**
 * Created by wecent on 2017/3/29.
 */

public final class BuildViewImpl implements BuildView {
    private Context mContext;
    private SmartParams mParams;
    private LinearLayout mRoot;
    private TitleView mTitleView;
    private BodyTextView mBodyTextView;
    private ItemsView mItemsView;
    private BodyProgressView mBodyProgressView;
    private BodyLottieView mBodyLottieView;
    private InputView mBodyInputView;
    private ItemsButton mItemsButton;
    private ButtonView mMultipleButton;
    private View mCustomView;

    public BuildViewImpl(Context context, SmartParams params) {
        this.mContext = context;
        this.mParams = params;
    }

    @Override
    public View buildRoot() {
        if (mRoot == null) {
            mRoot = new ScaleLinearLayout(mContext);
            mRoot.setOrientation(LinearLayout.VERTICAL);
        }
        return mRoot;
    }

    @Override
    public View buildTitle() {
        if (mTitleView == null) {
            mTitleView = new TitleView(mContext, mParams);
            mRoot.addView(mTitleView);
        }
        return mTitleView;
    }

    @Override
    public View buildCustom() {
        if (mCustomView == null) {
            View bodyView = LayoutInflater.from(mContext).inflate(mParams.bodyViewId, mRoot, false);
            this.mCustomView = bodyView;
            mRoot.addView(mCustomView);
        }
        return mCustomView;
    }

    @Override
    public View buildText() {
        if (mBodyTextView == null) {
            mBodyTextView = new BodyTextView(mContext, mParams);
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
            if (mParams.itemListener != null || mParams.itemsParams.adapter != null)
                mItemsView = new BodyItemsView(mContext, mParams);
            else if (mParams.rvItemListener != null || mParams.itemsParams.adapterRv != null)
                mItemsView = new BodyItemsRvView(mContext, mParams);

            mRoot.addView(mItemsView.getView());
        }
        return mItemsView;
    }

    @Override
    public ButtonView buildItemsButton() {
        if (mItemsButton == null) {
            mItemsButton = new ItemsButton(mContext, mParams);
            mRoot.addView(mItemsButton);
        }
        return mItemsButton;
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
    public void buildLottie() {
        if (mBodyLottieView == null) {
            mBodyLottieView = new BodyLottieView(mContext, mParams);
            mRoot.addView(mBodyLottieView);
        }
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
            mMultipleButton = new MultipleButton(mContext, mParams);
            if (!mMultipleButton.isEmpty()) {
                DividerView dividerView = new DividerView(mContext);
                dividerView.setVertical();
                mRoot.addView(dividerView);
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
