package com.onekeyask.lawfirm.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.GetRed;
import com.onekeyask.lawfirm.entity.HomePage;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseFragment;
import com.onekeyask.lawfirm.global.L;
import com.onekeyask.lawfirm.http.ProgressSubscriber;
import com.onekeyask.lawfirm.http.SubscriberOnNextListener;
import com.onekeyask.lawfirm.ui.act.index.GraphicConsultActivity;
import com.onekeyask.lawfirm.ui.act.index.PersonConsultActivity;
import com.onekeyask.lawfirm.ui.act.index.PhoneConsultActivity;
import com.onekeyask.lawfirm.ui.act.me.WithdrawalActivity;
import com.onekeyask.lawfirm.ui.act.user.IncomeDetailActivity;
import com.onekeyask.lawfirm.ui.act.user.LoginActivity;
import com.onekeyask.lawfirm.ui.act.user.TopMsgActivity;
import com.onekeyask.lawfirm.utils.UserService;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;


/**
 * Created by zht on 2017/04/10 15:13
 */

public class HomeIndexFragment extends BaseFragment {

    private View view;
    private TextView search_et;
    private Banner banner;
    private HomePage homePage;
    private Subscriber subscriber;
    private TextView tv_monthIncome_index, tv_balance_index, tv_tx, tv_detail;
    private List<String> image_url = new ArrayList<>();
    private List<String> banner_img = new ArrayList<>();
    private RelativeLayout rl_project_one, rl_project_two, rl_project_three, rl_project_four;
    private ImageView iv_top_msg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_index_fragment, container, false);
        initView(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initRed();
        initBanner(view);
    }

    private void initRed() {
        OkGo.<String>get(Apis.GetRed).params("userId", UserService.service(getActivity()).getLawyerId()).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                GetRed red = (new Gson()).fromJson(response.body(), GetRed.class);
                if (red.getCode() == 0) {

                    if (red.getData().getMessageIds().size() != 0) {
                        //消息中心的右上角小红点显示
                        iv_top_msg.setImageResource(R.drawable.tips_n);
                    } else {
                        iv_top_msg.setImageResource(R.drawable.tips_);
                    }

                } else {
//                    showShort(red.getMsg());
                }
            }
        });
    }

    private AlertDialog alert;
    private void initView(View view) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        search_et = (TextView) view.findViewById(R.id.search_et);
        tv_tx = (TextView) view.findViewById(R.id.tv_tx);
        tv_detail = (TextView) view.findViewById(R.id.tv_detail);
        tv_balance_index = (TextView) view.findViewById(R.id.tv_balance_index);
        rl_project_one = (RelativeLayout) view.findViewById(R.id.rl_project_one);
        rl_project_two = (RelativeLayout) view.findViewById(R.id.rl_project_two);
        rl_project_three = (RelativeLayout) view.findViewById(R.id.rl_project_three);
        rl_project_four = (RelativeLayout) view.findViewById(R.id.rl_project_four);
        iv_top_msg = (ImageView) view.findViewById(R.id.iv_top_msg);
        iv_top_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(TopMsgActivity.class);
            }
        });
        tv_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View view1 = LayoutInflater.from(getActivity()).inflate(R.layout.custom_dialog_share, null, false);
                alert = new AlertDialog.Builder(getActivity()).setView(view1).setCancelable(false).show();
                TextView tvMsg = (TextView) view1.findViewById(R.id.tv_msg);
                TextView cancel = (TextView) view1.findViewById(R.id.tv_cancel);
                TextView next = (TextView) view1.findViewById(R.id.tv_share_con);
                cancel.setVisibility(View.VISIBLE);
                tvMsg.setText("提现需十个工作日的时间才能到账，您是否需要继续？");
                next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(WithdrawalActivity.class);
                        if (alert.isShowing()) alert.dismiss();
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (alert.isShowing()) alert.dismiss();
                    }
                });
            }
        });

        tv_detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(IncomeDetailActivity.class);


            }
        });
        initBanner(view);

    }
    private void initBanner(View view) {
        banner = (Banner) view.findViewById(R.id.banner_index);

        banner_img.clear();
        banner_img.add(Apis.Base + "pic/1659");
//        banner_img.add(Apis.Base + "pic/1534");

        banner.setIndicatorGravity(BannerConfig.CENTER)
                .setBannerStyle(BannerConfig.CIRCLE_INDICATOR)
                .setBannerAnimation(Transformer.ZoomOut)
                .isAutoPlay(true)
                .setDelayTime(3000)
                .setImageLoader(new GlideImageLoader());
        banner.setImages(banner_img);
        switch ((int) (Math.random() * (17))) {
            case 0:
                banner.setBannerAnimation(Transformer.Default);
                break;
            case 1:
                banner.setBannerAnimation(Transformer.Accordion);
                break;
            case 2:
                banner.setBannerAnimation(Transformer.BackgroundToForeground);
                break;
            case 3:
                banner.setBannerAnimation(Transformer.ForegroundToBackground);
                break;
            case 4:
                banner.setBannerAnimation(Transformer.CubeIn);
                break;
            case 5:
                banner.setBannerAnimation(Transformer.CubeOut);
                break;
            case 6:
                banner.setBannerAnimation(Transformer.DepthPage);
                break;
            case 7:
                banner.setBannerAnimation(Transformer.FlipHorizontal);
                break;
            case 8:
                banner.setBannerAnimation(Transformer.FlipVertical);
                break;
            case 9:
                banner.setBannerAnimation(Transformer.RotateDown);
                break;
            case 10:
                banner.setBannerAnimation(Transformer.RotateUp);
                break;
            case 11:
                banner.setBannerAnimation(Transformer.ScaleInOut);
                break;
            case 12:
                banner.setBannerAnimation(Transformer.Stack);
                break;
            case 13:
                banner.setBannerAnimation(Transformer.Tablet);
                break;
            case 14:
                banner.setBannerAnimation(Transformer.ZoomIn);
                break;
            case 15:
                banner.setBannerAnimation(Transformer.ZoomOut);
                break;
            case 16:
                banner.setBannerAnimation(Transformer.ZoomOutSlide);
                break;
        }


        getResultOnNext = new SubscriberOnNextListener<HomePage>() {
            @Override
            public void onNext(HomePage page) {
                homePage = page;

//                tv_monthIncome_index.setText(Html.fromHtml("本月收入: <font color='#f79f0a'>￥" + homePage.getMonthIncome() + "元</font>"));
                tv_balance_index.setText(Html.fromHtml("我的钱包: <font color='#f79f0a'>￥" + homePage.getBalance() + "元</font>"));

                for (int i = 0; i < homePage.getServiceList().size(); i++) {
                    final int finalI = i;
                    switch (homePage.getServiceList().get(i).getServiceType()) {
                        case 1://私人律师
                            rl_project_four.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(PersonConsultActivity.class, "switch", homePage.getServiceList().get(finalI).isIsOn() + "");
                                }
                            });
                            break;
                        case 2://图文咨询
                            rl_project_two.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(GraphicConsultActivity.class, "switch", homePage.getServiceList().get(finalI).isIsOn() + "");
                                }
                            });
                            break;
                        case 3://电话咨询
                            rl_project_three.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(PhoneConsultActivity.class, "switch", homePage.getServiceList().get(finalI).isIsOn() + "");
                                }
                            });
                            break;
