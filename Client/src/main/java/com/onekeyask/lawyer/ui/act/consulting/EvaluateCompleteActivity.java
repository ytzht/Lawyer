package com.onekeyask.lawyer.ui.act.consulting;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.Html;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.HomePage;
import com.onekeyask.lawyer.entity.IsFavorite;
import com.onekeyask.lawyer.global.BaseToolBarActivity;
import com.onekeyask.lawyer.global.Constant;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.utils.UserService;

import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.OnClick;

public class EvaluateCompleteActivity extends BaseToolBarActivity {

    @BindView(R.id.iv_top_comp)
    ImageView ivTopComp;
    @BindView(R.id.tv_score_comp)
    TextView tvScoreComp;
    @BindView(R.id.tv_hint_comp)
    TextView tvHintComp;
    @BindView(R.id.rl_give_money)
    RelativeLayout rlGiveMoney;
    @BindView(R.id.rl_add_focus)
    RelativeLayout rlAddFocus;
    @BindView(R.id.ll_give_btn)
    LinearLayout llGiveBtn;
    @BindView(R.id.btn_complete_comp)
    TextView btnComp;
    @BindView(R.id.tv_favorite)
    TextView tvFavorite;


    private TextView tv_sel_2;
    private TextView tv_sel_4;
    private TextView tv_sel_6;
    private TextView tv_sel_8;
    private TextView tv_cancel_popup;
    private TextView tv_yes_popup;

    private boolean giveMoney;
    private int lawyerId;
    private PopupWindow popupWindow = null;
    private EditText et_money_popup, et_desc_popup;
    private View popupView;
    private String selectMoney = "2.00";
    private String userServiceId = "";
    private UserService service;
    private String lawName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_complete);
        setToolbarText("评价律师");


        initView();


    }

    private void initView() {

        service = new UserService(getBaseContext());
        userServiceId = getIntent().getStringExtra("userServiceId");
        lawName = getIntent().getStringExtra("lawName");
        lawyerId = getIntent().getIntExtra("lawyerId", Constant.lawyerId);

        initIsFavorite();


        SubscriberOnNextListener<HomePage> listener = new SubscriberOnNextListener<HomePage>() {
            @Override
            public void onNext(final HomePage homePage) {

                Glide.with(EvaluateCompleteActivity.this).load(homePage.getAdList().get(0).getPicURL()).into(ivTopComp);

            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };
        retrofitUtil.getHomePage(new ProgressSubscriber<HomePage>(listener, EvaluateCompleteActivity.this, false));


        tvScoreComp.setText(Html.fromHtml("感谢您的评价，赠送给您 <font color='#f79f0a'>30</font> 积分"));

        giveMoney = getIntent().getBooleanExtra("giveMoney", true);

        if (giveMoney) {

            tvHintComp.setHint("用户的好评就是对律师最大的鼓励，我们会做得更好");
            llGiveBtn.setVisibility(View.VISIBLE);
            btnComp.setVisibility(View.GONE);

        } else {
            btnComp.setVisibility(View.VISIBLE);
            llGiveBtn.setVisibility(View.GONE);
            tvHintComp.setText(Html.fromHtml("<font color='#5A5A5A'>感谢您对</font><font color='#3491e9'>张晓律师</font><font color='#5A5A5A'>的支持，您的留言已留在心意墙上！</font>"));
        }
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
    }

    private IsFavorite isFavorite;

    private void initIsFavorite() {
        getResultOnNext = new SubscriberOnNextListener<IsFavorite>() {
            @Override
            public void onNext(IsFavorite o) {
                isFavorite = o;
                if (isFavorite.isFavorite()) {
                    //已经关注过了
                    tvFavorite.setText("已关注");
                    rlAddFocus.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.full_gray_four));
                } else {
                    //还未关注该律师
                    tvFavorite.setText("+  添加关注");
                    rlAddFocus.setBackground(ContextCompat.getDrawable(getBaseContext(), R.drawable.full_green_four));
                }
            }

            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };
        retrofitUtil.getIsFavorite(service.getUserId(), lawyerId, new ProgressSubscriber<IsFavorite>(getResultOnNext, EvaluateCompleteActivity.this, false));

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

        retrofitUtil.getFavoriteLawyer(service.getUserId(), lawyerId, !(isFavorite.isFavorite()), new ProgressSubscriber<IsFavorite>(getResultOnNext, EvaluateCompleteActivity.this, true));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_complete, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menu_complete) {
            finish();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }

        return false;
    }

    @OnClick({R.id.rl_give_money, R.id.rl_add_focus, R.id.btn_complete_comp})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_give_money:
                popupWindow = getPopwindow(popupView);

                break;
            case R.id.rl_add_focus:
                toFavorite();
                break;
            case R.id.btn_complete_comp:
                finish();
                break;

        }
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
        popupWindow.showAtLocation(rlGiveMoney, Gravity.BOTTOM, 0, 0);
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

                    double input = (Double.parseDouble((new DecimalFormat("#.00")).format(Double.parseDouble(selectMoney))));

                    if (input == 0) {
                        showShort("输入金额不能小于0.01元");
                    } else {
                        if (input > 200) {
                            showShort("输入金额不能大于200元");
                        } else {
                            popupWindow.dismiss();
//                    showShort("选择" + selectMoney + "元 并说" + et_desc_popup.getText().toString());

                            Intent intent = new Intent(EvaluateCompleteActivity.this, PayLawyerActivity.class);
                            intent.putExtra("name", lawName);
                            intent.putExtra("start", "eva");
                            intent.putExtra("type", "2");
                            intent.putExtra("lawyerId", lawyerId);
                            intent.putExtra("money", input);
                            intent.putExtra("summary", et_desc_popup.getText().toString());
                            intent.putExtra("userServiceId", userServiceId);
                            startActivity(intent);

                        }
                    }


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
