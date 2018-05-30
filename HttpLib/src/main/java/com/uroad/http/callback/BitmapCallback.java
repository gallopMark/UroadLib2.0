package com.uroad.http.callback;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import okhttp3.ResponseBody;

public abstract class BitmapCallback extends HttpCallback<Bitmap> {
    @Override
    public Bitmap parseResponse(ResponseBody body) throws Exception{
        return BitmapFactory.decodeStream(body.byteStream());
    }

}
