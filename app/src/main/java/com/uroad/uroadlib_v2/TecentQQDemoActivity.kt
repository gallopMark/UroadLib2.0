package com.uroad.uroadlib_v2

import android.Manifest
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import com.tencent.connect.common.Constants
import com.tencent.tauth.Tencent
import com.tencent.tauth.UiError
import com.uroad.mobileqq.BaseUiListener
import com.uroad.mobileqq.TencentLib
import kotlinx.android.synthetic.main.activity_tentcentqq_demo.*
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager
import android.support.v4.content.ContextCompat
import android.provider.MediaStore
import android.content.ContentUris
import android.provider.DocumentsContract
import android.annotation.TargetApi
import android.net.Uri
import android.os.Build
import android.support.annotation.NonNull
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.database.Cursor
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.graphics.Rect
import java.io.FileDescriptor
import java.io.FileInputStream


/**
 *Created by MFB on 2018/5/29.
 */
class TecentQQDemoActivity : AppCompatActivity(), View.OnClickListener {
    private var isShareToQQ = false;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tentcentqq_demo)
        radioGroup.setOnCheckedChangeListener { _, checkId ->
            isShareToQQ = checkId == R.id.rb_friend
        }
        bt_shareText.setOnClickListener(this)
        bt_shareImage.setOnClickListener(this)
        bt_shareMusic.setOnClickListener(this)
        bt_shareApp.setOnClickListener(this)
        bt_shareQzone.setOnClickListener(this)
        bt_auth.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val appname = resources.getString(R.string.app_name)
        when (v.id) {
            R.id.bt_shareText -> {
                TencentLib.from(this).shareTextToQQ("分享消息到QQ的接口，可将新闻、图片、文字、应用等分享给QQ好友、群和讨论组。Tencent类的shareToQQ函数可直接调用，不用用户授权（使用手机QQ当前的登录态）。调用将打开分享的界面，用户选择好友、群或讨论组之后，点击确定即可完成分享，并进入与该好友进行对话的窗口。")
//                val param = TencentLib.Param().apply {
//                    TITLE = "测试标题"
//                    TARGET_URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527589268013&di=8d7634df4aa4c222653386f91e72d97e&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F140806%2F235020-140P60H10661.jpg"
//                    SUMMARY = "分享消息到QQ的接口，可将新闻、图片、文字、应用等分享给QQ好友、群和讨论组。Tencent类的shareToQQ函数可直接调用，不用用户授权（使用手机QQ当前的登录态）。调用将打开分享的界面，用户选择好友、群或讨论组之后，点击确定即可完成分享，并进入与该好友进行对话的窗口。"
//                    APPNAME = appname
//                    IMAGE_URL = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527589268013&di=8d7634df4aa4c222653386f91e72d97e&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F140806%2F235020-140P60H10661.jpg"
//                }
//                TencentLib.from(this).shareToQQ(param, uiListener)
            }
            R.id.bt_shareImage -> {
                select_photo()
            }
            R.id.bt_shareMusic -> {
                val musicUrl = "http://sc1.111ttt.cn/2018/1/03/13/396131229550.mp3"
                val param = TencentLib.Param().apply {
                    TITLE = "周笔畅-最美的期待"
                    SUMMARY = "音乐分享后，发送方和接收方在聊天窗口中点击消息气泡即可开始播放音乐。"
                    TARGET_URL = musicUrl
                    AUDIO_URL = musicUrl
                }
                TencentLib.from(this).shareMusicToQQ(param, uiListener)
            }
            R.id.bt_shareApp -> {
                val param = TencentLib.Param().apply {
                    TITLE = "测试应用标题"
                    SUMMARY = "这是我分享的第一个app"
                    APPNAME = appname
                }
                TencentLib.from(this).shareAppToQQ(param, uiListener)
            }
            R.id.bt_shareQzone -> {
                val param = TencentLib.Param().apply {
                    TITLE = "测试标题"
                    SUMMARY = "这是我分享QQ空间的第一条数据"
                    APPNAME = appname
                    TARGET_URL = "http://baidu.com"
                    IMAGE_URLS = ArrayList<String>().apply {
                        add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527589268013&di=8d7634df4aa4c222653386f91e72d97e&imgtype=0&src=http%3A%2F%2Fimg.taopic.com%2Fuploads%2Fallimg%2F140806%2F235020-140P60H10661.jpg")
                        add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527593193721&di=a49bca72522f726e5d78b2425dc6d648&imgtype=0&src=http%3A%2F%2Fpic.58pic.com%2F58pic%2F15%2F36%2F75%2F95458PICiSm_1024.jpg")
                        add("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1527593214157&di=3c22970fd30b409544d1ea2de6d447cf&imgtype=0&src=http%3A%2F%2Fpic15.photophoto.cn%2F20100402%2F0036036889148227_b.jpg")
                    }
                }
                TencentLib.from(this).shareToQzone(param, uiListener)
            }
            R.id.bt_auth -> {
                TencentLib.from(this).doLogin("get_user_info", uiListener)
            }
        }
    }

    /**
     * 从相册中获取图片
     */
    fun select_photo() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), 1)
        } else {
            openAlbum()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, @NonNull permissions: Array<String>, @NonNull grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openAlbum()
            }
        }
    }

    /**
     * 打开相册的方法
     */
    private fun openAlbum() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = "image/*"
        startActivityForResult(intent, 1)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == 1 && resultCode == RESULT_OK) {
            //判断手机系统版本号
            data?.let {
                val path = ImageUtils.getRealPathFromUri(this, data.data)
                displayImage(path)
            }
        } else if (requestCode == Constants.REQUEST_QQ_SHARE) {
            Tencent.handleResultData(data, uiListener)
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    private val uiListener = object : BaseUiListener() {
        override fun onComplete(o: Any?) {
            Toast.makeText(this@TecentQQDemoActivity, "发送完成", Toast.LENGTH_SHORT).show()
        }

        override fun onError(uiError: UiError) {
            Toast.makeText(this@TecentQQDemoActivity, uiError.errorMessage, Toast.LENGTH_SHORT).show()
        }

        override fun onCancel() {
            Toast.makeText(this@TecentQQDemoActivity, "发送取消", Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 根据图片路径显示图片的方法
     */
    private fun displayImage(imagePath: String?) {
        val appname = resources.getString(R.string.app_name)
        TencentLib.from(this).shareLocalImageToQQ(appname, imagePath, uiListener)
    }

    private object ImageUtils {
        /**
         * 将资源路径下的图片资源压缩后返回对应的Bitmap对象
         */
        fun decodeSampledBitmapFromResource(res: Resources, resId: Int, requiredWidth: Int, requiredHeight: Int): Bitmap {
            // 第一次解析将inJustDecodeBounds置为true用以获取图片大小
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeResource(res, resId, options)
            // 调用calculateInSampleSize计算inSampleSize的值
            options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight)
            // 将inJustDecodeBounds置为false,再次解析
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeResource(res, resId, options)
        }

        /**
         * 将磁盘上的FileDescriptor代表的图片文件压缩后返回对应的Bitmap对象
         */
        fun decodeSampledBitmapFromDisk(descriptor: FileDescriptor, requiredWidth: Int, requiredHeight: Int): Bitmap {
            // 第一次解析将inJustDecodeBounds置为true用以获取图片大小
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            val outPadding = Rect(0, 0, requiredWidth, requiredHeight)
            BitmapFactory.decodeFileDescriptor(descriptor, outPadding, options)
            // 调用calculateInSampleSize计算inSampleSize的值
            options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight)
            // 将inJustDecodeBounds置为false,再次解析
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeFileDescriptor(descriptor, outPadding,
                    options)
        }

        fun decodeSampledBitmap(`is`: FileInputStream, requiredWidth: Int, requiredHeight: Int): Bitmap {
            // 第一次解析将inJustDecodeBounds置为true用以获取图片大小
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeStream(`is`, null, options)
            // 调用calculateInSampleSize计算inSampleSize的值
            options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight)
            // 将inJustDecodeBounds置为false,再次解析
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeStream(`is`, null, options)
        }

        /**
         * 将磁盘上的imagePath路径下的图片文件压缩后返回对应的Bitmap对象
         */
        fun decodeSampledBitmapFromDisk(imagePath: String, requiredWidth: Int, requiredHeight: Int): Bitmap {
            // 第一次解析将inJustDecodeBounds置为true用以获取图片大小
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            BitmapFactory.decodeFile(imagePath, options)
            // 调用calculateInSampleSize计算inSampleSize的值
            options.inSampleSize = calculateInSampleSize(options, requiredWidth, requiredHeight)
            // 将inJustDecodeBounds置为false,再次解析
            options.inJustDecodeBounds = false
            return BitmapFactory.decodeFile(imagePath, options)
        }

        /**
         * 根据传入的BitmapFactory.Options对象和图片需要的宽和高计算出压缩比例
         *
         * @param options        BitmapFactory.Options对象
         * @param requiredWidth  需要的图片的宽度
         * @param requiredHeight 需要的图片的高度
         * @return
         */
        private fun calculateInSampleSize(options: BitmapFactory.Options, requiredWidth: Int, requiredHeight: Int): Int {
            // 根据BitmapFactory.Options对象获取原图片的高度和宽度
            val width = options.outWidth
            val height = options.outHeight
            var inSampleSize = 1
            if (width > requiredWidth || height > requiredHeight) {
                // 计算出实际的宽高比率
                val widthRatio = Math.round(width.toFloat() / requiredWidth.toFloat())
                val heightRatio = Math.round(height.toFloat() / requiredHeight.toFloat())
                // 选择宽高比率最小的作为inSampleSize的值,保证最后图片的宽和高大于等于需要的宽和高
                inSampleSize = if (widthRatio < heightRatio) widthRatio else heightRatio
            }
            return inSampleSize
        }

        /**
         * 根据Uri获取图片的绝对路径
         *
         * @param context 上下文对象
         * @param uri     图片的Uri
         * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
         */
        fun getRealPathFromUri(context: Context, uri: Uri): String? {
            val sdkVersion = Build.VERSION.SDK_INT
            return if (sdkVersion >= 19) { // api >= 19
                getRealPathFromUriAboveApi19(context, uri)
            } else { // api < 19
                getRealPathFromUriBelowAPI19(context, uri)
            }
        }

        /**
         * 适配api19以下(不包括api19),根据uri获取图片的绝对路径
         *
         * @param context 上下文对象
         * @param uri     图片的Uri
         * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
         */
        private fun getRealPathFromUriBelowAPI19(context: Context, uri: Uri): String? {
            return getDataColumn(context, uri, null, null)
        }

        /**
         * 适配api19及以上,根据uri获取图片的绝对路径
         *
         * @param context 上下文对象
         * @param uri     图片的Uri
         * @return 如果Uri对应的图片存在, 那么返回该图片的绝对路径, 否则返回null
         */
        @SuppressLint("NewApi")
        private fun getRealPathFromUriAboveApi19(context: Context, uri: Uri): String? {
            var filePath: String? = null
            if (DocumentsContract.isDocumentUri(context, uri)) {
                // 如果是document类型的 uri, 则通过document id来进行处理
                val documentId = DocumentsContract.getDocumentId(uri)
                if (isMediaDocument(uri)) { // MediaProvider
                    // 使用':'分割
                    val id = documentId.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[1]

                    val selection = MediaStore.Images.Media._ID + "=?"
                    val selectionArgs = arrayOf(id)
                    filePath = getDataColumn(context, MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection, selectionArgs)
                } else if (isDownloadsDocument(uri)) { // DownloadsProvider
                    val contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), java.lang.Long.valueOf(documentId))
                    filePath = getDataColumn(context, contentUri, null, null)
                }
            } else if ("content".equals(uri.scheme, ignoreCase = true)) {
                // 如果是 content 类型的 Uri
                filePath = getDataColumn(context, uri, null, null)
            } else if ("file" == uri.scheme) {
                // 如果是 file 类型的 Uri,直接获取图片对应的路径
                filePath = uri.path
            }
            return filePath
        }

        /**
         * 获取数据库表中的 _data 列，即返回Uri对应的文件路径
         * @return
         */
        private fun getDataColumn(context: Context, uri: Uri, selection: String?, selectionArgs: Array<String>?): String? {
            var path: String? = null
            val projection = arrayOf(MediaStore.Images.Media.DATA)
            var cursor: Cursor? = null
            try {
                cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null)
                if (cursor != null && cursor.moveToFirst()) {
                    val columnIndex = cursor.getColumnIndexOrThrow(projection[0])
                    path = cursor.getString(columnIndex)
                }
            } catch (e: Exception) {
                cursor?.close()
            }
            return path
        }

        /**
         * @param uri the Uri to check
         * @return Whether the Uri authority is MediaProvider
         */
        private fun isMediaDocument(uri: Uri): Boolean {
            return "com.android.providers.media.documents" == uri.authority
        }

        /**
         * @param uri the Uri to check
         * @return Whether the Uri authority is DownloadsProvider
         */
        private fun isDownloadsDocument(uri: Uri): Boolean {
            return "com.android.providers.downloads.documents" == uri.authority
        }

    }
}