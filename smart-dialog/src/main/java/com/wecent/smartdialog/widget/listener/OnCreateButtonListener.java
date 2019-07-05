package com.wecent.smartdialog.widget.listener;

import android.widget.TextView;

/**
 * desc: OnCreateButtonListener
 * author: wecent
 * date: 2018/3/29
 */
public interface OnCreateButtonListener {
    /**
     * @param negativeButton 取消
     * @param positiveButton 确定
     * @param neutralButton  中间
     */
    void onCreateButton(TextView negativeButton, TextView positiveButton, TextView neutralButton);
}
