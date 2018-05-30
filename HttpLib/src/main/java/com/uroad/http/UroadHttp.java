package com.uroad.http;

import android.os.Looper;

import com.uroad.http.cookie.SimpleCookieJar;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSocketFactory;

import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by MFB on 2018/5/28.
 */
public class UroadHttp {
    private static volatile UroadHttp mInstance;
    private OkHttpClient.Builder okHttpBuilder;
    private OkHttpClient mOkHttpClient;
    public int retryCount = 2;//默认重试2次

    private UroadHttp(Builder buider) {
        this.okHttpBuilder = buider.okHttpBuilder;
        this.mOkHttpClient = okHttpBuilder.build();
    }

    public static void init() {
        mInstance = new UroadHttp(new Builder());
    }

    public static void init(Builder builder) {
        if (builder == null) {
            builder = new Builder();
        }
        mInstance = new UroadHttp(builder);
    }

    public static UroadHttp getInstance() {
        if (mInstance == null) {
            synchronized (UroadHttp.class) {
                if (mInstance == null) {
                    init();
                }
            }
        }
        return mInstance;
    }

    public static class Builder {
        private OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();
        private Map<String, String> commonHeaders = new HashMap<>();  //全局header
        private long connectTimeout = 5000;
        private int retryCount = 2;//默认重试2次

        public Builder() {
            /*下面是OKHttp全局的默认配置*/
            okHttpBuilder.cookieJar(new SimpleCookieJar()) //cookie enabled
                    .connectTimeout(5000, TimeUnit.MILLISECONDS)
                    .readTimeout(5000, TimeUnit.MILLISECONDS)
                    .writeTimeout(5000, TimeUnit.MILLISECONDS);
        }

        /*下面是okhttp配置*/
        public Builder connectTimeout(long timeout) {
            if (timeout > 0) {
                okHttpBuilder.connectTimeout(timeout, TimeUnit.MILLISECONDS);
            }
            return this;
        }


        public Builder readTimeout(long timeout) {
            if (timeout > 0) {
                okHttpBuilder.readTimeout(timeout, TimeUnit.MILLISECONDS);
            }
            return this;
        }

        public Builder writeTimeout(long timeout) {
            if (timeout > 0) {
                okHttpBuilder.writeTimeout(timeout, TimeUnit.MILLISECONDS);
            }
            return this;
        }

        public Builder cookieJar(CookieJar cookieJar) {
            if (cookieJar != null) {
                okHttpBuilder.cookieJar(cookieJar);
            }
            return this;
        }

        public Builder sslSocketFactory(SSLSocketFactory sslSocketFactory) {
            if (sslSocketFactory != null) {
                okHttpBuilder.sslSocketFactory(sslSocketFactory);
            }
            return this;
        }

        public Builder hostnameVerifier(HostnameVerifier hostnameVerifier) {
            if (hostnameVerifier != null) {
                okHttpBuilder.hostnameVerifier(hostnameVerifier);
            }
            return this;
        }

        public Builder addInterceptor(Interceptor interceptor) {
            if (interceptor != null) {
                okHttpBuilder.addInterceptor(interceptor);
            }
            return this;
        }


        public Builder addNetworkInterceptor(Interceptor interceptor) {
            if (interceptor != null) {
                okHttpBuilder.addNetworkInterceptor(interceptor);
            }
            return this;
        }
    }
}
