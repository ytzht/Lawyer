package com.onekeyask.lawfirm.ui.fragment;

import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Bundle;
import android.text.Html;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.WindowManager.LayoutParams;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.HomePage;
import com.onekeyask.lawfirm.global.BaseFragment;
import com.onekeyask.lawfirm.http.ProgressSubscriber;
import com.onekeyask.lawfirm.http.SubscriberOnNextListener;
import com.onekeyask.lawfirm.ui.act.index.GraphicConsultActivity;
import com.onekeyask.lawfirm.ui.act.index.PayConsultActivity;
import com.onekeyask.lawfirm.ui.act.index.PersonConsultActivity;
import com.onekeyask.lawfirm.ui.act.index.PhoneConsultActivity;
import com.onekeyask.lawfirm.utils.ScreenUtils;
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
    private EditText search_et;
    private Banner banner;
    private HomePage homePage;
    private Subscriber subscriber;
    private TextView tv_monthIncome_index, tv_balance_index;
    private List<String> image_url = new ArrayList<>();
    private List<String> banner_img = new ArrayList<>();
    private RelativeLayout rl_project_one, rl_project_two, rl_project_three, rl_project_four;

    private ImageView img;

    private WindowManager windowManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_index_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        search_et = (EditText) view.findViewById(R.id.search_et);
        tv_monthIncome_index = (TextView) view.findViewById(R.id.tv_monthIncome_index);
        tv_balance_index = (TextView) view.findViewById(R.id.tv_balance_index);
        rl_project_one = (RelativeLayout) view.findViewById(R.id.rl_project_one);
        rl_project_two = (RelativeLayout) view.findViewById(R.id.rl_project_two);
        rl_project_three = (RelativeLayout) view.findViewById(R.id.rl_project_three);
        rl_project_four = (RelativeLayout) view.findViewById(R.id.rl_project_four);

        initBanner(view);


//        initWindow(view);
    }

    private void initWindow(View view) {
        windowManager = getActivity().getWindowManager();

        // 动态初始化图层
        img = new ImageView(getActivity());
        img.setLayoutParams(new LayoutParams(
                android.view.ViewGroup.LayoutParams.MATCH_PARENT,
                android.view.ViewGroup.LayoutParams.MATCH_PARENT));
        img.setScaleType(ImageView.ScaleType.FIT_XY);
        img.setImageResource(R.drawable.guide);

        // 设置LayoutParams参数
        LayoutParams params = new WindowManager.LayoutParams();
        // 设置显示的类型，TYPE_PHONE指的是来电话的时候会被覆盖，其他时候会在最前端，显示位置在stateBar下面，其他更多的值请查阅文档
        params.type = WindowManager.LayoutParams.TYPE_PHONE;
        // 设置显示格式
        params.format = PixelFormat.RGBA_8888;
        // 设置对齐方式
        params.gravity = Gravity.LEFT | Gravity.TOP;
        // 设置宽高
        params.width = ScreenUtils.getScreenWidth(getActivity());
        params.height = ScreenUtils.getScreenHeight(getActivity());

        // 添加到当前的窗口上
        windowManager.addView(img, params);

        // 点击图层之后，将图层移除
        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                windowManager.removeView(img);
            }
        });

    }


    private void initBanner(View view) {
        banner = (Banner) view.findViewById(R.id.banner_index);

        banner_img.clear();
        banner_img.add("http://139.198.11.78:8080/mylawyer/pic/7");
        banner_img.add("http://139.198.11.78:8080/mylawyer/pic/1534");

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

                tv_monthIncome_index.setText(Html.fromHtml("本月收入: <font color='#f79f0a'>￥" + homePage.getMonthIncome() + "元</font>"));
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
                        case 9://打赏咨询
                            rl_project_one.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startActivity(PayConsultActivity.class, "switch", homePage.getServiceList().get(finalI).isIsOn() + "");
                                }
                            });
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
                showShort(message);
                image_url.clear();
                banner.setImages(banner_img).start();
            }
        };

        retrofitUtil.getHomePage("3", new ProgressSubscriber<HomePage>(getResultOnNext, getActivity(), false));
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
            Glide.with(getActivity()).load(path).into(imageView);
        }


    }
}
