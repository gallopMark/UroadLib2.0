//package com.uroad.uroadlib_v2
//
//import android.os.Bundle
//import android.support.v7.app.AppCompatActivity
//import android.text.Html
//import android.view.View
//import com.uroad.http.UroadHttp
//import com.uroad.http.callback.StringCallback
//import kotlinx.android.synthetic.main.activity_http_demo.*
//import com.uroad.http.request.HttpRequest
//import com.uroad.http.callback.FileCallBack
//import java.io.File
//
//
///**
// *Created by MFB on 2018/5/28.
// */
//class HttpDemoActivity : AppCompatActivity(), View.OnClickListener {
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_http_demo)
//        bt_get.setOnClickListener(this)
//        bt_post.setOnClickListener(this)
//        bt_upload.setOnClickListener(this)
//        bt_download.setOnClickListener(this)
//        bt_cancel.setOnClickListener(this)
//    }
//
//    override fun onClick(v: View) {
//        when (v.id) {
//            R.id.bt_get -> doGet()
//            R.id.bt_post -> doPost()
//            R.id.bt_upload -> upload()
//            R.id.bt_download -> download()
//            R.id.bt_cancel -> UroadHttp.cancelTag(this)
//        }
//    }
//
//    private fun doGet() {
//        val url = "http://www.baidu.com"
//        UroadHttp.get(this, url, object : StringCallback() {
//            override fun onFailure(e: Throwable) {
//                tvGetStr.text = e.message
//            }
//
//            override fun onSuccess(rusult: String) {
//                tvGetStr.text = Html.fromHtml(rusult)
//            }
//        })
//    }
//
//    private fun doPost() {
//        val json = "{\n" +
//                "\t\"BaseAppType\": \"android\",\n" +
//                "\t\"BaseAppVersion\": \"4.10.1\",\n" +
//                "\t\"SystemVersion\": \"7.1.1\",\n" +
//                "\t\"_sign_\": \"1D97B4164A6C961AA4B6DBAF4A44DFF9\",\n" +
//                "\t\"_token_\": \"062fe4f6dd5148a58d168520bea372f9--00\",\n" +
//                "\t\"_wid_\": \"404084422--0\",\n" +
//                "\t\"appIdentifier\": \"com.hs.yjseller--0\",\n" +
//                "\t\"shop_id\": \"125036171————00\"\n" +
//                "}"
//        val request = HttpRequest.Builder()
//                .addBodyParams(json)
//                .build()
//        UroadHttp.post(this, "http://api.vd.cn/info/getbonusnotice/", request, object : StringCallback() {
//            override fun onFailure(e: Throwable) {
//                tvGetStr.text = "post failed:" + e.message
//            }
//
//            override fun onSuccess(response: String) {
//                tvGetStr.text = "response:\n" + response
//
//            }
//
//            override fun onUpProgress(bytesWritten: Long, totalSize: Long) {
//                super.onUpProgress(bytesWritten, totalSize)
//                progressBar.progress = (bytesWritten * 100 / totalSize).toInt()
//            }
//
//            override fun onStart() {
//                super.onStart()
//                progressBar.progress = 0
//            }
//        })
//
//    }
//
//    private fun upload() {
//
//    }
//
//    private fun download() {
//        val url = "http://180.163.220.71/softdl.360tpcdn.com/auto/20180309/102615199_2f0a7c0426fa87ac8112aff10789ed08.exe"
//        UroadHttp.get(this, url, object : FileCallBack(this.externalCacheDir.path + "/ehttp", "ehttp.exe") {
//            override fun onStart() {
//                super.onStart()
//                tvGetStr.text = "开始下载..."
//                progressBar.progress = 0
//            }
//
//            override fun onDownProgress(bytesWritten: Long, totalSize: Long) {
//                tvGetStr.text = "开始下载" + bytesWritten * 100 / totalSize + "%"
//                progressBar.progress = (bytesWritten * 100 / totalSize).toInt()
//            }
//
//            override fun onFailure(e: Throwable) {
//                tvGetStr.text = "download failed:" + e.message
//            }
//
//            override fun onSuccess(file: File) {
//                tvGetStr.text = file.absolutePath
//            }
//        })
//    }
//}