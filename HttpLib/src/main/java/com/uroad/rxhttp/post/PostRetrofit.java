package com.uroad.rxhttp.post;

import com.uroad.rxhttp.base.StringCallback;
import com.uroad.rxhttp.interceptor.Transformer;
import com.uroad.rxhttp.observer.StringObserver;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by MFB on 2018/6/4.
 */
public class PostRetrofit {
    private Retrofit.Builder mBuilder;

    private PostRetrofit() {
        mBuilder = new Retrofit.Builder()
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://www.xxx.com/");
    }

    public static PostRetrofit get() {
        return new PostRetrofit();
    }

    private Retrofit retrofit() {
        return mBuilder.build();
    }

    public static void post(String url, Map<String, Object> params, final StringCallback callback) {
        if (params == null) params = new HashMap<>();
        PostRetrofit.get()
                .retrofit()
                .create(PostApi.class)
                .post(url, params)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new StringObserver() {

                    @Override
                    public void doOnSubscribe(Disposable d) {
                        if (callback != null) callback.onSubscribe(d);
                    }

                    @Override
                    protected void onError(Throwable e, String errorMsg) {
                        if (callback != null) {
                            callback.onFailure(e, errorMsg);
                        }
                    }

                    @Override
                    protected void onSuccess(String data) {
                        if (callback != null) {
                            callback.onResponse(data);
                        }
                    }
                });
    }

    public static void doPostRaw(String url, String json, final StringCallback callback) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), json);
        PostRetrofit.get()
                .retrofit()
                .create(PostApi.class)
                .postWithJson(url, body)
                .compose(Transformer.<String>switchSchedulers())
                .subscribe(new StringObserver() {

                    @Override
                    public void doOnSubscribe(Disposable d) {
                        if (callback != null) callback.onSubscribe(d);
                    }

                    @Override
                    protected void onError(Throwable e, String errorMsg) {
                        if (callback != null) {
                            callback.onFailure(e, errorMsg);
                        }
                    }

                    @Override
                    protected void onSuccess(String data) {
                        if (callback != null) {
                            callback.onResponse(data);
                        }
                    }
                });
    }
}
