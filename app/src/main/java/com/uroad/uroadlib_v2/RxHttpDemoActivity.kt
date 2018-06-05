package com.uroad.uroadlib_v2

import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.uroad.rxhttp.RxHttpManager
import com.uroad.rxhttp.base.StringCallback
import com.uroad.rxhttp.bean.BaseData
import com.uroad.rxhttp.download.DownloadListener
import com.uroad.rxhttp.interceptor.Transformer
import com.uroad.rxhttp.observer.DataObserver
import com.uroad.rxhttp.upload.UploadListener
import com.uroad.uroadlib_v2.R.id.*
import com.uroad.uroadlib_v2.baselib.common.BaseActivity
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.activity_rxhttpdemo.*
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.http.*
import java.io.File


/**
 *Created by MFB on 2018/5/31.
 */
class RxHttpDemoActivity : BaseActivity() {
    private var isUploading = false
    private var isDownloading = false
    private var disposable1: Disposable? = null
    private var disposable2: Disposable? = null

    override fun setUp(savedInstanceState: Bundle?) {
        setBaseContentLayout(R.layout.activity_rxhttpdemo)
        toolbar.setNavigationIcon(R.drawable.ic_back_white_24dp)
        setToolTitle("RxHttp")
        bt_get.setOnClickListener { doGet() }
        bt_post.setOnClickListener { doPost() }
        bt_postJson.setOnClickListener { doPostRaw() }
        bt_upload.setOnClickListener {
            if (!isUploading) {
                doUpload()
                isUploading = true
                bt_upload.text = "取消上传"
            } else {
                disposable1?.dispose()
                isUploading = false
                bt_upload.text = "上传文件"
            }
        }
        bt_download.setOnClickListener {
            if (!isDownloading) {
                doDowload()
                isDownloading = true
                bt_download.text = "取消下载"
            } else {
                disposable2?.dispose()
                isDownloading = false
                bt_download.text = "下载文件"
            }
        }
    }

    private fun doGet() {
        val url = "https://tp-restapi.amap.com/gate"
        val params = HashMap<String, Any>().apply {
            put("sid", "90006")
            put("resType", "json")
            put("encode", "utf-8")
            put("reqData", "{ \"lat\":\"41.235318\", \"lon\":\"121.934779\", \"type \":1 }")
            put("serviceKey", "9892B629DE669327F9D561C24C1CD48A")
        }
        RxHttpManager.doGet(url, params, object : StringCallback() {
            override fun onFailure(e: Throwable?, errorMsg: String?) {
                tv_result.text = errorMsg
            }

            override fun onResponse(response: String?) {
                tv_result.text = response
            }

        })
    }

    private fun doPost() {
        val url = "http://gstgansuapi.u-road.com/gst_gansu_api/index.php/Mobile/getAllRoadsByDis"
        val params = HashMap<String, Any>().apply {
            put("latitude", "23.126408")
            put("longitude", "113.371453")
        }
        RxHttpManager.doPost(url, params, object : StringCallback() {
            override fun onResponse(response: String?) {
                tv_result.text = response
            }

            override fun onFailure(e: Throwable?, errorMsg: String?) {
                tv_result.text = errorMsg
            }

        })
    }

    private fun doPostRaw() {
        val url = "http://115.238.84.147:8280/ZJAppServer/index.php/EventDispatch/getmytodolist"
        val json = JsonObject().apply { addProperty("userid", "111111111111111111111111111111111") }
        RxHttpManager.doPostRaw(url, json.toString(), object : StringCallback() {
            override fun onResponse(response: String) {
                tv_result.text = response
            }

            override fun onFailure(e: Throwable, errorMsg: String) {
                tv_result.text = errorMsg
            }
        })
    }

    private fun doUpload() {
        val url = "http://ssl.u-road.com:82/HNJJAPIServer/index.php/API/uploadImage"
        //  val url = "http://upload.qiniu.com/"
        val filePath = Environment.getExternalStorageDirectory().absolutePath + "/zhejiangrescue_v1.4_20180601_debug.rar"
        val file = File(filePath)
        if (!file.exists()) Toast.makeText(this, "文件不存在", Toast.LENGTH_LONG).show()
        disposable1 = RxHttpManager.uploadFile(url, file, "filename", HashMap<String, String>().apply {
            put("name", "测试文字大搜大所大大所")
            put("date", "dasdjasiodfajodias")
        }, object : UploadListener() {
            override fun onProgress(bytesWritten: Long, contentLength: Long, progress: Int) {
                tv_result.text = "$progress"
                progressBar1.progress = bytesWritten.toInt()
                progressBar1.max = contentLength.toInt()
            }

            override fun onUpLoadSuccess(responseBody: ResponseBody) {
                tv_result.text = responseBody.string()
                isUploading = false
                bt_upload.text = "上传文件"
            }

            override fun onUpLoadFail(e: Throwable, errorMsg: String) {
                tv_result.text = errorMsg
                isUploading = false
                bt_upload.text = "上传文件"
            }
        })
    }

    private fun doDowload() {
        val url = "https://t.alipayobjects.com/L1/71/100/and/alipay_wap_main.apk"
        val destFilePath = getExternalFilesDir(null).absolutePath
        val destFileName = url.substring(url.lastIndexOf("/") + 1, url.length)
        disposable2 = RxHttpManager.downloadFile(url, destFilePath, destFileName, object : DownloadListener {
            override fun onStart() {
            }

            override fun onProgress(bytesRead: Long, contentLength: Long, progress: Float) {
                progressBar2.progress = bytesRead.toInt()
                progressBar2.max = contentLength.toInt()
            }

            override fun onFinish(filePath: String) {
                tv_result.text = filePath
                isDownloading = false
                bt_download.text = "下载文件"
            }

            override fun onError(e: Throwable?, errorMsg: String?) {
                tv_result.text = errorMsg
                isDownloading = false
                bt_download.text = "下载文件"
            }
        })
    }

    private interface ApiService {
        @Headers("Content-Type: application/json", "Accept: application/json")//需要添加头
        @POST("EventDispatch/getmytodolist")
        fun getmytodolist(@Body body: RequestBody): Observable<BaseData<List<EventMDL>>>

        @POST("EventDispatch/getmytodolist")
        fun geteventList(@Body body: JsonObject): Observable<BaseData<List<EventMDL>>>
    }

    private class EventMDL {
        var userid: String? = null
        var rescueid: String? = null
        var rescueno: String? = null
        var created: String? = null
        var sourcename: String? = null
        var rescuetypename: String? = null
        var address: String? = null
        var operatname: String? = null
        var dispatchmemberids: String? = null
        var dispatchmembernames: String? = null
        var dispatchvehicleid: String? = null
        var dispatchvehicleplate: String? = null
        var remark: String? = null
        var linkid: String? = null
        var dispatchstatus: String? = null
    }
}