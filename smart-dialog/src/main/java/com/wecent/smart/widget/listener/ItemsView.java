package com.wecent.smart.widget.listener;

import android.view.View;
import android.widget.AdapterView;

/**
 * Created by wecent on 2018/4/18.
 */

public interface ItemsView {
    void refreshItems();

    void regOnItemClickListener(OnItemClickListener listener);

    View getView();
}
