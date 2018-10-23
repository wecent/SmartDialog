package com.wecent.smart.widget.listener;

import android.widget.TextView;

/**
 * Created by wecent on 2018/6/13.
 */

public interface OnCreateButtonListener {
    /**
     * @param negativeButton 取消
     * @param positiveButton 确定
     * @param neutralButton  中间
     */
    void onCreateButton(TextView negativeButton, TextView positiveButton, TextView neutralButton);
}
