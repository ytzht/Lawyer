package com.onekeyask.lawyer.ui.act.lawyer.filter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.TextView;

import com.baiiu.filter.util.UIUtil;
import com.onekeyask.lawyer.R;

/**
 * Created by zht on 2017/08/09 17:07
 */

public class TitleViewHolder extends RecyclerView.ViewHolder {

    public TitleViewHolder(Context mContext, ViewGroup parent) {
        super(UIUtil.infalte(mContext, R.layout.holder_title, parent));
    }


    public void bind(String s) {
        ((TextView) itemView).setText(s);
    }
}