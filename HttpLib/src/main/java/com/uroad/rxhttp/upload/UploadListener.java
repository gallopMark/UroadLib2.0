package com.uroad.rxhttp.upload;

import okhttp3.ResponseBody;

/**
 * Created by MFB on 2018/6/1.
 */
public abstract class UploadListener {
    public void onStart() {
    }

    public abstract void onProgress(long bytesWritten, long contentLength, int progress);

    //上传成功的回调
    public  abstract void onUpLoadSuccess(ResponseBody responseBody);

    //上传失败回调
    public  abstract void onUpLoadFail(Throwable e, String errorMsg);
}
