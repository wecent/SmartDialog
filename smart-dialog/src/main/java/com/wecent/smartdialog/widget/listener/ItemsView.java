package com.wecent.smartdialog.widget.listener;

import android.view.View;

/**
 * Created by wecent on 2018/4/18.
 */

public interface ItemsView {
    void refreshItems();

    void regOnItemClickListener(OnItemsClickListener listener);

    View getView();
}
