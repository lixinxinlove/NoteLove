package com.lixinxinlove.notelove.activity


import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.baidu.speech.EventListener
import com.baidu.speech.EventManager
import com.baidu.speech.EventManagerFactory
import com.baidu.speech.asr.SpeechConstant
import com.lixinxinlove.base.activity.BaseActivity
import com.lixinxinlove.base.utils.GsonUtil
import com.lixinxinlove.notelove.R
import kotlinx.android.synthetic.main.activity_baidu_ai.*
import org.json.JSONObject
import java.util.*

/**
 * 百度语音识别
 */
class BaiduAiActivity : BaseActivity() {

    private val REQUESTCODE = 100

    private var enableOffline = false // 测试离线命令词，需要改成true


    private lateinit var eventListener: MyEventListener

    private lateinit var asr: EventManager

    override fun layoutId(): Int {
        return R.layout.activity_baidu_ai
    }

    override fun listener() {
        btnSpeech.setOnTouchListener(MyOnTouchListener())
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initPermission()
        asr = EventManagerFactory.create(this, "asr")
        eventListener = MyEventListener()
        asr.registerListener(eventListener)

    }


    inner class MyOnTouchListener : View.OnTouchListener {
        override fun onTouch(v: View?, event: MotionEvent?): Boolean {
            when (event!!.actionMasked) {

                MotionEvent.ACTION_DOWN -> {
                    start()
                    Toast.makeText(mContext, "按下", Toast.LENGTH_SHORT).show()
                }

                MotionEvent.ACTION_UP -> {
                    stop()
                    Toast.makeText(mContext, "抬起", Toast.LENGTH_SHORT).show()
                }
            }
            return true
        }
    }


    inner class MyEventListener : EventListener {
        override fun onEvent(name: String?, params: String?, data: ByteArray?, offset: Int, length: Int) {
//            var logTxt = "name: $name"
//            var result = ""
//            if (params != null && !params.isEmpty()) {
//                logTxt += " ;params :$params"
//                result = params
//            }


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
                        tvResult.text = bestResult

                        if (resultType.equals("final_result")) {
                            finalResult = GsonUtil.getValueByKey(params!!, "best_result")
                            Log.e("bestResult", bestResult)
                            tvResult.text = finalResult
                        }
                    }
                }
                SpeechConstant.CALLBACK_EVENT_ASR_END -> {

                }
                SpeechConstant.CALLBACK_EVENT_ASR_FINISH -> {

                }
                SpeechConstant.CALLBACK_EVENT_ASR_EXIT -> {

                }
                else -> {

                }
            }

//            if (params != null && !params.isEmpty()) {
//                Log.e("Event", params)
//            }
//            if (name == SpeechConstant.CALLBACK_EVENT_ASR_PARTIAL) {
//                if (params != null && params.contains("\"nlu_result\"")) {
//                    if (length > 0 && data!!.isNotEmpty()) {
//                        logTxt += ", 语义解析结果：" + String(data!!, offset, length)
//                    }
//                }
//            } else if (data != null) {
//                logTxt += " ;data length=" + data.size
//            }
            // Log.e("onEvent", logTxt)

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        asr.unregisterListener(eventListener)
    }


    /**
     * 基于SDK集成2.2 发送开始事件
     * 点击开始按钮
     * 测试参数填在这里
     */
    fun start() {
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


    private fun jumpSystemSetting() {
        val packageURI = Uri.parse("package:$packageName")
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, packageURI)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }


}
