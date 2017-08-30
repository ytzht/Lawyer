package com.onekeyask.lawyer.ui.act.me;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.ApplyTX;
import com.onekeyask.lawyer.entity.BankCardList;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.utils.UserService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ChooseBankActivity extends BaseToolBarActivity {

    @BindView(R.id.rlv_choose)
    RecyclerView rlvChoose;
    @BindView(R.id.add_card)
    TextView addCard;
    BankCardList list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_bank);
        setToolbarText("选择银行卡");
        ButterKnife.bind(this);

        rlvChoose.setLayoutManager(new LinearLayoutManager(this));
        rlvChoose.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

    }

    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        OkGo.<String>get(Apis.BankCardList)
                .params("userId", UserService.service(getBaseContext()).getUserId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        list = (new Gson()).fromJson(response.body(), BankCardList.class);
                        if (list.getCode() == 0) {
                            rlvChoose.setAdapter(new CardManageAdapter(list.getData().getCardList()));
                        } else {
                            showShort(list.getMsg());
                        }


                    }
                });
    }

    @OnClick(R.id.add_card)
    public void onViewClicked() {
        if (list.getData().getCardList().size()>0) {
            OkGo.<String>get(Apis.ApplyTX)
                    .params("userId", list.getData().getCardList().get(selectIndex).getUserId())
                    .params("cardId", list.getData().getCardList().get(selectIndex).getId())
                    .params("money", getIntent().getStringExtra("money"))
                    .params("codeId", getIntent().getStringExtra("codeId"))
                    .params("code", getIntent().getStringExtra("code"))
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {

                            ApplyTX tx = (new Gson()).fromJson(response.body(), ApplyTX.class);
                            if (tx.getCode() == 0) {
                                startActivity(WithStateActivity.class);
                                finish();
                            } else {
                                showShort(tx.getMsg());
                            }

                        }
                    });
        }else {
            showShort("请先添加一张银行卡");
        }
    }

    private int selectIndex = 0;
    private class CardManageAdapter extends RecyclerView.Adapter<CardManageAdapter.ViewHolder> {
        List<BankCardList.DataBean.CardListBean> cardList;
        public CardManageAdapter(List<BankCardList.DataBean.CardListBean> cardList) {
            this.cardList = cardList;

        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_card_manage, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            holder.card_detail.setText(cardList.get(position).getCardtype() + "(" + cardList.get(position).getCardno() + ")");
            holder.card_name.setText(cardList.get(position).getBankname());

            if (position == selectIndex){
                holder.iv_select.setImageResource(R.drawable.select_c);
            }else {
                holder.iv_select.setImageResource(R.drawable.select_g);
            }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    selectIndex = position;
                    notifyDataSetChanged();
                }
            });
        }

        @Override
        public int getItemCount() {
            return cardList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder{

            ImageView iv_select, card_img;
            TextView card_name, card_detail;

            public ViewHolder(View itemView) {
                super(itemView);

                iv_select = (ImageView) itemView.findViewById(R.id.iv_select);
                card_img = (ImageView) itemView.findViewById(R.id.card_img);
                card_name = (TextView) itemView.findViewById(R.id.card_name);
                card_detail = (TextView) itemView.findViewById(R.id.card_detail);
            }
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add_card, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.add_card) {
            startActivity(AddCardActivity.class);
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

}
