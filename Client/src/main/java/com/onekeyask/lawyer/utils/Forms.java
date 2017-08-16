package com.onekeyask.lawyer.utils;

import org.nutz.lang.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zht on 2017/08/16 13:25
 */

public class Forms {
    public static final String PHONENUM = "^1[3|4|5|7|8][0-9]\\d{8}$";



    public static boolean disValid(String value, String reg) {
        return !valid(value, reg);
    }

    public static boolean valid(String value, String reg) {
        if (Strings.isBlank(value)) {
            return false;
        }
        Pattern p = Pattern.compile(reg);
        Matcher matcher = p.matcher(value);
        return matcher.matches();
    }

}
