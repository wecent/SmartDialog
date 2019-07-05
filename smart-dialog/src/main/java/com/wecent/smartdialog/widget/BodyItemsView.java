package com.wecent.smartdialog.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.wecent.smartdialog.SmartController;
import com.wecent.smartdialog.SmartParams;
import com.wecent.smartdialog.params.ItemsParams;
import com.wecent.smartdialog.params.TitleParams;
import com.wecent.smartdialog.resource.drawable.ButtonDrawable;
import com.wecent.smartdialog.resource.values.SmartColor;
import com.wecent.smartdialog.resource.values.SmartDimen;
import com.wecent.smartdialog.widget.listener.ItemsView;
import com.wecent.smartdialog.widget.listener.OnItemsClickListener;

import java.util.Arrays;
import java.util.List;

/**
 * desc: Sheet弹框条目列表视图
 * author: wecent
 * date: 2018/3/29
 */
public final class BodyItemsView extends RecyclerView implements SmartController.OnClickListener, ItemsView {

    private Adapter mAdapter;
    private SmartParams mParams;

    public BodyItemsView(Context context, SmartParams params) {
        super(context);
        init(context, params);
    }

    private void init(Context context, final SmartParams params) {
        this.mParams = params;
        ItemsParams itemsParams = params.itemsParams;
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams
                .MATCH_PARENT, LayoutParams
                .MATCH_PARENT, 1);

        //设置列表与按钮之间的下距离
        if (itemsParams.bottomMargin == -1) {
            itemsParams.bottomMargin = SmartDimen.BUTTON_ITEMS_MARGIN;
        }
        layoutParams.bottomMargin = itemsParams.bottomMargin;
        setLayoutParams(layoutParams);
        setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        addItemDecoration(new LinearItemDecoration(new ColorDrawable(SmartColor.divider), itemsParams.dividerHeight));

        mAdapter = new ItemsAdapter(context, mParams);

        TitleParams titleParams = params.titleParams;

        int radius = mParams.dialogParams.radius;
        //如果没有背景色，则使用默认色
        int backgroundColor = itemsParams.backgroundColor != 0
                ? itemsParams.backgroundColor : mParams.dialogParams.backgroundColor;

