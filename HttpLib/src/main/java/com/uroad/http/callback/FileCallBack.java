package com.uroad.http.callback;

import android.os.Handler;
import android.os.Looper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import okhttp3.ResponseBody;

/*下载文件返回*/
public abstract class FileCallBack extends HttpCallback<File> {
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir;
    /**
     * 目标文件存储的文件名
     */
    private String destFileName;
    private Handler handler;

    public FileCallBack(String destFileDir, String destFileName) {
        this.destFileDir = destFileDir;
        this.destFileName = destFileName;
        handler = new Handler(Looper.getMainLooper());
    }

    public abstract void onDownProgress(long bytesWritten, long totalSize);

    @Override
    public File parseResponse(ResponseBody body) throws Exception {
        return saveFile(body);
    }

    private File saveFile(ResponseBody body) throws Exception {
        InputStream is = null;
        byte[] buf = new byte[2048];
        int len;
        FileOutputStream fos = null;
        try {
            is = body.byteStream();
            final long total = body.contentLength();
            long sum = 0;
            File dir = new File(destFileDir);
            if (!dir.exists()) dir.mkdirs();
            File file = new File(dir, destFileName);
            fos = new FileOutputStream(file);
            while ((len = is.read(buf)) != -1) {
                sum += len;
                fos.write(buf, 0, len);
                final long finalSum = sum;
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        onDownProgress(finalSum, total);
                    }
                });
            }
            fos.flush();
            return file;
        } finally {
            try {
                if (is != null) is.close();
            } catch (IOException e) {
            }
            try {
                if (fos != null) fos.close();
            } catch (IOException e) {
            }
        }
    }

}
