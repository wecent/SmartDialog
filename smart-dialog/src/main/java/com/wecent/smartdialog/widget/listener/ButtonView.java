package com.wecent.smartdialog.widget.listener;

import android.view.View;

/**
 * Created by wecent on 2018/5/21.
 */

public interface ButtonView {

    void regNegativeListener(View.OnClickListener onClickListener);

    void regPositiveListener(View.OnClickListener onClickListener);

    void regNeutralListener(View.OnClickListener onClickListener);

    void refreshText();

    View getView();

    boolean isEmpty();
}
