package com.lixinxinlove.notelove.activity

import android.app.DatePickerDialog
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.Outline
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.lixinxinlove.base.activity.BaseActivity
import com.lixinxinlove.notelove.R
import com.lixinxinlove.notelove.app.NoteApp
import com.lixinxinlove.user.data.db.NoteDataBaseHelper
import com.yalantis.ucrop.UCrop
import com.yalantis.ucrop.UCropActivity
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_myself.*
import java.io.File

class MyselfActivity : BaseActivity() {

    private lateinit var dpd: DatePickerDialog

    override fun layoutId(): Int {
        return R.layout.activity_myself
    }

    override fun listener() {
        toolbar.setNavigationOnClickListener { finish() }
        mBtnLogout.setOnClickListener {
            //logoutAlertDialog()

            showTime()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //  mTvUserName.text = NoteApp.user!!.name

        dpd = DatePickerDialog(this)
        dpd.setOnDateSetListener { view, year, month, dayOfMonth ->
            Log.e("lee", "$year,$month,$dayOfMonth")
        }


        val viewOutlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                view.clipToOutline = true
                outline.setOval(0, 0, view.width, view.height)
            }
        }
        headView.outlineProvider = viewOutlineProvider


    }


    private fun showTime() {


        dpd.show()

    }


    private fun logoutAlertDialog() {
        AlertDialog.Builder(mContext)
            .setTitle("提示").setMessage("亲！要退出吗？")
            .setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
            .setPositiveButton("确定") { dialog, which -> logout() }
            .create()
            .show()

    }

    private fun logout() {
        NoteDataBaseHelper.getInstance(mContext).appDataBase.userDao().deleteUser(NoteApp.user!!).subscribeOn(
            Schedulers.io()
        )
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Int> {
                override fun onSuccess(t: Int) {
                    NoteApp.isLogin = false
                    NoteApp.user = null
                    finish()
                }

                override fun onSubscribe(d: Disposable) {

                }

                override fun onError(e: Throwable) {
                    Toast.makeText(mContext, "退出登录失败", Toast.LENGTH_SHORT).show()
                }
            })
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        //裁剪图片回调
        if (resultCode == RESULT_OK && requestCode == UCrop.REQUEST_CROP) {
            val resultUri = UCrop.getOutput(data!!)
            val cropImagePath = getRealFilePathFromUri(applicationContext, resultUri)
            val file = File(cropImagePath)
            // GlideUtils.loadHead(this, cropImagePath, headImage)
            //  EventApp.api.postFile(postFileCallback, Config.uploadUrl, file)
        } else if (resultCode == UCrop.RESULT_ERROR) {
            val cropError = UCrop.getError(data!!)
            // ToastUtils.showToast(mContext, "图片加载失败")
        }

    }


    //裁剪图片
    private fun gotoClipImage(uri: Uri) {

        val options = UCrop.Options()
        options.setActiveWidgetColor(Color.parseColor("#1996f9"))
        //options.setCircleDimmedLayer(true);
        // options.setCropFrameColor(Color.parseColor("#1996f9"));
        options.setLogoColor(Color.parseColor("#1996f9"))
        options.setToolbarColor(Color.parseColor("#ffffff"))
        options.setStatusBarColor(Color.parseColor("#000000"))
        options.setToolbarWidgetColor(Color.parseColor("#000000"))
        options.setFreeStyleCropEnabled(true)
        options.setAllowedGestures(UCropActivity.SCALE, UCropActivity.SCALE, UCropActivity.SCALE)

        val destinationUri = Uri.fromFile(File(cacheDir, "lee.png"))
        UCrop.of(uri, destinationUri)
            .withAspectRatio(1f, 1f)
            // .withMaxResultSize(370, 400)
            .withMaxResultSize(200, 200)
            .withOptions(options)
            .start(this)
    }


    private fun getRealFilePathFromUri(context: Context, uri: Uri?): String? {
        if (null == uri)
            return null
        val scheme = uri.scheme
        var data: String? = null
        if (scheme == null)
            data = uri.path
        else if (ContentResolver.SCHEME_FILE == scheme) {
            data = uri.path
        } else if (ContentResolver.SCHEME_CONTENT == scheme) {
            val cursor = context.contentResolver.query(
                uri,
                arrayOf(MediaStore.Images.ImageColumns.DATA), null, null, null
            )
            if (null != cursor) {
                if (cursor.moveToFirst()) {
                    val index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                    if (index > -1) {
                        data = cursor.getString(index)
                    }
                }
                cursor.close()
            }
        }
        return data
    }


}
