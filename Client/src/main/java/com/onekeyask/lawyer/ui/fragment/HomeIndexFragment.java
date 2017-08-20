package com.onekeyask.lawyer.ui.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.HomePage;
import com.onekeyask.lawyer.global.BaseFragment;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.ui.act.consulting.ConsultingDetailActivity;
import com.onekeyask.lawyer.ui.act.lawyer.SearchLawActivity;
import com.onekeyask.lawyer.ui.act.user.TopMsgActivity;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by zht on 2017/04/10 15:13
 */

public class HomeIndexFragment extends BaseFragment implements View.OnClickListener {

    private View view;
    private TextView search_et;
    private ImageView iv_top_msg;
    private RelativeLayout rl_quick_consulting, rl_look_lawyer, rl_project_one, rl_project_two, rl_project_three, rl_project_four;
    private Banner banner;
    private List<String> image_url = new ArrayList<>();
    private List<String> banner_img = new ArrayList<>();
    private TextView tv_more_solutions;
    private RecyclerView rlv_index;
//    private SubscriberOnNextListener getResultOnNext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_index_fragment, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        search_et = (TextView) view.findViewById(R.id.search_et);
        iv_top_msg = (ImageView) view.findViewById(R.id.iv_top_msg);
        iv_top_msg.setOnClickListener(this);
        rl_quick_consulting = (RelativeLayout) view.findViewById(R.id.rl_quick_consulting);
        rl_quick_consulting.setOnClickListener(this);
        rl_look_lawyer = (RelativeLayout) view.findViewById(R.id.rl_look_lawyer);
        rl_look_lawyer.setOnClickListener(this);
        rl_project_one = (RelativeLayout) view.findViewById(R.id.rl_project_one);
        rl_project_one.setOnClickListener(this);
        rl_project_two = (RelativeLayout) view.findViewById(R.id.rl_project_two);
        rl_project_two.setOnClickListener(this);
        rl_project_three = (RelativeLayout) view.findViewById(R.id.rl_project_three);
        rl_project_three.setOnClickListener(this);
        rl_project_four = (RelativeLayout) view.findViewById(R.id.rl_project_four);
        rl_project_four.setOnClickListener(this);
        tv_more_solutions = (TextView) view.findViewById(R.id.tv_more_solutions);
        tv_more_solutions.setOnClickListener(this);
        rlv_index = (RecyclerView) view.findViewById(R.id.rlv_index);
        rlv_index.setLayoutManager(new LinearLayoutManager(getActivity()) {
            @Override
            public boolean canScrollVertically() {
                return false;
            }
        });
        rlv_index.setAdapter(new IndexAdapter());
        initBanner(view);

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
        banner.setBannerAnimation(Transformer.Default);
//        switch ((int) (Math.random() * (17))) {
//            case 0:
//                banner.setBannerAnimation(Transformer.Default);
//                break;
//            case 1:
//                banner.setBannerAnimation(Transformer.Accordion);
//                break;
//            case 2:
//                banner.setBannerAnimation(Transformer.BackgroundToForeground);
//                break;
//            case 3:
//                banner.setBannerAnimation(Transformer.ForegroundToBackground);
//                break;
//            case 4:
//                banner.setBannerAnimation(Transformer.CubeIn);
//                break;
//            case 5:
//                banner.setBannerAnimation(Transformer.CubeOut);
//                break;
//            case 6:
//                banner.setBannerAnimation(Transformer.DepthPage);
//                break;
//            case 7:
//                banner.setBannerAnimation(Transformer.FlipHorizontal);
//                break;
//            case 8:
//                banner.setBannerAnimation(Transformer.FlipVertical);
//                break;
//            case 9:
//                banner.setBannerAnimation(Transformer.RotateDown);
//                break;
//            case 10:
//                banner.setBannerAnimation(Transformer.RotateUp);
//                break;
//            case 11:
//                banner.setBannerAnimation(Transformer.ScaleInOut);
//                break;
//            case 12:
//                banner.setBannerAnimation(Transformer.Stack);
//                break;
//            case 13:
//                banner.setBannerAnimation(Transformer.Tablet);
//                break;
//            case 14:
//                banner.setBannerAnimation(Transformer.ZoomIn);
//                break;
//            case 15:
//                banner.setBannerAnimation(Transformer.ZoomOut);
//                break;
//            case 16:
//                banner.setBannerAnimation(Transformer.ZoomOutSlide);
//                break;
//        }

        SubscriberOnNextListener<HomePage> listener = new SubscriberOnNextListener<HomePage>() {
            @Override
            public void onNext(final HomePage homePage) {
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
        retrofitUtil.getHomePage(new ProgressSubscriber<HomePage>(listener, getActivity(), false));

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_quick_consulting:
                startActivity(ConsultingDetailActivity.class);
                break;
            case R.id.rl_look_lawyer:
                startActivity(SearchLawActivity.class);
                break;
            case R.id.rl_project_one:
                showShort("专题待一 模块尚未上线");
                break;
            case R.id.rl_project_two:
                showShort("专题待二 模块尚未上线");
                break;
            case R.id.rl_project_three:
                showShort("专题待三 模块尚未上线");
                break;
            case R.id.rl_project_four:
                showShort("专题待四 模块尚未上线");
                break;
            case R.id.tv_more_solutions:
                showShort("更多 模块尚未上线");
                break;
            case R.id.iv_top_msg:
                startActivity(TopMsgActivity.class);
        }
    }

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
            Glide.with(getActivity()).load(path).into(imageView);
        }


    }

    private class IndexAdapter extends RecyclerView.Adapter<IndexAdapter.ViewHolder> {

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public ViewHolder(View itemView) {
                super(itemView);
            }
        }
    }


}
