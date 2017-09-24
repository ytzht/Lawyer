package com.onekeyask.lawyer.ui.act.lawyer.filter;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baiiu.filter.interfaces.OnFilterDoneListener;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.L;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by zht on 2017/08/09 17:03
 */

public class BetterDoubleGridView extends LinearLayout implements View.OnClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private List<String> mTopGridData;
    private List<String> mMidGridData;
//    private List<String> mBottomGridList;
    private OnFilterDoneListener mOnFilterDoneListener;


    public BetterDoubleGridView(Context context) {
        this(context, null);
    }

    public BetterDoubleGridView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public BetterDoubleGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BetterDoubleGridView(Context context, AttributeSet attrs, int defStyleAttr,
                                int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        setBackgroundColor(Color.WHITE);
        inflate(context, R.layout.merge_filter_double_grid, this);
        ButterKnife.bind(this, this);
    }


    public BetterDoubleGridView setmTopGridData(List<String> mTopGridData) {
        this.mTopGridData = mTopGridData;
        return this;
    }

    public BetterDoubleGridView setmMidGridData(List<String> mMidGridData) {
        this.mMidGridData = mMidGridData;
        return this;
    }

//    public BetterDoubleGridView setmBottomGridList(List<String> mBottomGridList) {
//        this.mBottomGridList = mBottomGridList;
//        return this;
//    }

    public BetterDoubleGridView build() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this.getContext(), 4);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                if (position == 0 || position == mTopGridData.size() + 1 || position == mTopGridData.size() + 1 + mMidGridData.size() + 1) {
                    return 4;
                }
                return 1;
            }
        });
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(new DoubleGridAdapter(getContext(), mTopGridData, mMidGridData, this));

        return this;
    }

    private TextView mTopSelectedTextView1;
    private TextView mTopSelectedTextView2;
    private TextView mTopSelectedTextView3;
    private TextView mMidSelectedTextView;
//    private TextView mBottomSelectedTextView;

    private List<String> mTopString = new ArrayList<>();

    @Override
    public void onClick(View v) {

        TextView textView = (TextView) v;
        String text = (String) textView.getTag();

        if (textView == mTopSelectedTextView1) {
            mTopSelectedTextView1 = null;
            textView.setSelected(false);
            mTopString.remove("1");
        } else if (textView == mTopSelectedTextView2) {
            mTopSelectedTextView2 = null;
            textView.setSelected(false);
            mTopString.remove("2");
        } else if (textView == mTopSelectedTextView3) {
            mTopSelectedTextView3 = null;
            textView.setSelected(false);
            mTopString.remove("3");
        } else if (textView == mMidSelectedTextView) {
            mMidSelectedTextView = null;
            textView.setSelected(false);
//        }else if (textView == mBottomSelectedTextView) {
//            mBottomSelectedTextView = null;
//            textView.setSelected(false);
        } else if (mTopGridData.contains(text)) {
            if (text.contains("私人律师")){
                mTopSelectedTextView1 = textView;
                mTopString.add("1");
            }else if (text.contains("图文咨询")){
                mTopSelectedTextView2 = textView;
                mTopString.add("2");
            }else if (text.contains("电话咨询")){
                mTopSelectedTextView3 = textView;
                mTopString.add("3");
            }
            textView.setSelected(true);

        } else if (mMidGridData.contains(text)){
            if (mMidSelectedTextView != null) {
                mMidSelectedTextView.setSelected(false);
            }
            mMidSelectedTextView = textView;
            textView.setSelected(true);
        } else {
//            if (mBottomSelectedTextView != null) {
//                mBottomSelectedTextView.setSelected(false);
//            }
//            mBottomSelectedTextView = textView;
//            textView.setSelected(true);
        }
    }


    public BetterDoubleGridView setOnFilterDoneListener(OnFilterDoneListener listener) {
        mOnFilterDoneListener = listener;
        return this;
    }

    @OnClick(R.id.bt_confirm)
    public void clickDone() {

        String mTop = "";
        if (mTopString.size() != 0){
            for (int i = 0; i < mTopString.size(); i++) {
                L.d("=====ss=====", mTopString.get(i));
                if (i == 0){
                    mTop += mTopString.get(i);
                }else {
                    mTop += ((",")+mTopString.get(i));
                }
            }
        }
        FilterUrl.instance().doubleGridTop = mTop;
//        FilterUrl.instance().doubleGridTop = mTopSelectedTextView == null ? "" : (String) mTopSelectedTextView.getTag();
        FilterUrl.instance().doubleGridMid = mMidSelectedTextView == null ? "" : (String) mMidSelectedTextView.getTag();
//        FilterUrl.instance().doubleGridBottom = mBottomSelectedTextView == null ? "" : (String) mBottomSelectedTextView.getTag();

        if (mOnFilterDoneListener != null) {
            mOnFilterDoneListener.onFilterDone(3, "", "");
        }
    }

    @OnClick(R.id.bt_cancel)
    public void clickCancel() {
        if (mTopSelectedTextView1 != null) {
            mTopSelectedTextView1.setSelected(false);
            mTopSelectedTextView1 = null;
        }
        if (mTopSelectedTextView2 != null) {
            mTopSelectedTextView2.setSelected(false);
            mTopSelectedTextView2 = null;
        }
        if (mTopSelectedTextView3 != null) {
            mTopSelectedTextView3.setSelected(false);
            mTopSelectedTextView3 = null;
        }
        if (mMidSelectedTextView != null) {
            mMidSelectedTextView.setSelected(false);
            mMidSelectedTextView = null;
        }
//        if (mBottomSelectedTextView != null) {
//            mBottomSelectedTextView.setSelected(false);
//            mBottomSelectedTextView = null;
//        }

        FilterUrl.instance().doubleGridTop = "";
        FilterUrl.instance().doubleGridMid = "";
//        FilterUrl.instance().doubleGridBottom = "";

        if (mOnFilterDoneListener != null) {
            mOnFilterDoneListener.onFilterDone(3, "", "");
        }
    }


}