package com.onekeyask.lawyer.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
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
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.GetRed;
import com.onekeyask.lawyer.entity.HomePage;
import com.onekeyask.lawyer.entity.UserDiscoveries;
import com.onekeyask.lawyer.global.Apis;
import com.onekeyask.lawyer.global.BaseEvent;
import com.onekeyask.lawyer.global.BaseFragment;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.ui.act.consulting.ConsultingDetailActivity;
import com.onekeyask.lawyer.ui.act.lawyer.AskDetailActivity;
import com.onekeyask.lawyer.ui.act.search.SearchContentActivity;
import com.onekeyask.lawyer.ui.act.search.SearchLawActivity;
import com.onekeyask.lawyer.ui.act.user.LoginActivity;
import com.onekeyask.lawyer.ui.act.user.TopMsgActivity;
import com.onekeyask.lawyer.utils.UserService;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.Transformer;
import com.youth.banner.listener.OnBannerListener;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by zht on 2017/04/10 15:13
 */

public class HomeIndexFragment extends BaseFragment {

    private View view;
    private TextView search_et;
    private ImageView iv_top_msg, search_main;
    private SmartRefreshLayout refreshLayout;
    private List<String> image_url = new ArrayList<>();
    private List<String> banner_img = new ArrayList<>();
    private UserService service;
    private RecyclerView rlv_index;
    private int index = 1;
    private int size = 10;
    private boolean hasMore = true;
    private List<UserDiscoveries.DataBean.UserDiscoveriesBean> data = new ArrayList<>();
    private IndexAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_index_fragment, container, false);

        initView(view);

        return view;
    }

    private void initView(View view) {
        data.clear();
        service = UserService.service(getActivity());

        search_main = (ImageView) view.findViewById(R.id.search_main);
        search_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchContentActivity.class);
            }
        });
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        search_et = (TextView) view.findViewById(R.id.search_et);
        iv_top_msg = (ImageView) view.findViewById(R.id.iv_top_msg);
        rlv_index = (RecyclerView) view.findViewById(R.id.rlv_index);
        rlv_index.setLayoutManager(new LinearLayoutManager(getActivity()));
        rlv_index.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        iv_top_msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (UserService.service(getActivity()).isLogin()) {
                    startActivity(TopMsgActivity.class);
                }else {
                    startActivity(LoginActivity.class);
                }
            }
        });
        adapter = new IndexAdapter();
        rlv_index.setAdapter(adapter);

        refreshLayout = (SmartRefreshLayout) view.findViewById(R.id.index_refreshLayout);
        refreshLayout.setRefreshHeader(new ClassicsHeader(getActivity()));
        refreshLayout.setRefreshFooter(new ClassicsFooter(getActivity()));
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                refreshlayout.finishRefresh(200);
                index = 1;
                initData();
            }
        });
        refreshLayout.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                refreshlayout.finishLoadmore(200);
                if (hasMore) {
                    index += 1;
                    initData();
                }
            }
        });

    }


    @Override
    public void onResume() {
        super.onResume();
        index = 1;
        initData();
    }

    private void initRed() {
        OkGo.<String>get(Apis.GetRed).params("userId", UserService.service(getActivity()).getUserId()).execute(new StringCallback() {
            @Override
            public void onSuccess(Response<String> response) {

                GetRed red = (new Gson()).fromJson(response.body(), GetRed.class);
                if (red.getCode() == 0) {

                    if (red.getData().getMessageIds().size() != 0) {
                        //消息中心的右上角小红点显示
                        iv_top_msg.setImageResource(R.drawable.tips_n);
                    } else {
                        iv_top_msg.setImageResource(R.drawable.tips);
                    }

                } else {
//                    showShort(red.getMsg());
                }
            }
        });
    }

    private void initData() {
        OkGo.<String>get(Apis.Discovery)
                .params("userId", UserService.service(getActivity()).getUserId())
                .params("sort", "1")
                .params("page", index)
                .params("size", size)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        UserDiscoveries discoveries = (new Gson()).fromJson(response.body(), UserDiscoveries.class);
                        hasMore = discoveries.getData().isHasMore();
                        if (discoveries.getCode() == 0) {
                            if (index == 1) {
                                data.clear();
                                data.addAll(discoveries.getData().getUserDiscoveries());
                                rlv_index.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                data.addAll(discoveries.getData().getUserDiscoveries());
                                adapter.notifyDataSetChanged();
                            }
                        } else {
                            showShort(discoveries.getMsg());
                        }
                    }
                });
        initRed();

    }

    private void initBanner(final Banner banner) {


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

    private class GlideImageLoader extends ImageLoader {
        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            //具体方法内容自己去选择，次方法是为了减少banner过多的依赖第三方包，所以将这个权限开放给使用者去选择
            Glide.with(getActivity()).load(path).into(imageView);
        }


    }
    private class IndexAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


        @Override
        public int getItemViewType(int position) {

            if (position == 0) {
                return R.layout.cell_index_main;
            } else {
                return R.layout.cell_index;
            }
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(viewType, parent, false);

            if (viewType == R.layout.cell_index_main) {
                return new IndexViewHolder(view);
            } else {
                return new ViewHolder(view);
            }
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {

            if (position == 0) {
                initBanner(((IndexViewHolder) holder).banner);
                ((IndexViewHolder) holder).rl_quick_consulting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            startActivity(ConsultingDetailActivity.class);
                    }
                });
                ((IndexViewHolder) holder).rl_look_lawyer.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                            startActivity(SearchLawActivity.class);
                    }
                });
                ((IndexViewHolder) holder).tv_more_solutions.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EventBus.getDefault().post(BaseEvent.event(BaseEvent.GO_DISCOVER));
                    }
                });

                ((IndexViewHolder) holder).special_rlv.setAdapter(new SpecialAdapter(getActivity()));
                LinearLayoutManager manager = new LinearLayoutManager(getActivity());
                manager.setOrientation(LinearLayoutManager.HORIZONTAL);
                ((IndexViewHolder) holder).special_rlv.setLayoutManager(manager);

            } else {

                ((ViewHolder) holder).dis_type.setText(data.get(position - 1).getCategoryName());
                ((ViewHolder) holder).dis_context.setText(data.get(position - 1).getContent());
                ((ViewHolder) holder).dis_name.setText(data.get(position - 1).getLawyerName());
                ((ViewHolder) holder).tv_name.setText(data.get(position - 1).getUserPhoneNo());
                ((ViewHolder) holder).dis_office.setText(data.get(position - 1).getOfficeName());
                ((ViewHolder) holder).dis_count.setText(String.valueOf(data.get(position - 1).getSupportCount()));
                Glide.with(getActivity()).load(data.get(position - 1).getHeadURL()).into(((ViewHolder) holder).dis_img);
                ((ViewHolder) holder).dis_praise.setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.praise_g));

                ((ViewHolder) holder).itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (service.isLogin()) {
                            Intent intent = new Intent(getActivity(), AskDetailActivity.class);
                            intent.putExtra("cid", data.get(position - 1).getChatId());
                            intent.putExtra("lawyerName", data.get(position - 1).getLawyerName());
                            intent.putExtra("officeName", data.get(position - 1).getOfficeName());
                            intent.putExtra("headUrl", data.get(position - 1).getHeadURL());
                            intent.putExtra("sid", data.get(position - 1).getUserServiceId());
                            startActivity(intent);
                        }else {
                            startActivity(LoginActivity.class);
                        }



                    }
                });
            }

        }

        @Override
        public int getItemCount() {
            return data.size() + 1;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            private TextView dis_type, dis_context, dis_name, dis_office, dis_count, tv_name;
            private ImageView dis_praise;
            private CircleImageView dis_img;


            public ViewHolder(View itemView) {
                super(itemView);
                dis_type = (TextView) itemView.findViewById(R.id.tv_tag_now);
                dis_context = (TextView) itemView.findViewById(R.id.tv_content_index);
                dis_name = (TextView) itemView.findViewById(R.id.tv_law_name);
                tv_name = (TextView) itemView.findViewById(R.id.tv_name);
                dis_office = (TextView) itemView.findViewById(R.id.lawyer_office);
                dis_count = (TextView) itemView.findViewById(R.id.count_zan);
                dis_praise = (ImageView) itemView.findViewById(R.id.iv_like);
                dis_img = (CircleImageView) itemView.findViewById(R.id.iv_law);
            }
        }

        public class IndexViewHolder extends RecyclerView.ViewHolder {
            private RelativeLayout rl_quick_consulting, rl_look_lawyer;
            private Banner banner;
            private ImageView tv_more_solutions;
            private RecyclerView special_rlv;

            public IndexViewHolder(View itemView) {
                super(itemView);

                special_rlv = (RecyclerView) itemView.findViewById(R.id.special_rlv);

                banner = (Banner) itemView.findViewById(R.id.banner_index);

                rl_quick_consulting = (RelativeLayout) itemView.findViewById(R.id.rl_quick_consulting);

                rl_look_lawyer = (RelativeLayout) itemView.findViewById(R.id.rl_look_lawyer);

                tv_more_solutions = (ImageView) itemView.findViewById(R.id.tv_more_solutions);


            }
        }
    }


}
