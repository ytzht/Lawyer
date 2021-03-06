package com.onekeyask.lawfirm.ui.act.me;

import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.BankCardList;
import com.onekeyask.lawfirm.entity.MyMoney;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseEvent;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.utils.UserService;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class WithdrawalActivity extends BaseToolBarActivity {

    @BindView(R.id.can_with)
    TextView canWith;
    @BindView(R.id.et_with)
    EditText etWith;
    @BindView(R.id.with_next)
    TextView withNext;

    double balance;

    @BindView(R.id.rlv_choose)
    RecyclerView rlvChoose;
    BankCardList list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdrawal);
        ButterKnife.bind(this);
        setToolbarText("余额提现");

        rlvChoose.setLayoutManager(new LinearLayoutManager(this));
        rlvChoose.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        OkGo.<String>get(Apis.MyWallet).params("lawyerId", UserService.service(getBaseContext()).getLawyerId()).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                MyMoney myMoney = (new Gson()).fromJson(response.body(), MyMoney.class);
                if (myMoney.getCode() == 0) {
                    balance = Double.parseDouble(myMoney.getData().getMyMoney());
                    canWith.setText(myMoney.getData().getMyMoney() + "元");
                } else {
                    showShort(myMoney.getMsg());
                    finish();
                }
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        OkGo.<String>get(Apis.BankCardList)
                .params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
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

    @OnClick(R.id.with_next)
    public void onViewClicked() {
        if (!etWith.getText().toString().equals("") && !etWith.getText().toString().equals(".")) {
            double ye = Double.valueOf(etWith.getText().toString());
            if (balance >= ye) {
                if (ye >= 20) {
                    startActivity(PwdAuthActivity.class,
                            "money", ye+"",
                            "userId", list.getData().getCardList().get(selectIndex).getUserId()+"",
                            "cardId", list.getData().getCardList().get(selectIndex).getId()+"");

                } else {
                    showShort("提现的金额不能少于20.00元");
                }
            } else {
                showShort("您输入的金额需小于可提现金额");
            }
        } else {
            showShort("请输入提现金额");
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
    public void onEventMainThread(BaseEvent event) {
        super.onEventMainThread(event);

        if (event.getCode() == BaseEvent.TXState){
            finish();
        }
    }

}
