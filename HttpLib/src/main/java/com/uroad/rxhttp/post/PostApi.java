package com.uroad.rxhttp.post;

import java.util.Map;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Url;

/**
 * Created by MFB on 2018/6/4.
 */
public interface PostApi {
    @FormUrlEncoded
    @POST
    Observable<String> post(@Url String url, @FieldMap Map<String, Object> params);

    @Headers({"Content-Type: application/json", "Accept: application/json"})//需要添加头
    @POST
    Observable<String> postWithJson(@Url String url, @Body RequestBody body);
}
