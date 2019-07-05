package com.wecent.smartdialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.wecent.smartdialog.params.DialogParams;

/**
 * desc: AbsSmartDialog
 * author: wecent
 * date: 2018/3/29
 */
public final class AbsSmartDialog extends BaseSmartDialog implements DialogInterface.OnShowListener {
    private static final String SAVED_PARAMS = "smart:params";
    private SmartParams mParams;
    private SmartController mSmartController;

    public AbsSmartDialog() {
    }

    public static AbsSmartDialog newAbsSmartDialog(SmartParams params) {
        AbsSmartDialog smartDialog = new AbsSmartDialog();
        smartDialog.mParams = params;
        Bundle bundle = new Bundle();
        bundle.putParcelable(SAVED_PARAMS, params);
        smartDialog.setArguments(bundle);
        return smartDialog;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mParams != null && mParams.inputParams != null && mParams.inputParams.showSoftKeyboard
                && mSmartController != null) {
            EditText editText = mSmartController.getInputEdit();
            if (editText != null) showSoftInputView(editText);
        }
    }

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        mSmartController = new SmartController(getContext().getApplicationContext(), mParams, this);
        mSmartController.createView();
        View view = mSmartController.getView();
        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            mParams = savedInstanceState.getParcelable(SAVED_PARAMS);
        }
        DialogParams dialogParams = mParams.dialogParams;
        setGravity(dialogParams.gravity);
        setCanceledOnTouchOutside(dialogParams.canceledOnTouchOutside);
        setCanceledBack(dialogParams.cancelable);
        setWidth(dialogParams.width);
        setMaxHeight(dialogParams.maxHeight);
        int[] mPadding = dialogParams.mPadding;
        if (mPadding != null)
            setPadding(mPadding[0], mPadding[1], mPadding[2], mPadding[3]);
        setAnimations(dialogParams.animStyle);
        setDimEnabled(dialogParams.isDimEnabled);
        //setBackgroundColor(dialogParams.backgroundColor);
        setRadius(dialogParams.radius);
        setAlpha(dialogParams.alpha);
        setX(dialogParams.xOff);
        setY(dialogParams.yOff);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (mParams != null) {
            if (mParams.dismissListener != null)
                mParams.dismissListener.onDismiss(dialog);
            if (mParams.cancelListener != null)
                mParams.cancelListener.onCancel(dialog);
        }
        mParams = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(SAVED_PARAMS, mParams);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.setOnShowListener(this);
        }
    }

    @Override
    public void onShow(DialogInterface dialog) {
        if (mParams != null && mParams.showListener != null) {
            mParams.showListener.onShow(dialog);
        }
    }

    public void refreshView() {
        mSmartController.refreshView();
    }
}
