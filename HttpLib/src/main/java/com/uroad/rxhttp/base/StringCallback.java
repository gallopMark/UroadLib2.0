package com.uroad.rxhttp.base;


import io.reactivex.disposables.Disposable;

/**
 * Created by MFB on 2018/6/4.
 */
public abstract class StringCallback {
    public void onSubscribe(Disposable disposable) {
    }

    public abstract void onResponse(String response);

    public abstract void onFailure(Throwable e, String errorMsg);

}
