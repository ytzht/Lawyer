package com.onekeyask.lawyer.utils;

import com.onekeyask.lawyer.global.L;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zht on 2017/04/17 15:32
 */

public class LogInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();

        L.d("network=====", String.format("Sending request %s on %s%n%s", request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();

//        L.d(String.format("Received response for %s in %.1fms%n%s", response.request().url(), (t2 - t1) / 1e6d, response.headers()));
        L.d("network=====", String.format("Received response for %s in %.1fms%n", response.request().url(), (t2 - t1) / 1e6d));

        return response.newBuilder().header("Cache-Control", "max-age=100").build();

    }

}
