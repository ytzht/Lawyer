package com.onekeyask.lawyer.ui.act.lawyer;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.CustomerReply;
import com.onekeyask.lawyer.entity.CustomerReplyBean;
import com.onekeyask.lawyer.entity.GiveMoneyList;
import com.onekeyask.lawyer.entity.IsFavorite;
import com.onekeyask.lawyer.entity.LawyerDetail;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.ui.act.consulting.PayLawyerActivity;
import com.onekeyask.lawyer.utils.MyDecoration;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class LawyerDetailActivity extends BaseToolBarActivity {

    @BindView(R.id.lawyer_header)
    CircleImageView lawyerHeader;
    @BindView(R.id.lawyer_name)
    TextView lawyerName;
    @BindView(R.id.lawyer_office)
    TextView lawyerOffice;
    @BindView(R.id.tv_tag1)
    TextView tvTag1;
    @BindView(R.id.tv_tag2)
    TextView tvTag2;
    @BindView(R.id.tv_tag3)
    TextView tvTag3;
    @BindView(R.id.rl_lawyer_header)
    RelativeLayout rlLawyerHeader;
    @BindView(R.id.special_rl)
    RecyclerView special_rl;
    @BindView(R.id.tags_fl)
    RecyclerView tagsFl;
    @BindView(R.id.service_list)
    RecyclerView serviceList;
    @BindView(R.id.give_money_list)
    RecyclerView giveMoneyList;
    @BindView(R.id.h_service_type)
    RecyclerView h_service_type;
    @BindView(R.id.serviceCount)
    TextView serviceCount;
    @BindView(R.id.serviceScore)
    TextView serviceScore;
    @BindView(R.id.ll_send_money)
    LinearLayout llSendMoney;
    @BindView(R.id.favorite_iv)
    ImageView favoriteIv;
    @BindView(R.id.favorite_tv)
    TextView favoriteTv;
    @BindView(R.id.ll_favorite)
    LinearLayout llFavorite;
    @BindView(R.id.consulting)
    TextView consulting;
//    @BindView(R.id.refreshLayout_detail)
//    SmartRefreshLayout refreshLayout;
    @BindView(R.id.header_more)
    ImageView headerMore;
    @BindView(R.id.service_notes)
    TextView service_notes;
    private int lawyerId;
    private LawyerDetail.DataBean data;
    private int index = 1;
    private int size = 100;
    private boolean hasMore = true;
    private CommentListAdapter commentListAdapter;
    private GiveMoneyListAdapter giveMoneyListAdapter;
    private SpecialAdapter specialAdapter;
    private CommentTagAdapter commentTagAdapter;
    private ServiceTypeAdapter serviceTypeAdapter;
    private List<CustomerReplyBean> comData = new ArrayList<>();
    private List<GiveMoneyList.DataBean.GiveMoneyBean> moneyData = new ArrayList<>();
    private IsFavorite isFavorite = new IsFavorite();
    private int type = 1;//图文咨询、电话咨询、私人律师
    private int price;
    private String cycle;
    private PopupWindow popupWindow = null;
    private View popupView;
    private TextView tv_sel_2;
    private TextView tv_sel_4;
    private TextView tv_sel_6;
    private TextView tv_sel_8;
    private TextView tv_cancel_popup;
    private TextView tv_yes_popup;
    private EditText et_money_popup, et_desc_popup;
    private String selectMoney = "2.00";
    private int score = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lawyer_detail);
        ButterKnife.bind(this);

        lawyerId = getIntent().getIntExtra("lawyerId", 0);

        index = 1;
        initCListData();
        initData();
        initPop();
    }

    private void initPop() {
        popupView = LayoutInflater.from(this).inflate(R.layout.popup_select_money, null);
        tv_sel_2 = (TextView) popupView.findViewById(R.id.tv_sel_2);
        tv_sel_4 = (TextView) popupView.findViewById(R.id.tv_sel_4);
        tv_sel_6 = (TextView) popupView.findViewById(R.id.tv_sel_6);
        tv_sel_8 = (TextView) popupView.findViewById(R.id.tv_sel_8);
        tv_cancel_popup = (TextView) popupView.findViewById(R.id.tv_cancel_popup);
        tv_yes_popup = (TextView) popupView.findViewById(R.id.tv_yes_popup);
        initPopupClick();

        et_money_popup = (EditText) popupView.findViewById(R.id.et_money_popup);
        et_desc_popup = (EditText) popupView.findViewById(R.id.et_desc_popup);
        et_money_popup.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        et_money_popup.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!et_money_popup.getText().toString().equals("") && !et_money_popup.getText().toString().equals(".")) {
                    int money = (int) (((Double.parseDouble(et_money_popup.getText().toString()))));
                    if (money != 0) {
                        selectMoney = String.valueOf(money);
                    } else {
                        selectMoney = "";
                    }
                }
                tv_sel_2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_4.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_6.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_8.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        llSendMoney.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow = getPopwindow(popupView);
            }
        });


    }

    //跳出选项框
    public PopupWindow getPopwindow(View view) {
        PopupWindow popupWindow = new PopupWindow(view,
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
        layoutParams.alpha = 0.6f;
        getWindow().setAttributes(layoutParams);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setSoftInputMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        popupWindow.showAtLocation(llSendMoney, Gravity.BOTTOM, 0, 0);
//        popupWindow.showAsDropDown(rlGiveMoney);
//        popupWindow.setAnimationStyle(R.style.anim_menu_bottombar);
        popupWindow.setAnimationStyle(android.R.style.Animation_InputMethod);
        popupWindow.update();
        popupWindow.setTouchable(true);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams layoutParams = getWindow().getAttributes();
                layoutParams.alpha = 1f;
                getWindow().setAttributes(layoutParams);
            }
        });
        return popupWindow;
    }

    private void initCListData() {

        serviceList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        serviceList.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        commentListAdapter = new CommentListAdapter();
        serviceList.setAdapter(commentListAdapter);

        OkGo.<String>get(Apis.CommentList)
                .params("score", score) //满意度评分，0 全部5 很满意3 满意1 不满意
                .params("lawyerId", lawyerId)
                .params("page", index)
                .params("size", size)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        CustomerReply reply = (new Gson()).fromJson(response.body(), CustomerReply.class);
                        hasMore = reply.getData().isHasMore();
                        if (reply.getCode() == 0) {

                            if (index == 1) {
                                comData.clear();
                                comData.addAll(reply.getData().getCustomerReply());
                                serviceList.setAdapter(commentListAdapter);
                                commentListAdapter.notifyDataSetChanged();
                            } else {
                                comData.addAll(reply.getData().getCustomerReply());
                                commentListAdapter.notifyDataSetChanged();
                            }
                        } else {
                            showShort(reply.getMsg());
                        }
                    }
                });


        giveMoneyList.setLayoutManager(new LinearLayoutManager(this) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        giveMoneyList.addItemDecoration(new MyDecoration(this, MyDecoration.VERTICAL_LIST));
        giveMoneyListAdapter = new GiveMoneyListAdapter();
        giveMoneyList.setAdapter(giveMoneyListAdapter);


        OkGo.<String>get(Apis.GiveMoneyList)
                .params("type", 0) //满意度评分，0 全部5 很满意3 满意1 不满意
                .params("lawyerId", lawyerId)
                .params("page", index)
                .params("size", size)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        GiveMoneyList reply = (new Gson()).fromJson(response.body(), GiveMoneyList.class);
                        hasMore = reply.getData().isHasMore();
                        if (reply.getCode() == 0) {

                            if (index == 1) {
                                moneyData.clear();
                                moneyData.addAll(reply.getData().getGiveMoney());
                                giveMoneyList.setAdapter(giveMoneyListAdapter);
                                giveMoneyListAdapter.notifyDataSetChanged();
                            } else {
                                moneyData.addAll(reply.getData().getGiveMoney());
                                giveMoneyListAdapter.notifyDataSetChanged();
                            }
                        } else {
                            showShort(reply.getMsg());
                        }
                    }
                });
    }

    private void initData() {


        OkGo.<String>get(Apis.LawyerDetail)
                .params("lawyerId", lawyerId)
                .params("userId", "2")
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        LawyerDetail detail = (new Gson()).fromJson(response.body(), LawyerDetail.class);
                        if (detail.getCode() == 0) {
                            data = detail.getData();
                            setValue();
                        } else {
                            finish();
                            showShort(detail.getMsg());
                        }
                    }
                });
    }

    private void setValue() {
        data.getLawyer().getServiceList().get(0).setCheck(true);
        service_notes.setText(data.getLawyer().getServiceList().get(0).getNotes());

        Glide.with(this).load(data.getLawyer().getHeadURL()).into(lawyerHeader);
        lawyerName.setText(data.getLawyer().getName());
        lawyerOffice.setText(data.getLawyer().getLawyerOfficeName());

        if (data.getLawyer().getTags().size() > 0) {
            tvTag1.setVisibility(View.VISIBLE);
            tvTag1.setText(data.getLawyer().getTags().get(0));
        }

        if (data.getLawyer().getTags().size() > 1) {
            tvTag2.setVisibility(View.VISIBLE);
            tvTag2.setText(data.getLawyer().getTags().get(1));
        }

        if (data.getLawyer().getTags().size() > 2) {
            tvTag3.setVisibility(View.VISIBLE);
            tvTag3.setText(data.getLawyer().getTags().get(2));
        }
        special_rl.setLayoutManager(new GridLayoutManager(LawyerDetailActivity.this, 4));
        specialAdapter = new SpecialAdapter();
        special_rl.setAdapter(specialAdapter);

        tagsFl.setLayoutManager(new GridLayoutManager(LawyerDetailActivity.this, 4));
        commentTagAdapter = new CommentTagAdapter();
        tagsFl.setAdapter(commentTagAdapter);

        String[] mValues = new String[data.getLawyer().getTags().size()];
        for (int i = 0; i < data.getLawyer().getTags().size(); i++) {
            mValues[i] = data.getLawyer().getTags().get(i);
        }

        serviceCount.setText(String.valueOf(data.getLawyer().getServiceCount()));
        serviceScore.setText("" + data.getLawyer().getServiceScore() + "%");
        isFavorite.setFavorite(data.getLawyer().isFavorite());
        if (isFavorite.isFavorite()) {
            favoriteTv.setText("已关注");
            favoriteIv.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.star_o));
        } else {
            favoriteTv.setText("关注");
            favoriteIv.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.star_g));
        }
        llFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toFavorite();
            }
        });


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        h_service_type.setLayoutManager(linearLayoutManager);
        serviceTypeAdapter = new ServiceTypeAdapter();
        h_service_type.setAdapter(serviceTypeAdapter);
        price = data.getLawyer().getServiceList().get(0).getPriceList().get(0).getPrice();
        cycle = data.getLawyer().getServiceList().get(0).getPriceList().get(0).getCycle();
        consulting.setText(data.getLawyer().getServiceList().get(0).getServiceName() + price + "元/" + cycle);

        consulting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type == 1) {
                    startActivity(PersonLawyerActivity.class, "lawyerId", lawyerId + "");
                } else if (type == 2) {
                    Intent intent = new Intent(LawyerDetailActivity.this, TxtPicAskActivity.class);
                    intent.putExtra("lawyerId", lawyerId);
                    startActivity(intent);
                } else {
                    showShort("电话咨询");
                }
            }
        });


        headerMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LawyerDetailActivity.this, LawyerIntroActivity.class);
                intent.putExtra("lawyerId", lawyerId);
                intent.putExtra("office", data.getLawyer().getLawyerOfficeName());
                intent.putExtra("notes", data.getLawyer().getNotes());
                startActivity(intent);
            }
        });
    }

    private void toFavorite() {

        getResultOnNext = new SubscriberOnNextListener<IsFavorite>() {
            @Override
            public void onNext(IsFavorite favorite) {
                initIsFavorite();
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };

        retrofitUtil.getFavoriteLawyer("2", "3", !(isFavorite.isFavorite()), new ProgressSubscriber<IsFavorite>(getResultOnNext, LawyerDetailActivity.this, true));

    }

    private void initIsFavorite() {
        getResultOnNext = new SubscriberOnNextListener<IsFavorite>() {
            @Override
            public void onNext(IsFavorite o) {
                isFavorite = o;
                if (isFavorite.isFavorite()) {
                    //已经关注过了
                    favoriteTv.setText("已关注");
                    favoriteIv.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.star_o));
                } else {
                    //还未关注该律师
                    favoriteTv.setText("关注");
                    favoriteIv.setImageDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.star_g));
                }
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };
        retrofitUtil.getIsFavorite("2", "3", new ProgressSubscriber<IsFavorite>(getResultOnNext, LawyerDetailActivity.this, false));

    }

    private class SpecialAdapter extends RecyclerView.Adapter<SpecialAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_law_tag, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv_tag_text.setText(data.getLawyer().getSpecial().get(position));
        }

        @Override
        public int getItemCount() {
            return data.getLawyer().getSpecial().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_tag_text;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_tag_text = (TextView) itemView.findViewById(R.id.tv_tag_text);
            }
        }
    }

    private class CommentTagAdapter extends RecyclerView.Adapter<CommentTagAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_law_tag, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            if (position == getItemCount() - 1) {

                holder.tv_tag_text.setText("送心意(" + data.getLawyer().getGiveMoneyCount() + ")");
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        serviceList.setVisibility(View.GONE);
                        giveMoneyList.setVisibility(View.VISIBLE);
                        data.getLawyer().setPosition(position);
//                        score = data.getLawyer().getScoreCounts().get(position).getScore();
                        commentTagAdapter.notifyDataSetChanged();
                        index = 1;
                        initCListData();
                    }
                });
            } else {

                holder.tv_tag_text.setText(data.getLawyer().getScoreCounts().get(position).getScoreShowName() + "(" +
                        data.getLawyer().getScoreCounts().get(position).getScount() + ")");

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        serviceList.setVisibility(View.VISIBLE);
                        giveMoneyList.setVisibility(View.GONE);
                        data.getLawyer().setPosition(position);
                        score = data.getLawyer().getScoreCounts().get(position).getScore();
                        commentTagAdapter.notifyDataSetChanged();
                        index = 1;
                        initCListData();
                    }
                });
            }

            if (data.getLawyer().getPosition() == position) {
                holder.tv_tag_text.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                holder.tv_tag_text.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
            } else {
                holder.tv_tag_text.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.grey));
                holder.tv_tag_text.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_unselect));
            }

        }

        @Override
        public int getItemCount() {
            return data.getLawyer().getScoreCounts().size() + 1;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_tag_text;

            public ViewHolder(View itemView) {
                super(itemView);
                tv_tag_text = (TextView) itemView.findViewById(R.id.tv_tag_text);
            }
        }
    }

    private class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_comment, parent, false));

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.comName.setText(comData.get(position).getPhoneNo());
            holder.comContent.setText(comData.get(position).getContent());
            holder.comTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(comData.get(position).getCommentDate()));
            holder.comType.setText(comData.get(position).getServiceName());
            holder.comTxt.setText(comData.get(position).getNotes());

        }

        @Override
        public int getItemCount() {
            return comData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView comName;
            private TextView comTime;
            private TextView comType;
            private TextView comTxt;
            private TextView comContent;

            public ViewHolder(View itemView) {
                super(itemView);
                comName = (TextView) itemView.findViewById(R.id.com_name);
                comTime = (TextView) itemView.findViewById(R.id.com_time);
                comType = (TextView) itemView.findViewById(R.id.com_type);
                comTxt = (TextView) itemView.findViewById(R.id.com_txt);
                comContent = (TextView) itemView.findViewById(R.id.com_content);
            }
        }

    }

    private class GiveMoneyListAdapter extends RecyclerView.Adapter<GiveMoneyListAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_give_money, parent, false));

        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            holder.comName.setText(moneyData.get(position).getPhoneNo());
            holder.comTime.setText(new SimpleDateFormat("yyyy-MM-dd").format(moneyData.get(position).getGiveTime()));
            holder.comMoney.setText(moneyData.get(position).getMoney() + "元");
            holder.comTxt.setText(moneyData.get(position).getSummary());

        }

        @Override
        public int getItemCount() {
            return moneyData.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView comName;
            private TextView comTime;
            private TextView comMoney;
            private TextView comTxt;

            public ViewHolder(View itemView) {
                super(itemView);
                comName = (TextView) itemView.findViewById(R.id.com_name);
                comTime = (TextView) itemView.findViewById(R.id.com_time);
                comMoney = (TextView) itemView.findViewById(R.id.com_money);
                comTxt = (TextView) itemView.findViewById(R.id.com_txt);
            }
        }

    }

    private class ServiceTypeAdapter extends RecyclerView.Adapter<ServiceTypeAdapter.ViewHolder> {


        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new ViewHolder(LayoutInflater.from(getBaseContext()).inflate(R.layout.cell_service_type, parent, false));
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {

            Glide.with(LawyerDetailActivity.this).load(data.getLawyer().getServiceList().get(position).getImgURL()).into(holder.service_pic);
            holder.service_name.setText(data.getLawyer().getServiceList().get(position).getServiceName());

            if (data.getLawyer().getServiceList().get(position).isCheck()) {
                holder.ll_type.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.tag_select));
            } else {
                holder.ll_type.setBackgroundColor(ContextCompat.getColor(getBaseContext(), R.color.white));
            }


            holder.ll_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    type = data.getLawyer().getServiceList().get(position).getServiceType();
                    consulting.setText(data.getLawyer().getServiceList().get(position).getServiceName()
                            + data.getLawyer().getServiceList().get(position).getPriceList().get(0).getPrice()
                            + "元/"
                            + data.getLawyer().getServiceList().get(position).getPriceList().get(0).getCycle());

                    if (!data.getLawyer().getServiceList().get(position).isCheck()) {
                        for (int i = 0; i < data.getLawyer().getServiceList().size(); i++) {
                            data.getLawyer().getServiceList().get(i).setCheck(false);
                        }
                        data.getLawyer().getServiceList().get(position).setCheck(true);
                        serviceTypeAdapter.notifyDataSetChanged();
                    }
                    service_notes.setText(data.getLawyer().getServiceList().get(position).getNotes());
                }
            });

        }

        @Override
        public int getItemCount() {
            return data.getLawyer().getServiceList().size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            private LinearLayout ll_type;
            private CircleImageView service_pic;
            private TextView service_name;

            public ViewHolder(View itemView) {
                super(itemView);
                ll_type = (LinearLayout) itemView.findViewById(R.id.ll_type);
                service_pic = (CircleImageView) itemView.findViewById(R.id.service_pic);
                service_name = (TextView) itemView.findViewById(R.id.service_name);
            }
        }
    }

    private void initPopupClick() {

        tv_cancel_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        tv_yes_popup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectMoney.equals("")) {
                    showShort("请选择金额");
                } else {
                    popupWindow.dismiss();
                    showShort("选择" + selectMoney + "元 并说" + et_desc_popup.getText().toString());
                    startActivity(PayLawyerActivity.class,
                            "name", "张三",
                            "money", selectMoney + "",
                            "summary", et_desc_popup.getText().toString(),
                            "userServiceId", "-1");
                }
            }
        });
        tv_sel_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMoney = "2.00";
                et_money_popup.setText(selectMoney);
                tv_sel_2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
                tv_sel_4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                tv_sel_4.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_6.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_8.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
            }
        });
        tv_sel_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMoney = "4.00";
                et_money_popup.setText(selectMoney);
                tv_sel_4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
                tv_sel_2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_4.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                tv_sel_2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_6.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_8.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
            }
        });
        tv_sel_6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMoney = "6.00";
                et_money_popup.setText(selectMoney);
                tv_sel_6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
                tv_sel_4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_6.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                tv_sel_4.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_8.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
            }
        });
        tv_sel_8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectMoney = "8.00";
                et_money_popup.setText(selectMoney);
                tv_sel_8.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.tag_select));
                tv_sel_4.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_6.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_2.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.gray_un_four));
                tv_sel_8.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.white));
                tv_sel_4.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_6.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
                tv_sel_2.setTextColor(ContextCompat.getColor(getBaseContext(), R.color.blackTopic));
            }
        });
    }

}