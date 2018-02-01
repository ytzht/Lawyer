package com.onekeyask.lawfirm.ui.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.global.BaseFragment;
import com.onekeyask.lawfirm.ui.act.found.HotFoundFragment;
import com.onekeyask.lawfirm.ui.act.found.LatestFoundFragment;
import com.onekeyask.lawfirm.ui.act.found.ResolvedFoundFragment;


/**
 * Created by zht on 2017/04/10 15:17
 */

public class HomeFoundFragment extends BaseFragment {

    private View view;
    private final String[] mTitles = {"已解决", "最新", "热门"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_founds_fragment, container, false);
        initView();

        return view;

    }

    private void initView() {

        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.sliding_tabs);
        pagerAdapter = new HomeFoundAdapter(getChildFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        resolvedFoundFragment = new ResolvedFoundFragment();
        latestFoundFragment = new LatestFoundFragment();
        hotFoundFragment = new HotFoundFragment();
        viewPager.setCurrentItem(1);

    }

    private HomeFoundAdapter pagerAdapter;
    private ResolvedFoundFragment resolvedFoundFragment;
    private LatestFoundFragment latestFoundFragment;
    private HotFoundFragment hotFoundFragment;

    private class HomeFoundAdapter extends FragmentPagerAdapter {

        public HomeFoundAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return resolvedFoundFragment;
                case 1:
                    return latestFoundFragment;
                case 2:
                    return hotFoundFragment;
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
