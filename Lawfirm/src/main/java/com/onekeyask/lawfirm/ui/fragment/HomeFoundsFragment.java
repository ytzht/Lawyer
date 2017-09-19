package com.onekeyask.lawfirm.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.entity.GetSpecialInfoList;
import com.onekeyask.lawfirm.global.Apis;
import com.onekeyask.lawfirm.global.BaseFragment;
import com.onekeyask.lawfirm.utils.UserService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zht on 2017/09/19 13:31
 */

public class HomeFoundsFragment extends BaseFragment implements OnTabSelectListener {
    private View view;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private List<GetSpecialInfoList.DataBean.SpecialListBean> beanList = new ArrayList<>();

    private ImageView search_main;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_found_fragment, container, false);
        initTag();

        return view;
    }

//    @Override
//    public void onResume() {
//        super.onResume();
//        initTag();
//    }

    private void initTag() {
//        SubscriberOnNextListener<FreeAskCategory> listener = new SubscriberOnNextListener<FreeAskCategory>() {
//            @Override
//            public void onNext(FreeAskCategory category) {
//                beanList = category.getAllSpecial();
//                initView();
//            }
//            @Override
//            public void onError(int code, String message) {
//                showShort(message);
//            }
//        };
//
//
//        retrofitUtil.getTags(new ProgressSubscriber<FreeAskCategory>(listener, getActivity(), false));

        OkGo.<String>get(Apis.GetSpecialInfoList)
                .params("lawyerId", UserService.service(getActivity()).getLawyerId())
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        GetSpecialInfoList list = (new Gson()).fromJson(response.body(), GetSpecialInfoList.class);
                        if (list.getCode() == 0){
                            beanList = list.getData().getSpecialList();
//                            tagAdapter = new ConTagAdapter();
//                            rlvcan.setAdapter(tagAdapter);
                            initView();
                        }


                    }
                });






        search_main = (ImageView) view.findViewById(R.id.search_main);
        search_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(SearchFindActivity.class, "type", "content");
            }
        });

    }

    private void initView() {
        for (int i = 0; i < beanList.size(); i++) {
            mFragments.add(SimpleCardFragment.getInstance(beanList.get(i).getSpecialId()));
        }

        ViewPager vp = (ViewPager) view.findViewById(R.id.discover_vp);
        mAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        vp.setAdapter(mAdapter);

        SlidingTabLayout tl_discover = (SlidingTabLayout) view.findViewById(R.id.tl_discover);
        tl_discover.setViewPager(vp);
        tl_discover.setOnTabSelectListener(this);

    }

    @Override
    public void onTabSelect(int position) {
//        showShort(" " + position);
    }

    @Override
    public void onTabReselect(int position) {
//        showShort("re " + position);
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return beanList.get(position).getSpecialName();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