//                        case 9://打赏咨询
//                            rl_project_one.setOnClickListener(new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    startActivity(PayConsultActivity.class, "switch", homePage.getServiceList().get(finalI).isIsOn() + "");
//                                }
//                            });
                    }
                }


                //banner
                image_url.clear();
                for (int i = 0; i < homePage.getAdList().size(); i++) {
                    image_url.add(homePage.getAdList().get(i).getPicURL());
                }
                if (image_url.size() > 0) {
                    banner.setImages(image_url).start();

                    banner.setOnBannerListener(new OnBannerListener() {
                        @Override
                        public void OnBannerClick(int position) {
                            L.d(position+"");
                            showShort(homePage.getAdList().get(position).getContent());
                        }
                    });

                } else {
                    image_url.clear();
                    banner.setImages(banner_img).start();
                }

            }

            @Override
            public void onError(int code, String message) {
                if (code == -106){
                    startActivity(LoginActivity.class);
                    getActivity().finish();
                }else {
                    image_url.clear();
                    banner.setImages(banner_img).start();
                }
            }
        };

        retrofitUtil.getHomePage(UserService.service(getActivity()).getLawyerId(), new ProgressSubscriber<HomePage>(getResultOnNext, getActivity(), false));
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
            Glide.with(getActivity()).load(path).into(imageView);
        }


    }
}
