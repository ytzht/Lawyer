package com.onekeyask.lawyer.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.onekeyask.lawyer.R;

/**
 * Created by zht on 2017/04/10 15:17
 */

public class HomeFoundFragment extends Fragment {

    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.home_found_fragment, container, false);
        return view;
    }
}
