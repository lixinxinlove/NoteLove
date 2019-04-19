package com.lixinxinlove.notelove.activity

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.baidu.speech.EventListener
import com.baidu.speech.EventManager
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import com.jaeger.library.StatusBarUtil
import com.lixinxinlove.base.activity.BaseActivity
import com.lixinxinlove.base.utils.GsonUtil
import com.lixinxinlove.notelove.R
import com.lixinxinlove.notelove.data.protocol.Note
import com.lixinxinlove.user.data.db.NoteDataBaseHelper
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_edit.*
import kotlinx.android.synthetic.main.content_edit.*
import org.json.JSONObject

/**
 * 编辑页面
 */
class EditActivity : BaseActivity(), View.OnClickListener {

    private val TAG = "EditActivity"

    private val REQUESTCODE = 100

    private var enableOffline = false // 测试离线命令词，需要改成true

    private var isEdit = false

    private var note = Note(System.currentTimeMillis() / 1000)

    private lateinit var eventListener: MyEventListener

    private lateinit var asr: EventManager


    override fun layoutId(): Int {
        return R.layout.activity_edit
    }

    override fun listener() {
        tvSave.setOnClickListener(this)
        toolbar.setNavigationOnClickListener { finish() }
        btnLongSpeech.setOnTouchListener(MyOnTouchListener())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        StatusBarUtil.setTranslucent(this)
        StatusBarUtil.setLightMode(this)
        super.onCreate(savedInstanceState)
        initData()


        initPermission()
        asr = EventManagerFactory.create(this, "asr")
        eventListener = MyEventListener()
        asr.registerListener(eventListener)

    }

