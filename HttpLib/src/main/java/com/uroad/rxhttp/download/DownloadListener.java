package com.uroad.rxhttp.download;

/**
 * Created by MFB on 2018/6/4.
 */
public interface DownloadListener {
    void onStart();

    void onProgress(long bytesRead, long contentLength, float progress);

    void onFinish(String filePath);

    void onError(Throwable e, String errorMsg);
}
