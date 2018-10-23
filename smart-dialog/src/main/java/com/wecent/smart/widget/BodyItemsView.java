package com.wecent.smart.widget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.wecent.smart.SmartParams;
import com.wecent.smart.params.ItemsParams;
import com.wecent.smart.params.TitleParams;
import com.wecent.smart.resource.drawable.SelectorBtn;
import com.wecent.smart.resource.values.CircleColor;
import com.wecent.smart.resource.values.CircleDimen;
import com.wecent.smart.scale.ScaleHelper;
import com.wecent.smart.widget.listener.ItemsView;
import com.wecent.smart.widget.listener.OnRvItemClickListener;

import java.util.Arrays;
import java.util.List;

/**
 * Created by wecent on 2017/3/30.
 */

final class BodyItemsView extends ListView implements Controller.OnClickListener, ItemsView {
    private BaseAdapter mAdapter;
    private SmartParams mParams;
    private TitleParams mTitleParams;
    private int mRadius;
    private int mBackgroundColor;
    private int mBackgroundColorPress;
    private SelectorBtn bgItemAllRadius;
    private SelectorBtn bgItemTopRadius;
    private SelectorBtn bgItemBottomRadius;
    private SelectorBtn bgItemNoRadius;

    public BodyItemsView(Context context, SmartParams params) {
        super(context);
        init(context, params);
    }

    private void init(Context context, final SmartParams params) {
        this.mParams = params;
        this.mTitleParams = params.titleParams;
        ItemsParams itemsParams = params.itemsParams;

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams
                .MATCH_PARENT, LayoutParams
                .MATCH_PARENT, 1);

        //设置列表与按钮之间的下距离
        if (itemsParams.bottomMargin == -1) {
            itemsParams.bottomMargin = CircleDimen.BUTTON_ITEMS_MARGIN;
        }
        layoutParams.bottomMargin = ScaleHelper.scaleValue(itemsParams.bottomMargin);
        setLayoutParams(layoutParams);

        this.mRadius = mParams.dialogParams.radius;
        //如果没有背景色，则使用默认色
        this.mBackgroundColor = itemsParams.backgroundColor != 0
                ? itemsParams.backgroundColor : mParams.dialogParams.backgroundColor;
        this.mBackgroundColorPress = itemsParams.backgroundColorPress != 0
                ? itemsParams.backgroundColorPress : mParams.dialogParams.backgroundColorPress;


        final SelectorBtn listViewBg = new SelectorBtn(mBackgroundColor, mBackgroundColor
                , mTitleParams != null ? 0 : mRadius, mTitleParams != null ? 0 : mRadius
                , mRadius, mRadius);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(listViewBg);
        } else {
            setBackgroundDrawable(listViewBg);
        }

        bgItemAllRadius = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                , mRadius, mRadius, mRadius, mRadius);
        bgItemTopRadius = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                , mRadius, mRadius, 0, 0);
        bgItemBottomRadius = new SelectorBtn(mBackgroundColor, mBackgroundColorPress
                , 0, 0, mRadius, mRadius);
        bgItemNoRadius = new SelectorBtn(mBackgroundColor, mBackgroundColorPress, 0, 0, 0, 0);

        setSelector(new ColorDrawable(Color.TRANSPARENT));
        setDivider(new ColorDrawable(CircleColor.divider));
        setDividerHeight(itemsParams.dividerHeight);

        mAdapter = itemsParams.adapter;
        if (mAdapter == null) {
            mAdapter = new ItemsAdapter(context, params);
        }
        setAdapter(mAdapter);
    }

    @Override
    public void refreshItems() {
        post(new Runnable() {
            @Override
            public void run() {
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void regOnItemClickListener(OnItemClickListener onItemClickListener) {
        setOnItemClickListener(onItemClickListener);
    }

    @Override
    public void regOnItemClickListener(OnRvItemClickListener listener) {

    }

    @Override
    public View getView() {
        return this;
    }

    @Override
    public void onClick(View view, int which) {
        if (mParams.itemListener != null) {
            mParams.itemListener.onItemClick((AdapterView<?>) view, view, which, which);
        }
    }

    /****
     * 拦截触摸事件
     */
    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                int x = (int) ev.getX();
                int y = (int) ev.getY();
                int position = pointToPosition(x, y);
                if (position == AdapterView.INVALID_POSITION)
                    break;
                else {
                    //top
                    if (position == 0 && mTitleParams == null) {
                        if (position == (getAdapter().getCount() - 1)) {
                            setSelector(bgItemAllRadius);
                        } else {

                            setSelector(bgItemTopRadius);
                        }
                    }
                    //bottom
                    else if (position == (getAdapter().getCount() - 1)) {
                        // 最后一项
                        setSelector(bgItemBottomRadius);
                    }
                    //middle
                    else {
                        // 中间项
                        setSelector(bgItemNoRadius);
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    static class ItemsAdapter<T> extends BaseAdapter {
        private Context mContext;
        private List<T> mItems;
        private ItemsParams mItemsParams;

        public ItemsAdapter(Context context, SmartParams params) {
            this.mContext = context;
            this.mItemsParams = params.itemsParams;

            Object entity = mItemsParams.items;
            if (entity != null && entity instanceof Iterable) {
                this.mItems = (List<T>) entity;
            } else if (entity != null && entity.getClass().isArray()) {
                this.mItems = Arrays.asList((T[]) entity);
            } else {
                throw new IllegalArgumentException("entity must be an Array or an Iterable.");
            }
        }

        @Override
        public int getCount() {
            if (mItems != null)
                return mItems.size();
            return 0;
        }

        @Override
        public T getItem(int position) {
            if (mItems != null)
                return mItems.get(position);
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                viewHolder = new ViewHolder();
                ScaleTextView textView = new ScaleTextView(mContext);
                textView.setTextSize(mItemsParams.textSize);
                textView.setTextColor(mItemsParams.textColor);
                textView.setHeight(mItemsParams.itemHeight);
                viewHolder.item = textView;
                convertView = textView;
                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.item.setText(String.valueOf(getItem(position).toString()));
            return convertView;
        }

        class ViewHolder {
            TextView item;
        }
    }
}
