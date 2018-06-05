package com.uroad.uroadlib_v2;

import android.app.Application;

import com.uroad.rxhttp.RxHttpManager;

import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by admin(wlw) on 2018/5/16.
 */
public class CurrApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        RxHttpManager.get()
                .config()
                .setBaseUrl("http://115.238.84.147:8280/ZJAppServer/index.php/")
                .getGlobalRetrofitBuilder()
                .addConverterFactory(ScalarsConverterFactory.create());
    }

}
