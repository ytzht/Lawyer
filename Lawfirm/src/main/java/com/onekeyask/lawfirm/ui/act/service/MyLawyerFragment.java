package com.onekeyask.lawfirm.ui.act.service;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onekeyask.lawfirm.R;
import com.onekeyask.lawfirm.global.BaseFragment;


/**
 * Created by zht on 2017/04/12 17:24
 */

public class MyLawyerFragment extends BaseFragment {

    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_index_fragment, container, false);

//        initView(view);

        return view;
    }


}
