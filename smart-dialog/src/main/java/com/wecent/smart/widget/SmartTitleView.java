package com.wecent.smart.widget;

import android.content.Context;
import android.os.Build;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.wecent.smart.SmartParams;
import com.wecent.smart.params.DialogParams;
import com.wecent.smart.params.SubtitleParams;
import com.wecent.smart.params.TitleParams;
import com.wecent.smart.resource.drawable.SmartDrawable;
import com.wecent.smart.widget.listener.OnCreateTitleListener;

/**
 * desc: 仿IOS AlertDialog标题（包括标题和副标题） .
 * author: wecent .
 * date: 2017/3/27 .
 */
final class SmartTitleView extends ScaleLinearLayout {

    public SmartTitleView(Context context, SmartParams params) {
        super(context);
        init(params);
    }

    private void init(SmartParams params) {
        setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
        setOrientation(LinearLayout.VERTICAL);

        DialogParams dialogParams = params.dialogParams;
        TitleParams titleParams = params.titleParams;
        SubtitleParams subtitleParams = params.subtitleParams;

        ScaleRelativeLayout titleLayout = new ScaleRelativeLayout(getContext());
        titleLayout.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT
                , LayoutParams.WRAP_CONTENT));

        setTitleBg(titleLayout, params, titleParams.backgroundColor
                , dialogParams.backgroundColor, dialogParams.radius);
        //标题图标
        ImageView ivTitleIcon = new ImageView(getContext());
        RelativeLayout.LayoutParams layoutParamsTitleIcon = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsTitleIcon.addRule(RelativeLayout.LEFT_OF, android.R.id.title);
        layoutParamsTitleIcon.addRule(RelativeLayout.CENTER_VERTICAL);
        ivTitleIcon.setLayoutParams(layoutParamsTitleIcon);
        if (titleParams.icon != 0) {
            ivTitleIcon.setImageResource(titleParams.icon);
            ivTitleIcon.setVisibility(VISIBLE);
        } else {
            ivTitleIcon.setVisibility(GONE);
        }
        titleLayout.addView(ivTitleIcon);
        //标题
        final ScaleTextView tvTitle = new ScaleTextView(getContext());
        tvTitle.setId(android.R.id.title);
        RelativeLayout.LayoutParams layoutParamsTitle = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        layoutParamsTitle.addRule(RelativeLayout.CENTER_HORIZONTAL);
        tvTitle.setLayoutParams(layoutParamsTitle);
        tvTitle.setGravity(titleParams.gravity);
        tvTitle.setHeight(titleParams.height);
        tvTitle.setTextColor(titleParams.textColor);
        tvTitle.setTextSize(titleParams.textSize);
        tvTitle.setText(titleParams.text);
        tvTitle.setTypeface(tvTitle.getTypeface(), titleParams.styleText);
        titleLayout.addView(tvTitle);
        addView(titleLayout);

        //副标题
        ScaleTextView tvSubtitle = null;
        if (subtitleParams != null) {
            tvSubtitle = new ScaleTextView(getContext());
            setSubTitleBg(tvSubtitle, subtitleParams.backgroundColor, dialogParams.backgroundColor);
            tvSubtitle.setGravity(subtitleParams.gravity);
            if (subtitleParams.height != 0)
                tvSubtitle.setHeight(subtitleParams.height);
            tvSubtitle.setTextColor(subtitleParams.textColor);
            tvSubtitle.setTextSize(subtitleParams.textSize);
            tvSubtitle.setText(subtitleParams.text);
            int[] padding = subtitleParams.padding;
            if (padding != null)
                tvSubtitle.setAutoPadding(padding[0], padding[1], padding[2], padding[3]);
            tvSubtitle.setTypeface(tvSubtitle.getTypeface(), subtitleParams.styleText);
            addView(tvSubtitle);
        }
        OnCreateTitleListener createTitleListener = params.createTitleListener;
        if (createTitleListener != null) {
            createTitleListener.onCreateTitle(ivTitleIcon, tvTitle, tvSubtitle);
        }
    }

    private void setTitleBg(ScaleRelativeLayout tv, SmartParams params, int tbg, int dbg, int radius) {
        //如果标题没有背景色，则使用默认色
        int bg = tbg != 0 ? tbg : dbg;

        //有内容则顶部圆角
        if (params.messageParams != null || params.itemsParams != null || params.progressParams != null
                || params.inputParams != null || params.bodyViewId != 0 || params.lottieParams != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                tv.setBackground(new SmartDrawable(bg, radius, radius, 0, 0));
            } else {
                tv.setBackgroundDrawable(new SmartDrawable(bg, radius, radius, 0, 0));
            }
        }
        //无内容则全部圆角
        else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                tv.setBackground(new SmartDrawable(bg, radius));
            } else {
                tv.setBackgroundDrawable(new SmartDrawable(bg, radius));
            }
        }
    }

    private void setSubTitleBg(ScaleTextView tv, int tbg, int dbg) {
        //如果标题没有背景色，则使用默认色
        int bg = tbg != 0 ? tbg : dbg;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            tv.setBackground(new SmartDrawable(bg, 0));
        } else {
            tv.setBackgroundDrawable(new SmartDrawable(bg, 0));
        }
    }
}
