package com.onekeyask.lawyer.app;

/**
 * Created by ytzht on 2018/03/21 下午1:25
 */

public class Demo {
    public static void main(String args[]){

        int n = 100;
        int s = 0;

        for (int i = 1; i <= n; i++) {
            int k = 0;
            for (int j = 1; j <= i; j++) {
                k += j;
            }
            s = s + k;
            System.out.println("n="+i+"时 s="+s);
        }

    }
}
