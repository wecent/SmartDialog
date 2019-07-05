package com.wecent.smartdialog.widget.listener;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * desc: OnCreateInputListener
 * author: wecent
 * date: 2018/3/29
 */
public interface OnCreateInputListener {
    void onCreateText(RelativeLayout inputLayout, EditText inputView, TextView counterView);
}
