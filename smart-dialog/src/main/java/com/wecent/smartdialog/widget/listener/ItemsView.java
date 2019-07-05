package com.wecent.smartdialog.widget.listener;

import android.view.View;

/**
 * desc: ItemsView
 * author: wecent
 * date: 2018/3/29
 */
public interface ItemsView {
    void refreshItems();

    void regOnItemClickListener(OnItemsClickListener listener);

    View getView();
}
