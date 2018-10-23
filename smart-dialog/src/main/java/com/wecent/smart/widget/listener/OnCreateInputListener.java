package com.wecent.smart.widget.listener;

import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by wecent on 2018/6/13.
 */

public interface OnCreateInputListener {
    void onCreateText(RelativeLayout inputLayout, EditText inputView, TextView counterView);
}
