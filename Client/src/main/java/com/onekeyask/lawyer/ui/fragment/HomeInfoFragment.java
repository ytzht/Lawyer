package com.onekeyask.lawyer.ui.fragment;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.global.BaseFragment;
import com.onekeyask.lawyer.ui.act.me.EarnPointsActivity;
import com.onekeyask.lawyer.ui.act.me.MyIntegralActivity;
import com.onekeyask.lawyer.ui.act.me.MyWalletActivity;
import com.onekeyask.lawyer.ui.act.me.OpinionActivity;
import com.onekeyask.lawyer.ui.act.me.SettingActivity;
import com.onekeyask.lawyer.ui.act.user.LoginActivity;
import com.onekeyask.lawyer.ui.act.user.MyInfoActivity;
import com.onekeyask.lawyer.utils.UserService;

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
    private View view;

    private UserService userService;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_info_fragment, container, false);
        unbinder = ButterKnife.bind(this, view);
        initView();
        return view;
    }

    private void initView() {
        userService = new UserService(getActivity());
    }

    @Override
    public void onResume() {
        super.onResume();
        if (!userService.getToken().equals("")) {
            userName.setText(userService.getUserName());
            if (userService.getHeadURL().equals("")){
                userHeader.setImageResource(R.drawable.ic_member_avatar);
            }else {
                Glide.with(getActivity()).load(userService.getHeadURL()).skipMemoryCache(true)
                        .diskCacheStrategy(DiskCacheStrategy.NONE).into(userHeader);
            }
        }else {
            userName.setText("登录/注册");
            userHeader.setImageResource(R.drawable.ic_member_avatar);
        }

//        Picasso.with(getActivity()).load(userService.getHeadURL()).into(userHeader);
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
                startActivity(MyIntegralActivity.class);
                break;
            case R.id.how_in://赚取积分
                startActivity(EarnPointsActivity.class);
                break;
            case R.id.my_wallet://我的钱包
                startActivity(MyWalletActivity.class);
                break;
            case R.id.customer_server://客服
                showAlert();
                break;
            case R.id.opinion://意见反馈
                startActivity(OpinionActivity.class);
                break;
            case R.id.setting:
                startActivity(SettingActivity.class);
                break;
            case R.id.my_header://头部登录

                if (userService.getToken().equals("")) {
                    startActivity(LoginActivity.class);
                }else {
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
        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) dialog.dismiss();
            }
        });
    }
}
