package com.onekeyask.lawyer.ui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.PointsInfo;
import com.onekeyask.lawyer.entity.PriceList;
import com.onekeyask.lawyer.global.BaseFragment;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.ui.act.me.EarnPointsActivity;
import com.onekeyask.lawyer.ui.act.me.MyIntegralActivity;
import com.onekeyask.lawyer.ui.act.me.MyWalletActivity;
import com.onekeyask.lawyer.ui.act.me.OpinionActivity;
import com.onekeyask.lawyer.ui.act.me.SettingActivity;
import com.onekeyask.lawyer.ui.act.user.LoginActivity;
import com.onekeyask.lawyer.ui.act.user.MyInfoActivity;
import com.onekeyask.lawyer.utils.UserService;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zht on 2017/04/10 15:17
 */

public class HomeInfoFragment extends BaseFragment {

    @BindView(R.id.user_header)
    CircleImageView userHeader;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.user_info)
    ImageView userInfo;
    @BindView(R.id.my_score)
    TextView myScore;
    @BindView(R.id.my_in)
    LinearLayout myIn;
    @BindView(R.id.how_in)
    TextView howIn;
    @BindView(R.id.my_money)
    TextView myMoney;
    @BindView(R.id.my_wallet)
    LinearLayout myWallet;
    @BindView(R.id.customer_server)
    LinearLayout customerServer;
    @BindView(R.id.opinion)
    LinearLayout opinion;
    @BindView(R.id.setting)
    LinearLayout setting;
    Unbinder unbinder;
    @BindView(R.id.my_header)
    LinearLayout myHeader;
    @BindView(R.id.ll_score)
    LinearLayout ll_score;
    private View view;

    private UserService userService;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_info_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        userService = new UserService(getActivity());
        return view;
    }

    public SubscriberOnNextListener getResultOnNext;

    private void initView() {

        if (userService.isLogin()) {
            getResultOnNext = new SubscriberOnNextListener<PointsInfo>() {
                @Override
                public void onNext(PointsInfo info) {
                    myScore.setText(String.valueOf(info.getPoints()));
                }

                @Override
                public void onError(int code, String message) {
                    showShort(message);
                }
            };
            retrofitUtil.getPointsInfo(UserService.service(getActivity()).getUserId(), new ProgressSubscriber<PointsInfo>(getResultOnNext, getActivity(), false));
        }


    }

    @Override
    public void onResume() {
        super.onResume();
        if (!userService.getToken().equals("-1")) {
            userName.setText(userService.getUserName());
            if (userService.getHeadURL().equals("")) {
                userHeader.setImageResource(R.drawable.ic_member_avatar);
            } else {
                Picasso.with(getActivity()).load(userService.getHeadURL())
                        .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar).into(userHeader);
            }
            initView();
            ll_score.setVisibility(View.VISIBLE);

            SubscriberOnNextListener getResultOnNext = new SubscriberOnNextListener<PriceList>() {
                @Override
                public void onNext(PriceList list) {
                    myMoney.setText("￥"+String.valueOf(list.getBalance()));
                }

                @Override
                public void onError(int code, String message) {
                    myMoney.setText("");
                }
            };

            retrofitUtil.getPriceList(UserService.service(getActivity()).getUserId(), new ProgressSubscriber<PriceList>(getResultOnNext, getActivity(), false));



        } else {
            ll_score.setVisibility(View.GONE);
            userName.setText("登录/注册");
            userHeader.setImageResource(R.drawable.ic_member_avatar);
        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_in, R.id.my_wallet, R.id.customer_server, R.id.opinion, R.id.setting, R.id.my_header, R.id.how_in})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.my_in://我的积分

                if (userService.isLogin()) {
                    startActivity(MyIntegralActivity.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.how_in://赚取积分
                if (userService.isLogin()) {
                    startActivity(EarnPointsActivity.class);
                } else {
                    startActivity(LoginActivity.class);
                }

                break;
            case R.id.my_wallet://我的钱包
                if (userService.isLogin()) {
                    startActivity(MyWalletActivity.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.customer_server://客服
                showAlert();
                break;
            case R.id.opinion://意见反馈
                if (userService.isLogin()) {
                    startActivity(OpinionActivity.class);
                } else {
                    startActivity(LoginActivity.class);
                }
                break;
            case R.id.setting:
                startActivity(SettingActivity.class);


                break;
            case R.id.my_header://头部登录

                if (userService.getToken().equals("-1")) {
                    startActivity(LoginActivity.class);
                } else {
                    startActivity(MyInfoActivity.class);
                }
                break;
        }
    }


    private AlertDialog dialog;

    private void showAlert() {
        View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_dialog_tel, null, false);
        dialog = new AlertDialog.Builder(getActivity()).setView(view1).setCancelable(true).show();

        ImageView iv_close = (ImageView) view1.findViewById(R.id.iv_close);
        LinearLayout call_ll = (LinearLayout) view1.findViewById(R.id.call_ll);
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) dialog.dismiss();
            }
        });
        call_ll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-62886288"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
