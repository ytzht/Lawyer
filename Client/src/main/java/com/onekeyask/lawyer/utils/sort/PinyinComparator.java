package com.onekeyask.lawyer.utils.sort;

import android.text.TextUtils;

import com.onekeyask.lawyer.entity.SortBean;

import java.util.Comparator;

/**
 * Created by zht on 2018/01/26 14:52
 */

public class PinyinComparator implements Comparator<SortBean> {
    @Override
    public int compare(SortBean o1, SortBean o2) {
        if (TextUtils.equals(o1.letter, "#")) {
            return 1;
        } else if (TextUtils.equals(o2.letter, "#")) {
            return -1;
        } else {
            return o1.pinyin.compareTo(o2.pinyin);
        }
    }
}
