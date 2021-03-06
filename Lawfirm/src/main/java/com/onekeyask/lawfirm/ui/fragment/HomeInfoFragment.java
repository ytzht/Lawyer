package com.onekeyask.lawfirm.ui.fragment;

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

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.MyMoney;
import com.onekeyask.lawfirm.entity.PersonalInfo;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseFragment;
import com.onekeyask.lawfirm.ui.act.me.MyWalletActivity;
import com.onekeyask.lawfirm.ui.act.me.OpinionActivity;
import com.onekeyask.lawfirm.ui.act.me.SettingActivity;
import com.onekeyask.lawfirm.ui.act.user.LoginActivity;
import com.onekeyask.lawfirm.ui.act.user.MyInfoActivity;
import com.onekeyask.lawfirm.utils.UserService;
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
    @BindView(R.id.opinion)
    LinearLayout opinion;
    @BindView(R.id.my_money)
    TextView myMoney;
    @BindView(R.id.service_count)
    TextView service_count;
    @BindView(R.id.service_score)
    TextView service_score;
    @BindView(R.id.tv_office)
    TextView tv_office;
    @BindView(R.id.my_wallet)
    LinearLayout myWallet;
    @BindView(R.id.customer_server)
    LinearLayout customerServer;
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
        userService = new UserService(getActivity());
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();


        OkGo.<String>get(Apis.getPersonalInfo).params("lawyerId", userService.getLawyerId()).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                PersonalInfo info = (new Gson()).fromJson(response.body(), PersonalInfo.class);
                if (info.getCode() == 0) {
                    service_count.setText(info.getData().getServiceCount());
                    service_score.setText(info.getData().getServiceScore() + "%");
                    userName.setText(info.getData().getLawyerName());
                    if (info.getData().getHeadURL().equals("")) {
                        Glide.with(getActivity()).load(info.getData().getHeadURL())
                                .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar).into(userHeader);
                    }else {
                        Picasso.with(getActivity()).load(info.getData().getHeadURL())
                                .placeholder(R.drawable.ic_member_avatar).error(R.drawable.ic_member_avatar).into(userHeader);

                    }
                    tv_office.setText(info.getData().getOfficeName());

                }
            }
        });

        OkGo.<String>get(Apis.MyWallet).params("lawyerId", UserService.service(getActivity()).getLawyerId()).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {
                MyMoney money = (new Gson()).fromJson(response.body(), MyMoney.class);
                if (money.getCode() == 0){
                    myMoney.setText("￥"+String.valueOf(money.getData().getMyMoney()));
                }else {
                    showShort(money.getMsg());
                }
            }
        });


    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.my_wallet, R.id.customer_server, R.id.setting, R.id.my_header, R.id.opinion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
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

                if (userService.getToken().equals("-1")) {
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
                Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-86393094"));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }
}