        final ButtonDrawable RvBg = new ButtonDrawable(backgroundColor, backgroundColor
                , titleParams != null ? 0 : radius, titleParams != null ? 0 : radius
                , radius, radius);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            setBackground(RvBg);
        } else {
            setBackgroundDrawable(RvBg);
        }
        setAdapter(mAdapter);
    }

    @Override
    public void regOnItemClickListener(OnItemsClickListener listener) {
        if (mAdapter != null && mAdapter instanceof ItemsAdapter) {
            ((ItemsAdapter) mAdapter).setOnItemClickListener(listener);
        }
    }

    @Override
    public View getView() {
        return this;
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
    public void onClick(View view, int which) {
        if (mParams.itemListener != null) {
            mParams.itemListener.onItemsClick(view, which);
        }
    }

    class LinearItemDecoration extends RecyclerView.ItemDecoration {

        private Drawable mDivider;
        private int mDividerHeight;

        public LinearItemDecoration(Drawable divider, int dividerHeight) {
            mDivider = divider;
            mDividerHeight = dividerHeight;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            outRect.set(0, 0, 0, mDividerHeight);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount() - 1;
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + mDividerHeight;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }
    }

    public class GridItemDecoration extends RecyclerView.ItemDecoration {

        private Drawable mDivider;
        private int mDividerHeight;

        public GridItemDecoration(Drawable divider, int dividerHeight) {
            mDivider = divider;
            mDividerHeight = dividerHeight;
        }

        @Override
        public void onDraw(Canvas c, RecyclerView parent, State state) {
            drawHorizontal(c, parent);
            drawVertical(c, parent);
        }

        private void drawHorizontal(Canvas c, RecyclerView parent) {
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);
                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int left = child.getLeft() - params.leftMargin;
                final int right = child.getRight() + params.rightMargin + mDividerHeight;
                final int top = child.getBottom() + params.bottomMargin;
                final int bottom = top + mDividerHeight;
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        private void drawVertical(Canvas c, RecyclerView parent) {
            final int childCount = parent.getChildCount() - 1;
            for (int i = 0; i < childCount; i++) {
                final View child = parent.getChildAt(i);

                final RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();
                final int top = child.getTop() - params.topMargin;
                final int bottom = child.getBottom() + params.bottomMargin;
                final int left = child.getRight() + params.rightMargin;
                final int right = left + mDividerHeight;

                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(c);
            }
        }

        private boolean isLastColum(RecyclerView parent, int pos, int spanCount, int childCount) {
            LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                // 如果是最后一列，则不需要绘制右边
                if ((pos + 1) % spanCount == 0) {
                    return true;
                }
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
                if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                    // 如果是最后一列，则不需要绘制右边
                    if ((pos + 1) % spanCount == 0) {
                        return true;
                    }
                } else {
                    childCount = childCount - childCount % spanCount;
                    // 如果是最后一列，则不需要绘制右边
                    if (pos >= childCount) {
                        return true;
                    }
                }
            }
            return false;
        }

        private boolean isLastRaw(RecyclerView parent, int pos, int spanCount, int childCount) {
            LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {
                childCount = childCount - childCount % spanCount;
                // 如果是最后一行，则不需要绘制底部
                if (pos >= childCount) {
                    return true;
                }
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                int orientation = ((StaggeredGridLayoutManager) layoutManager).getOrientation();
                // StaggeredGridLayoutManager 且纵向滚动
                if (orientation == StaggeredGridLayoutManager.VERTICAL) {
                    childCount = childCount - childCount % spanCount;
                    // 如果是最后一行，则不需要绘制底部
                    if (pos >= childCount) {
                        return true;
                    }
                }
                // StaggeredGridLayoutManager 且横向滚动
                else {
                    // 如果是最后一行，则不需要绘制底部
                    if ((pos + 1) % spanCount == 0) {
                        return true;
                    }
                }
            }
            return false;
        }

        @Override
        public void getItemOffsets(Rect outRect, int itemPosition, RecyclerView parent) {
            int spanCount = getSpanCount(parent);
            int childCount = parent.getAdapter().getItemCount();
            // 如果是最后一行，则不需要绘制底部
            if (isLastRaw(parent, itemPosition, spanCount, childCount)) {
                outRect.set(0, 0, mDividerHeight, 0);
            }
            // 如果是最后一列，则不需要绘制右边
            else if (isLastColum(parent, itemPosition, spanCount, childCount)) {
                outRect.set(0, 0, 0, mDividerHeight);
            } else {
                outRect.set(0, 0, mDividerHeight, mDividerHeight);
            }
        }

        private int getSpanCount(RecyclerView parent) {
            // 列数
            int spanCount = -1;
            LayoutManager layoutManager = parent.getLayoutManager();
            if (layoutManager instanceof GridLayoutManager) {

                spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
            } else if (layoutManager instanceof StaggeredGridLayoutManager) {
                spanCount = ((StaggeredGridLayoutManager) layoutManager).getSpanCount();
            }
            return spanCount;
        }
    }

    static class ItemsAdapter<T> extends Adapter<ItemsAdapter.Holder> {

        static class Holder extends RecyclerView.ViewHolder implements OnClickListener {
            OnItemsClickListener mItemClicksListener;
            TextView item;

            public Holder(TextView itemView, OnItemsClickListener listener) {
                super(itemView);
                item = itemView;
                mItemClicksListener = listener;
                itemView.setOnClickListener(this);
            }

            @Override
            public void onClick(View v) {
                if (mItemClicksListener != null) {
                    mItemClicksListener.onItemsClick(v, getAdapterPosition());
                }
            }
        }

        private OnItemsClickListener mItemClickListener;
        private Context mContext;
        private List<T> mItems;
        private int mRadius;
        private int mBackgroundColor;
        private int mBackgroundColorPress;
        private ItemsParams mItemsParams;
        private TitleParams mTitleParams;
        private ButtonDrawable bgItemAllRadius;
        private ButtonDrawable bgItemTopRadius;
        private ButtonDrawable bgItemBottomRadius;

        public ItemsAdapter(Context context, SmartParams params) {
            this.mContext = context;
            this.mTitleParams = params.titleParams;
            this.mItemsParams = params.itemsParams;
            this.mRadius = params.dialogParams.radius;

            //如果没有背景色，则使用默认色
            this.mBackgroundColor = mItemsParams.backgroundColor != 0
                    ? mItemsParams.backgroundColor : params.dialogParams.backgroundColor;
            this.mBackgroundColorPress = mItemsParams.backgroundColorPress != 0
                    ? mItemsParams.backgroundColorPress : params.dialogParams.backgroundColorPress;

            bgItemAllRadius = new ButtonDrawable(mBackgroundColor, mBackgroundColorPress
                    , mRadius, mRadius, mRadius, mRadius);
            bgItemTopRadius = new ButtonDrawable(mBackgroundColor, mBackgroundColorPress
                    , mRadius, mRadius, 0, 0);
            bgItemBottomRadius = new ButtonDrawable(mBackgroundColor, mBackgroundColorPress
                    , 0, 0, mRadius, mRadius);

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
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            SmartTextView textView = new SmartTextView(mContext);
            textView.setLayoutParams(new LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
            textView.setPadding(10, 0, 10, 0);
            textView.setTextSize(mItemsParams.textSize);
            textView.setTextColor(mItemsParams.textColor);
            textView.setHeight(mItemsParams.itemHeight);
            Holder holder = new Holder(textView, mItemClickListener);
            return holder;
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {
            llvBg(holder, position);
            holder.item.setText(String.valueOf(mItems.get(position).toString()));
        }

        //LinearLayoutManager Vertical Background
        private void llvBg(Holder holder, int position) {
            //top 且没有标题
            if (position == 0 && mTitleParams == null) {
                if (getItemCount() == 1) {
                    setItemBg(holder, bgItemAllRadius);
                } else {
                    setItemBg(holder, bgItemTopRadius);
                }
            }
            //bottom 有标题与中间一样
            else if (position == getItemCount() - 1) {
                setItemBg(holder, bgItemBottomRadius);
            }
            //middle
            else {
                setItemBg(holder, new ButtonDrawable(mBackgroundColor, mBackgroundColorPress, 0, 0, 0, 0));
            }
        }

        private void setItemBg(Holder holder, ButtonDrawable buttonDrawable) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                holder.item.setBackground(buttonDrawable);
            } else {
                holder.item.setBackgroundDrawable(buttonDrawable);
            }
        }

        @Override
        public int getItemCount() {
            return mItems == null ? 0 : mItems.size();
        }

        public void setOnItemClickListener(OnItemsClickListener listener) {
            this.mItemClickListener = listener;
        }
    }
}
