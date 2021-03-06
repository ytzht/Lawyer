package com.onekeyask.lawyer.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onekeyask.lawyer.R;
import com.onekeyask.lawyer.ui.act.service.CompleteServiceFragment;
import com.onekeyask.lawyer.ui.act.service.MyLawyerFragment;
import com.onekeyask.lawyer.ui.act.service.NowServerFragment;

/**
 * Created by zht on 2017/04/10 15:17
 */

public class HomeServiceFragment extends Fragment {

    private View view;
    private final String[] mTitles = {"当前服务", "我的律师", "已完成服务"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_service_fragment, container, false);

        initView(view);
        return view;
    }

    private void initView(View view) {

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);

        pagerAdapter = new HomeServiceAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        viewPager.setOffscreenPageLimit(2);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        nowServerFragment = new NowServerFragment();
        myLawyerFragment = new MyLawyerFragment();
        completeServiceFragment = new CompleteServiceFragment();
    }
    private HomeServiceAdapter pagerAdapter;
    private NowServerFragment nowServerFragment;
    private MyLawyerFragment myLawyerFragment;
    private CompleteServiceFragment completeServiceFragment;

    private class HomeServiceAdapter extends FragmentPagerAdapter {

        public HomeServiceAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return nowServerFragment;
                case 1:
                    return myLawyerFragment;
                case 2:
                    return completeServiceFragment;
                default:
                    return null;
            }

        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
