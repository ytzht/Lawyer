package com.onekeyask.lawfirm.ui.act.user;

import android.app.ProgressDialog;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.IncomeAll;
import com.onekeyask.lawfirm.entity.IncomeDetail;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseToolBarActivity;
import com.onekeyask.lawfirm.utils.UserService;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class IncomeDetailActivity extends BaseToolBarActivity {

    private android.support.v7.widget.RecyclerView rlvincome;
    private IncomeAdapter adapter;
    private boolean hasMore = true;
    private int index = 1;
    private int size = 10;
    private List<IncomeDetail.DataBean.IncomeListBean> list = new ArrayList<>();
    private View contentView;
    private PopupWindow popupWindow = null;
    private int month;
    private int year;
    private ProgressDialog dialog;

    private double totalMoney;
    private String allMoney;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_income_detail);
        this.rlvincome = (RecyclerView) findViewById(R.id.rlv_income);
        setToolbarText("收入详情");
        index = 1;
        year = Calendar.getInstance().get(Calendar.YEAR);
        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
        rlvincome.setLayoutManager(new LinearLayoutManager(this));
        rlvincome.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new IncomeAdapter();
        rlvincome.setAdapter(adapter);
        rlvincome.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (((LinearLayoutManager) (recyclerView.getLayoutManager())).findLastVisibleItemPosition()
                            == recyclerView.getLayoutManager().getItemCount() - 1) {
                        if (hasMore) {
                            index += 1;
                            initData();
                        }
                    }
                }
            }
        });

        dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("正在加载...");

        OkGo.<String>get(Apis.IncomeAll).params("lawyerId", UserService.service(getBaseContext()).getLawyerId()).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                IncomeAll all = (new Gson()).fromJson(response.body(), IncomeAll.class);
                if (all.getCode() == 0) {
                    allMoney = all.getData().getTotalMoney();
                } else {
                    showShort(all.getMsg());
                }
            }
        });

        initData();
    }

    private void initData() {

        OkGo.<String>get(Apis.IncomeDetail)
                .params("lawyerId", UserService.service(getBaseContext()).getLawyerId())
                .params("year", year)
                .params("month", month)
                .params("page", index)
                .params("size", size)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        IncomeDetail detail = (new Gson()).fromJson(response.body(), IncomeDetail.class);
                        if (detail.getCode() == 0) {

                            hasMore = detail.getData().isHasMore();

                            totalMoney = detail.getData().getTotalMoney();
                            if (index == 1) {
                                list.clear();
                                list.addAll(detail.getData().getIncomeList());
                                rlvincome.setAdapter(adapter);
                            } else {
                                list.addAll(detail.getData().getIncomeList());
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            showShort(detail.getMsg());
                            finish();
                        }
                        if (dialog.isShowing()) dialog.dismiss();
                    }
                });


    }


    private class IncomeAdapter extends RecyclerView.Adapter {

        @Override
        public int getItemViewType(int position) {

            if (position == 0) {
                return R.layout.cell_income;
            } else if (getItemCount() == position + 1) {
                return R.layout.cell_income_text;
            } else {
                return R.layout.cell_income_item;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getBaseContext()).inflate(viewType, parent, false);
            if (viewType == R.layout.cell_income) {
                return new ViewHolderHeader(view);
            } else if (viewType == R.layout.cell_income_item) {
                return new ViewHolder(view);
            } else {
                return new ViewHolderText(view);
            }
        }

        @Override
        public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {

            if (position == 0) {

                ((ViewHolderHeader) holder).total_income.setText("￥" + allMoney + "元");
                ((ViewHolderHeader) holder).month_money.setText("￥" + totalMoney + "元");
                ((ViewHolderHeader) holder).tv_year.setText(year + "年");
                ((ViewHolderHeader) holder).ll_month.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        popupWindow = getPopwindow(contentView);
                        popupWindow.showAsDropDown(((ViewHolderHeader) holder).ll_month);
                    }
                });

                ((ViewHolderHeader) holder).tv_month.setText(month + "月");

                contentView = LayoutInflater.from(getBaseContext()).inflate(R.layout.menu_month, null);
                final TextView tv_1 = (TextView) contentView.findViewById(R.id.tv_type_1);
                tv_1.setText((Calendar.getInstance().get(Calendar.MONTH) + 1) + "月");
                tv_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        month = Calendar.getInstance().get(Calendar.MONTH) + 1;
                        year = Calendar.getInstance().get(Calendar.YEAR);
                        index = 1;
                        popupWindow.dismiss();
                        dialog.show();
                        initData();
                        ((ViewHolderHeader) holder).tv_month.setText(month + "月");
                    }
                });
                final TextView tv_2 = (TextView) contentView.findViewById(R.id.tv_type_2);
                final Calendar calendar = Calendar.getInstance();
                calendar.add(Calendar.MONTH, -1);
                tv_2.setText((calendar.get(Calendar.MONTH) + 1) + "月");

                tv_2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        month = calendar.get(Calendar.MONTH) + 1;
                        year = calendar.get(Calendar.YEAR);
                        index = 1;
                        dialog.show();
                        popupWindow.dismiss();
                        initData();
                        ((ViewHolderHeader) holder).tv_month.setText(month + "月");
                    }
                });
                final TextView tv_3 = (TextView) contentView.findViewById(R.id.tv_type_3);
                final Calendar calendar1 = Calendar.getInstance();
                calendar1.add(Calendar.MONTH, -2);
                tv_3.setText((calendar1.get(Calendar.MONTH) + 1) + "月");
                tv_3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        month = calendar1.get(Calendar.MONTH) + 1;
                        year = calendar1.get(Calendar.YEAR);
                        index = 1;
                        dialog.show();
                        popupWindow.dismiss();
                        initData();
                        ((ViewHolderHeader) holder).tv_month.setText(month + "月");
                    }
                });


            } else if (position == getItemCount() - 1) {

            } else {

                ((ViewHolder) holder).type_tv.setText(list.get(position - 1).getServiceName());
                ((ViewHolder) holder).income_tv.setText("￥" + list.get(position - 1).getAmount() + "元");
                SimpleDateFormat sdf = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.US);
                ((ViewHolder) holder).tv_income_time.setText(list.get(position - 1).getTime());

            }
        }

        @Override
        public int getItemCount() {
            return list.size() + 2;
        }


        class ViewHolderHeader extends RecyclerView.ViewHolder {

            private TextView total_income, month_money, tv_month, tv_year;
            private LinearLayout ll_month;

            public ViewHolderHeader(View itemView) {
                super(itemView);
                ll_month = (LinearLayout) itemView.findViewById(R.id.ll_month);
                total_income = (TextView) itemView.findViewById(R.id.total_income);
                month_money = (TextView) itemView.findViewById(R.id.month_money);
                tv_month = (TextView) itemView.findViewById(R.id.tv_month);
                tv_year = (TextView) itemView.findViewById(R.id.tv_year);
            }
        }

        class ViewHolder extends RecyclerView.ViewHolder {
            private TextView type_tv, income_tv, tv_income_time;

            public ViewHolder(View view) {
                super(view);
                type_tv = (TextView) view.findViewById(R.id.type_tv);
                income_tv = (TextView) view.findViewById(R.id.income_tv);
                tv_income_time = (TextView) view.findViewById(R.id.tv_income_time);

            }
        }

        class ViewHolderText extends RecyclerView.ViewHolder {

            public ViewHolderText(View itemView) {
                super(itemView);
            }
        }


        //跳出选项框
        public PopupWindow getPopwindow(View view) {
            PopupWindow popupWindow = new PopupWindow(view, LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            popupWindow.setFocusable(true);
            popupWindow.setOutsideTouchable(true);
            WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//        layoutParams.alpha = 1f;
            getWindow().setAttributes(layoutParams);
            popupWindow.setBackgroundDrawable(new ColorDrawable());
//        popupWindow.showAtLocation(getActivity().findViewById(R.id.rl_ser_list), Gravity.BOTTOM, 0, 0);
            popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
//        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
            popupWindow.update();
            popupWindow.setTouchable(true);

            popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
                @Override
                public void onDismiss() {
                    WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
//                layoutParams.alpha = 1f;
                    getWindow().setAttributes(layoutParams);
                }
            });
            return popupWindow;
        }


    }
}
