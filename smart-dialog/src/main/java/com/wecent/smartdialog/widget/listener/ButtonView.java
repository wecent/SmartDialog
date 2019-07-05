package com.wecent.smartdialog.widget.listener;

import android.view.View;

/**
 * desc: ButtonView
 * author: wecent
 * date: 2018/3/29
 */
public interface ButtonView {

    void regNegativeListener(View.OnClickListener onClickListener);

    void regPositiveListener(View.OnClickListener onClickListener);

    void regNeutralListener(View.OnClickListener onClickListener);

    void refreshText();

    View getView();

    boolean isEmpty();
}