    private fun initData() {
        isEdit = intent.getBooleanExtra("is_edit", false)
        if (!isEdit) {
            return
        }
        note = intent.getParcelableExtra("note")
        mTitle.setText(note.title)
        mInfo.setText(note.info)

    }

    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.tvSave -> {
                if (isEdit) {
                    note.title = mTitle.text.toString()
                    note.info = mInfo.text.toString()
                    note.editTime = System.currentTimeMillis()
                    note.theme = 0
                    if (note.status == 0) {
                        note.status = 0
                    } else {
                        note.status = 2
                    }
                    onUpdate(note)
                } else {
                    note.time = System.currentTimeMillis()
                    note.title = mTitle.text.toString()
                    note.info = mInfo.text.toString()
                    note.editTime = System.currentTimeMillis()
                    note.theme = 0
                    note.status = 0
                    onSave(note)
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun onUpdate(note: Note) {
        NoteDataBaseHelper.getInstance(mContext).appDataBase.noteDao().updateNote(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Int> {
                override fun onSuccess(t: Int) {
                    Log.e(TAG, "onSuccess")
                    setResult(Activity.RESULT_OK)
                    finish()
                }

                override fun onSubscribe(d: Disposable) {
                    Log.e(TAG, "onSubscribe")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError")
                }
            })
    }

    private fun onSave(note: Note) {
        NoteDataBaseHelper.getInstance(mContext).appDataBase.noteDao().insert(note)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<Long> {
                override fun onSuccess(t: Long) {
                    Log.e(TAG, "onSuccess")
                    setResult(Activity.RESULT_OK)
                    finish()
                }

                override fun onSubscribe(d: Disposable) {
                    Log.e(TAG, "onSubscribe")
                }

                override fun onError(e: Throwable) {
                    Log.e(TAG, "onError")
                }
            })
    }


    /**
     * 基于SDK集成2.2 发送开始事件
     * 点击开始按钮
     * 测试参数填在这里
     */
    private fun start() {
        val params = LinkedHashMap<String, Any>()
        var event: String? = null
        event = SpeechConstant.ASR_START // 替换成测试的event

        if (enableOffline) {
            params[SpeechConstant.DECODER] = 2
        }
        // 基于SDK集成2.1 设置识别参数
        params[SpeechConstant.ACCEPT_AUDIO_VOLUME] = false
        // params.put(SpeechConstant.NLU, "enable");
        // params.put(SpeechConstant.VAD_ENDPOINT_TIMEOUT, 0); // 长语音
        // params.put(SpeechConstant.IN_FILE, "res:///com/baidu/android/voicedemo/16k_test.pcm");
        // params.put(SpeechConstant.VAD, SpeechConstant.VAD_DNN);
        // params.put(SpeechConstant.PID, 1537); // 中文输入法模型，有逗号
        // 请先使用如‘在线识别’界面测试和生成识别参数。 params同ActivityRecog类中myRecognizer.start(params);
        // 复制此段可以自动检测错误
//        AutoCheck(applicationContext, object : Handler() {
//            override fun handleMessage(msg: Message) {
//                if (msg.what == 100) {
//                    val autoCheck = msg.obj as AutoCheck
//                    synchronized(autoCheck) {
//                        val message = autoCheck.obtainErrorMessage() // autoCheck.obtainAllMessage();
//                        txtLog.append(message + "\n")
//                        // Log.w("AutoCheckMessage", message);
//                    }// 可以用下面一行替代，在logcat中查看代码
//                }
//            }
//        }, enableOffline).checkAsr(params)
        var json: String? = null // 可以替换成自己的json
        json = JSONObject(params).toString() // 这里可以替换成你需要测试的json
        asr.send(event, json, null, 0, 0)

    }

    /**
     * 点击停止按钮
     * 基于SDK集成4.1 发送停止事件
     */
    private fun stop() {
        asr.send(SpeechConstant.ASR_STOP, null, null, 0, 0) //
    }

    inner class MyOnTouchListener : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when (event!!.actionMasked) {
                MotionEvent.ACTION_DOWN -> {
                    start()
                    btnLongSpeech.text = "开始输入"
                }
                MotionEvent.ACTION_MOVE -> {
                    btnLongSpeech.text = "输入中..."
                }
                MotionEvent.ACTION_UP -> {
                    stop()
                    btnLongSpeech.text = "长按说话"
                    tvHint.text=""
                }
            }
            return true
        }
    }

    inner class MyEventListener : EventListener {
        override fun onEvent(name: String?, params: String?, data: ByteArray?, offset: Int, length: Int) {

            Log.e("Event", name)

            var bestResult = ""
            var resultType = ""
            var finalResult = ""

            when (name) {
                SpeechConstant.CALLBACK_EVENT_ASR_READY -> {

                }
                SpeechConstant.CALLBACK_EVENT_ASR_SERIALNUMBER -> {

                }
                SpeechConstant.CALLBACK_EVENT_ASR_BEGIN -> {

                }
                SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL -> {
                    if (!params!!.isEmpty()) {
                        bestResult = GsonUtil.getValueByKey(params, "best_result")
                        resultType = GsonUtil.getValueByKey(params, "result_type")
                        tvHint.text=bestResult
                        if (resultType.equals("final_result")) {
                            finalResult = GsonUtil.getValueByKey(params!!, "best_result")
                            Log.e("bestResult", bestResult)
                            insertText(mInfo, finalResult)
                        }
                    }
                }
                SpeechConstant.CALLBACK_EVENT_ASR_END -> {

                }
                SpeechConstant.CALLBACK_EVENT_ASR_FINISH -> {

                }
                SpeechConstant.CALLBACK_EVENT_ASR_EXIT -> {
                    tvHint.text=""
                }
                else -> {

                }
            }
        }
    }

    private fun getEditTextCursorIndex(editText: EditText): Int {
        return editText.selectionStart
    }

    //向光标处插入文本
    private fun insertText(editText: EditText, string: String) {
        editText.text.insert(getEditTextCursorIndex(mInfo), string)
    }

    //光标左移
    private fun setEditTextCusorRollLeft(editText: EditText, rollLeft: Int) {
        editText.setSelection(getEditTextCursorIndex(editText) - rollLeft)
    }


    /**
     * android 6.0 以上需要动态申请权限
     */
    private fun initPermission() {
        val permissions = arrayOf(
            Manifest.permission.RECORD_AUDIO,
            Manifest.permission.ACCESS_NETWORK_STATE,
            Manifest.permission.INTERNET,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

        val toApplyList = ArrayList<String>()

        for (perm in permissions) {
            if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, perm)) {
                toApplyList.add(perm)
                // 进入到这里代表没有权限.
                Log.e("permissions", "进入到这里代表没有权限")
            }
        }
        val tmpList = arrayOfNulls<String>(toApplyList.size)
        if (!toApplyList.isEmpty()) {
            ActivityCompat.requestPermissions(this, toApplyList.toTypedArray(), REQUESTCODE)
        }
    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        // 此处为android 6.0以上动态授权的回调，用户自行实现。

        if (requestCode == REQUESTCODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //有权限
            } else {
                jumpSystemSetting()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        asr.unregisterListener(eventListener)
    }

    private fun jumpSystemSetting() {
        val packageURI = Uri.parse("package:$packageName")
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

}
