package com.wecent.sample;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.wecent.smartdialog.BaseSmartDialog;
import com.wecent.smartdialog.sample.R;

/**
 * 注销框
 * Created by wecent on 2017/4/5.
 */
public class CustomDialog extends BaseSmartDialog implements View.OnClickListener {

    public static CustomDialog getInstance() {
        CustomDialog dialogFragment = new CustomDialog();
        dialogFragment.setCanceledBack(false);
        dialogFragment.setCanceledOnTouchOutside(false);
        dialogFragment.setGravity(Gravity.BOTTOM);
        dialogFragment.setWidth(1f);
        return dialogFragment;
    }

    @Override
    public View createView(Context context, LayoutInflater inflater, ViewGroup container) {
        return inflater.inflate(com.wecent.smartdialog.sample.R.layout.dialog_logout, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        view.findViewById(com.wecent.smartdialog.sample.R.id.but_cancle).setOnClickListener(this);
        view.findViewById(com.wecent.smartdialog.sample.R.id.logout_ok).setOnClickListener(this);
        view.findViewById(com.wecent.smartdialog.sample.R.id.logout_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.logout_ok) {
            //注销逻辑
        } else {
            dismiss();
        }
    }

}
