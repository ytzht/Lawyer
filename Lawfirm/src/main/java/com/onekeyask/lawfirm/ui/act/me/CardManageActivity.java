package com.onekeyask.lawfirm.ui.act.me;

import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.BankCardList;
import com.onekeyask.lawfirm.entity.ResultData;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.global.L;
import com.onekeyask.lawfirm.ui.act.Apis;
import com.onekeyask.lawfirm.utils.UserService;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;

import java.util.List;

public class CardManageActivity extends BaseToolBarActivity {

    private SwipeMenuRecyclerView rlv_card_manage;
    private TextView add_card;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_manage);
        setToolbarText("管理银行卡");

        initView();

    }

    private void initView() {
        rlv_card_manage = (SwipeMenuRecyclerView) findViewById(R.id.rlv_card_manage);
        add_card = (TextView) findViewById(R.id.add_card);

        add_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(AddCardActivity.class);
            }
        });

        rlv_card_manage.setLayoutManager(new LinearLayoutManager(this));
        rlv_card_manage.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));


// 创建菜单：
        SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void onCreateMenu(SwipeMenu leftMenu, SwipeMenu rightMenu, int viewType) {
                int width = getResources().getDimensionPixelSize(R.dimen.burro_size_70dp);

                // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
                // 2. 指定具体的高，比如80;
                // 3. WRAP_CONTENT，自身高度，不推荐;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;

                SwipeMenuItem closeItem = new SwipeMenuItem(getBaseContext())
                        .setBackground(R.drawable.selector_ext)
                        .setText("删除")
                        .setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white))
                        .setWidth(width)
                        .setHeight(height);
                rightMenu.addMenuItem(closeItem); // 添加菜单到右侧。

            }
        };
// 设置监听器。
        rlv_card_manage.setSwipeMenuCreator(mSwipeMenuCreator);
        rlv_card_manage.setSwipeMenuItemClickListener(mMenuItemClickListener);


        initData();
    }


    /**
     * RecyclerView的Item中的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {

//                showShort(list.getData().getCardList().get(adapterPosition).getId() + "");

                OkGo.<String>get(Apis.DelBankCard)
                        .params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                        .params("cardId", list.getData().getCardList().get(adapterPosition).getId())
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(Response<String> response) {
                                ResultData baseResult = (new Gson()).fromJson(response.body(), ResultData.class);
                                if (baseResult.getCode() == 0){
                                    initData();
                                }else {
                                    baseResult.getMsg();
                                }
                            }
                        });

//                Toast.makeText(getBaseContext(), "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
//                Toast.makeText(getBaseContext(), "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };

    BankCardList list;

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
                            rlv_card_manage.setAdapter(new CardManageAdapter(list.getData().getCardList()));
                        } else {
                            showShort(list.getMsg());
                        }


                    }
                });
    }


    private class CardManageAdapter extends RecyclerView.Adapter<CardManageAdapter.ViewHolder> {
        List<BankCardList.DataBean.CardListBean> data;

        public CardManageAdapter(List<BankCardList.DataBean.CardListBean> data) {
            this.data = data;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_card_manage, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            holder.card_detail.setText(data.get(position).getCardtype() + "(" + data.get(position).getCardno() + ")");
            holder.card_name.setText(data.get(position).getBankname());

            holder.ll_card.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    L.d(position + "");
                    rlv_card_manage.smoothOpenRightMenu(position);

                }
            });
        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            ImageView card_img;
            LinearLayout ll_card;
            TextView card_name, card_detail;

            public ViewHolder(View itemView) {
                super(itemView);
                ll_card = (LinearLayout) itemView.findViewById(R.id.ll_card);
                card_img = (ImageView) itemView.findViewById(R.id.card_img);
                card_name = (TextView) itemView.findViewById(R.id.card_name);
                card_detail = (TextView) itemView.findViewById(R.id.card_detail);

            }
        }
    }
}
