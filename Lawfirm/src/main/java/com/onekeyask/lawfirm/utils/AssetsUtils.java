package com.onekeyask.lawfirm.utils;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 操作安装包中的“assets”目录下的文件
 *
 * @author 李玉江[QQ:1023694760]
 * @version 2013-11-2
 */
public class AssetsUtils {

    /**
     * read file content
     *
     * @param context   the context
     * @param assetPath the asset path
     * @return String string
     */
    public static String readText(Context context, String assetPath) {
        try {
            return ConvertUtils.toString(context.getAssets().open(assetPath));
        } catch (Exception e) {
            return "";
        }
    }

    public static String getGapTime(String createTime) {
        Date parse = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            parse = sdf.parse(createTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar d2 = Calendar.getInstance();
        Calendar d1 = Calendar.getInstance();
        d1.setTime(parse);
        int days = d2.get(Calendar.DAY_OF_YEAR) - d1.get(Calendar.DAY_OF_YEAR);
        if (days < 1) {
            int hour = d2.get(Calendar.HOUR_OF_DAY)
                    - d1.get(Calendar.HOUR_OF_DAY);
            if (hour < 1) {
                int munites = d2.get(Calendar.MINUTE) - d1.get(Calendar.MINUTE);
                if (munites == 0) {
                    return "刚刚";
                } else {
                    return munites + "分钟前";
                }
            } else if (hour <= 12) {
                return hour + "小时前";
            }
        }
        return new SimpleDateFormat("yyyy-MM-dd").format(parse);
    }

}
