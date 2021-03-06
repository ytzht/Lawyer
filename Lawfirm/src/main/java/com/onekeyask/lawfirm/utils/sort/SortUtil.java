package com.onekeyask.lawfirm.utils.sort;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.promeg.pinyinhelper.Pinyin;
import com.onekeyask.lawfirm.entity.SortBean;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by zht on 2018/01/26 14:50
 */

public class SortUtil {
    private List<SortBean> datas;
    private Map<String, int[]> letterMap;
    private int lastFirstVisibleItem = -1;

    public void onScrolled(RecyclerView recyclerView, View layout, TextView tvLetter) {
        if (recyclerView == null || layout == null || tvLetter == null || letterMap == null || datas == null) {
            return;
        }
        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager == null || !(manager instanceof LinearLayoutManager)) {
            return;
        }
        int firstVisibleItemPosition = ((LinearLayoutManager) manager).findFirstVisibleItemPosition();

        int[] value = letterMap.get(datas.get(firstVisibleItemPosition).letter);
        int nextSectionPosition = value != null ? value[1] : -1;
        if (firstVisibleItemPosition != lastFirstVisibleItem) {
            ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layout.getLayoutParams();
            params.topMargin = 0;
            layout.setLayoutParams(params);
            tvLetter.setText(datas.get(firstVisibleItemPosition).letter);
        }
        if (nextSectionPosition == firstVisibleItemPosition + 1) {
            View childView = recyclerView.getChildAt(0);
            if (childView != null) {
                int titleHeight = layout.getHeight();
                int bottom = childView.getBottom();
                ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) layout.getLayoutParams();
                if (bottom < titleHeight) {
                    float pushedDistance = bottom - titleHeight;
                    params.topMargin = (int) pushedDistance;
                    layout.setLayoutParams(params);
                } else {
                    if (params.topMargin != 0) {
                        params.topMargin = 0;
                        layout.setLayoutParams(params);
                    }
                }
            }
        }
        lastFirstVisibleItem = firstVisibleItemPosition;
    }

    public void onChange(int index, String c, RecyclerView recyclerView) {
        if (recyclerView == null || letterMap == null) {
            return;
        }
        RecyclerView.LayoutManager lm = recyclerView.getLayoutManager();
        if (lm == null || !(lm instanceof LinearLayoutManager)) {
            return;
        }
        LinearLayoutManager manager = (LinearLayoutManager) lm;
        if (index == 0) {
            manager.scrollToPositionWithOffset(0, 0);//置顶
            return;
        }
        int[] value = letterMap.get(c);
        if (value != null) {
            manager.scrollToPositionWithOffset(value[0], 0);
        }
    }

    public Map<String, int[]> sortDatas(List<SortBean> list) {
        letterMap = new HashMap<>();
        if (list == null || list.size() <= 0) {
            return letterMap;
        }
        int count = list.size();
        for (int i = 0; i < count; i++) {
            SortBean bean = list.get(i);
            String pinyin = Pinyin.toPinyin(bean.content, "");
            String letter = TextUtils.isEmpty(pinyin) ? "" : pinyin.substring(0, 1).toUpperCase();
            //正则表达式，判断首字母是否是英文字母/数字/其他
            if (letter.matches("[0-9]")) {
                letter = "☆";
            } else if (!letter.matches("[A-Z]")) {
                letter = "#";
            }
            bean.pinyin = pinyin;
            bean.letter = letter;
            bean.isLetter = false;
        }
        Collections.sort(list, new PinyinComparator());
        String key = null;
        int[] value = null;//is[0]:thisSectionPosition,is[1]:nextSectionPosition
        for (int i = 0; i < count; i++) {
            SortBean b = list.get(i);
            if (!TextUtils.equals(key, b.letter)) {
                key = b.letter;
                b.isLetter = true;
                if (value != null) {
                    value[1] = i;
                }
                value = new int[]{i, -1};
                letterMap.put(key, value);
            }
        }
        datas = list;
        return letterMap;
    }
}