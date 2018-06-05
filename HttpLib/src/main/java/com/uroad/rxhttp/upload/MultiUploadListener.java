package com.uroad.rxhttp.upload;

import java.io.File;

import okhttp3.ResponseBody;

/**
 * Created by MFB on 2018/6/4.
 */
public abstract class MultiUploadListener {
    public void onStart() {
    }

    public abstract void onProgress(File file, long bytesWritten, long contentLength, int progress);

    //上传成功的回调
    public abstract void onUpLoadSuccess(ResponseBody responseBody);

    //上传失败回调
    public abstract void onUpLoadFail(Throwable e, String errorMsg);
}
