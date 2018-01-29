package com.onekeyask.lawyer.ui.act.me;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;

import com.d.lib.xrv.adapter.CommonAdapter;
import com.d.lib.xrv.adapter.CommonHolder;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.SortBean;
import com.onekeyask.lawyer.global.BaseEvent;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.utils.sort.SideBar;
import com.onekeyask.lawyer.utils.sort.SortUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class BankCardTypeActivity extends BaseToolBarActivity {

    private LinearLayout llytTin;
    private TextView tvTinLetter;
    private RecyclerView rvList;
    private SortUtil sortUtil;

    @BindView(R.id.rb_save)
    RadioButton rbSave;
    @BindView(R.id.rb_trust)
    RadioButton rbTrust;
    @BindView(R.id.sb_sidebar)
    SideBar sideBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bank_card_type);
        llytTin = (LinearLayout) findViewById(R.id.llyt_tin);
        tvTinLetter = (TextView) findViewById(R.id.tv_tin_letter);
        rvList = (RecyclerView) findViewById(R.id.rv_list);
        rbSave = (RadioButton) findViewById(R.id.rb_save);
        rbTrust = (RadioButton) findViewById(R.id.rb_trust);

        rbSave.toggle();


        init();
    }

    private void init() {
        setToolbarText("验证银行");
        List<SortBean> datas = getDatas();
        sortUtil = new SortUtil();
        sortUtil.sortDatas(datas);
        rvList.setLayoutManager(new LinearLayoutManager(this));
        rvList.setAdapter(new SortAdapter(this, datas, R.layout.adapter_sort));
        rvList.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                sortUtil.onScrolled(recyclerView, llytTin, tvTinLetter);
            }
        });
        sideBar.setOnLetterChangedListener(new SideBar.OnLetterChangedListener() {
            @Override
            public void onChange(int index, String c) {
                sortUtil.onChange(index, c, rvList);
            }
        });
    }

    public List<SortBean> getDatas() {
        String[] arrays = getResources().getStringArray(R.array.sort_data);
        List<SortBean> datas = new ArrayList<>();
        for (String c : arrays) {
            SortBean bean = new SortBean(c);
            bean.content = c;
            datas.add(bean);
        }
        return datas;
    }

    public class SortAdapter extends CommonAdapter<SortBean> {
        public SortAdapter(Context context, List<SortBean> datas, int layoutId) {
            super(context, datas, layoutId);
        }

        @Override
        public void convert(int position, CommonHolder holder, final SortBean item) {
            if (item.isLetter) {
                holder.setViewVisibility(R.id.llyt_sort, View.VISIBLE);
                holder.setText(R.id.tv_letter, item.letter);
            } else {
                holder.setViewVisibility(R.id.llyt_sort, View.GONE);
            }
            holder.setText(R.id.tv_content, item.content);
            holder.setViewOnClickListener(R.id.ll_item, new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (!rbSave.isChecked() && !rbTrust.isChecked()) {
                        showShort("请选择银行卡种类");
                        return;
                    }
                    String cardtype;
                    if (rbTrust.isChecked()) cardtype = "信用卡";
                    else cardtype = "储蓄卡";

                    startActivity(VerificationBankActivity.class,
                            "bank", item.content,
                            "type", cardtype,
                            "number", getIntent().getStringExtra("number")
                    );
                    finish();


                }
            });
        }
    }

    @Override
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);

        if (event.getCode() == BaseEvent.AddBankCard){
            finish();
        }
    }
}
