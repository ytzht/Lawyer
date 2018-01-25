package com.onekeyask.lawfirm.http;

/**
 * Created by zht on 2017/04/19 17:19
 */

public interface SubscriberOnNextListener<T> {
    void onNext(T t);
    void onError(int code, String message);

}
