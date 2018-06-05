package com.uroad.rxhttp;

import android.app.Application;
import android.content.Context;

import com.google.gson.JsonObject;
import com.uroad.rxhttp.constant.SPKeys;
import com.uroad.rxhttp.download.DownloadListener;
import com.uroad.rxhttp.download.DownloadRetrofit;
import com.uroad.rxhttp.get.GetRetrofit;
import com.uroad.rxhttp.base.StringCallback;
import com.uroad.rxhttp.http.GlobalRxHttp;
import com.uroad.rxhttp.http.SingleRxHttp;
import com.uroad.rxhttp.post.PostRetrofit;
import com.uroad.rxhttp.upload.MultiUploadListener;
import com.uroad.rxhttp.upload.UploadListener;
import com.uroad.rxhttp.upload.UploadRetrofit;
import com.uroad.rxhttp.utils.SPUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import io.reactivex.disposables.Disposable;

/**
 * 网络请求
 */
public class RxHttpManager {

    private static volatile RxHttpManager instance;
    private static Application context;

    private static List<Disposable> disposables;

    public static RxHttpManager get() {
        //checkInitialize();
        if (instance == null) {
            synchronized (RxHttpManager.class) {
                if (instance == null) {
                    instance = new RxHttpManager();
                    disposables = new ArrayList<>();
                }
            }

        }
        return instance;
    }


    /**
     * 必须在全局Application先调用，获取context上下文，否则缓存无法使用
     *
     * @param app Application
     */
    public static void init(Application app) {
        context = app;
    }

    /**
     * 获取全局上下文
     */
    public static Context getContext() {
        checkInitialize();
        return context;
    }

    /**
     * 检测是否调用初始化方法
     */
    private static void checkInitialize() {
        if (context == null) {
            throw new ExceptionInInitializerError("请先在全局Application中调用 RxHttpManager.init() 初始化！");
        }
    }

    public GlobalRxHttp config() {
        return GlobalRxHttp.getInstance();
    }


    /**
     * 使用全局参数创建请求
     *
     * @param cls Class
     * @param <K> K
     * @return 返回
     */
    public static <K> K createApi(Class<K> cls) {
        return GlobalRxHttp.createGApi(cls);
    }

    /**
     * 获取单个请求配置实例
     *
     * @return SingleRxHttp
     */
    public static SingleRxHttp getSInstance() {
        return SingleRxHttp.getInstance();
    }

    /*get请求*/
    public static void doGet(String url, Map<String, Object> params, StringCallback callback) {
        GetRetrofit.get(url, params, callback);
    }

    /*post请求*/
    public static void doPost(String url, Map<String, Object> params, StringCallback callback) {
        PostRetrofit.post(url, params, callback);
    }

    /*post请求 参数为json格式(raw请求)*/
    public static void doPostRaw(String url, String json, StringCallback callback) {
        PostRetrofit.doPostRaw(url, json, callback);
    }

    /**
     * 下载文件
     *
     * @param url 文件链接 必须是完整链接
     * @return ResponseBody
     */
    public static Disposable downloadFile(String url, String destFilPath, String destFileName, DownloadListener downloadListener) {
        return DownloadRetrofit.downloadFile(url, destFilPath, destFileName, downloadListener);
    }

    public static Disposable uploadFile(String url, File file, String fileKey, HashMap<String, String> params) {
        return uploadFile(url, file, fileKey, params, null);
    }

    public static Disposable uploadFile(String url, File file, String fileKey, HashMap<String, String> params, UploadListener listener) {
        return UploadRetrofit.uploadFile(url, file, fileKey, params, listener);
    }

    public static Disposable uploadFileWithJson(String url, File file, String fileKey, JsonObject json) {
        return uploadFileWithJson(url, file, fileKey, json, null);
    }

    public static Disposable uploadFileWithJson(String url, File file, String fileKey, JsonObject json, UploadListener listener) {
        return UploadRetrofit.uploadFileWithJson(url, file, fileKey, json, listener);
    }

    /**
     * 上传多个文件
     *
     * @param url       地址
     * @param filePaths 文件路径
     * @return ResponseBody
     */
    public static Disposable uploadFiles(String url, List<String> filePaths, String fileKey, MultiUploadListener listener) {
        return uploadFiles(url, filePaths, fileKey, null, listener);
    }

    public static Disposable uploadFiles(String url, List<String> filePaths, String fileKey, HashMap<String, Object> params, MultiUploadListener listener) {
        return UploadRetrofit.uploadFiles(url, filePaths, fileKey, params, listener);
    }

    /**
     * 获取Cookie
     *
     * @return HashSet
     */
    public static HashSet<String> getCookie() {
        HashSet<String> preferences = (HashSet<String>) SPUtils.get(SPKeys.COOKIE, new HashSet<String>());
        return preferences;
    }

    /**
     * 获取disposable 在onDestroy方法中取消订阅disposable.dispose()
     *
     * @param disposable disposable
     */
    public static void addDisposable(Disposable disposable) {
        if (disposables != null) {
            disposables.add(disposable);
        }
    }

    /**
     * 取消所有请求
     */
    public static void cancelAllRequest() {
        if (disposables != null) {
            for (Disposable disposable : disposables) {
                disposable.dispose();
            }
            disposables.clear();
        }
    }

    /**
     * 取消单个请求
     */
    public static void cancelSingleRequest(Disposable disposable) {
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}
