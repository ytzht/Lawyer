package com.onekeyask.lawyer.ui.fragment;

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
import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.entity.FreeAskCategory;
import com.onekeyask.lawyer.global.BaseFragment;
import com.onekeyask.lawyer.http.ProgressSubscriber;
import com.onekeyask.lawyer.http.SubscriberOnNextListener;
import com.onekeyask.lawyer.ui.act.search.SearchFindActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zht on 2017/04/10 15:17
 */

public class HomeFoundFragment extends BaseFragment implements OnTabSelectListener {

    private View view;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private MyPagerAdapter mAdapter;
    private List<FreeAskCategory.AllSpecialBean> beanList = new ArrayList<>();

    private ImageView search_main;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_found_fragment, container, false);
        initTag();
        search_main = (ImageView) view.findViewById(R.id.search_main);
        search_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(SearchFindActivity.class);
            }
        });
        return view;
    }

    private void initTag() {
        SubscriberOnNextListener<FreeAskCategory> listener = new SubscriberOnNextListener<FreeAskCategory>() {
            @Override
            public void onNext(FreeAskCategory category) {
                beanList = category.getAllSpecial();
                initView();
            }
            @Override
            public void onError(int code, String message) {
                showShort(message);
            }
        };


        retrofitUtil.getTags(new ProgressSubscriber<FreeAskCategory>(listener, getActivity(), false));
    }

    private void initView() {
        for (int i = 0; i < beanList.size(); i++) {
            mFragments.add(SimpleCardFragment.getInstance(beanList.get(i).getId()));
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
            return beanList.get(position).getName();
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
    }

}
