package com.onekeyask.lawyer.ui.act.lawyer.filter;

import android.text.TextUtils;

import com.onekeyask.lawyer.entity.SpecialBeanString;

/**
 * Created by zht on 2017/08/09 17:02
 */

public class FilterUrl {
    private volatile static FilterUrl filterUrl;

    private FilterUrl() {
    }

    public static FilterUrl instance() {
        if (filterUrl == null) {
            synchronized (FilterUrl.class) {
                if (filterUrl == null) {
                    filterUrl = new FilterUrl();
                }
            }
        }
        return filterUrl;
    }

    public String singleListPosition;
    public String doubleListLeft;
    public String doubleListRight;
    public String singleGridPosition;
    public String doubleGridTop;
    public String doubleGridMid;
    public String doubleGridBottom;

    public int position;
    public String positionTitle;

    @Override
    public String toString() {
        StringBuilder buffer = new StringBuilder();

        if (!TextUtils.isEmpty(singleGridPosition)) {
            for (int i = 0; i < SpecialBeanString.getSpecial().size(); i++) {

                if (SpecialBeanString.getSpecial().get(i).getName().equals(singleGridPosition)){
                    if (SpecialBeanString.getSpecial().get(i).getId() != 11) {
                        buffer.append("&special=" + SpecialBeanString.getSpecial().get(i).getId());
                    }
                }
            }
        }

        if (!TextUtils.isEmpty(doubleListLeft)) {
            buffer.append("&city="+doubleListLeft);
        }

        if (!TextUtils.isEmpty(doubleListRight)) {
            buffer.append("&district="+doubleListRight);
        }

        if (!TextUtils.isEmpty(singleListPosition)) {
            switch (singleListPosition){
                case "综合":
                    buffer.append("&sort=1");
                    break;
                case "评价等级":
                    buffer.append("&sort=2");
                    break;
                case "咨询人次":
                    buffer.append("&sort=3");
                    break;
                case "执业年限":
                    buffer.append("&sort=4");
                    break;
                case "价格由低到高":
                    buffer.append("&sort=5");
                    break;
                case "价格由高到低":
                    buffer.append("&sort=6");
                    break;

            }

        }

        if (!TextUtils.isEmpty(doubleGridTop)) {
            buffer.append("&serviceType=" + doubleGridTop);

        }

        if (!TextUtils.isEmpty(doubleGridMid)) {
            String[] split = doubleGridMid.split("-");
            if (split.length>1){
                buffer.append("&pricefrom="+split[0]);
                buffer.append(("&priceto="+split[1]));
            }else {
                buffer.append("&pricefrom=51&priceto=100000");
            }
        }

        if (!TextUtils.isEmpty(doubleGridBottom)) {
            String[] split = doubleGridBottom.split("-");
            if (split.length>1){
                buffer.append("&yearfrom="+split[0]);
                buffer.append(("&yearto="+split[1]).replace("年", ""));
            }else {
                buffer.append("&yearfrom=10&yearto=100");
            }
        }

        return buffer.toString();
    }

    public void clear() {
        filterUrl = null;
    }

}
