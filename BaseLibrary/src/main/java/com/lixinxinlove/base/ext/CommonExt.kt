package com.kotlin.base.ext

import com.kotlin.base.data.protocol.BaseResp
import com.kotlin.base.rx.BaseFunc
import com.kotlin.base.rx.BaseFuncBoolean
import io.reactivex.Observable


//Kotlin通用扩展

/*
    扩展Observable执行
 */
//fun <T> Observable<T>.excute(subscriber: BaseSubscriber<T>) {
//    this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(subscriber)
//}


/*
    扩展数据转换
 */
fun <T> Observable<BaseResp<T>>.convert():Observable<T>{
    return this.flatMap(BaseFunc())
}

/*
    扩展Boolean类型数据转换
 */
fun <T> Observable<BaseResp<T>>.convertBoolean():Observable<Boolean>{
    return this.flatMap(BaseFuncBoolean())
}

/*
    扩展点击事件
 */
//fun View.onClick(listener:View.OnClickListener):View{
//    setOnClickListener(listener)
//    return this
//}

/*
    扩展点击事件，参数为方法
 */
//fun View.onClick(method:() -> Unit):View{
//    setOnClickListener { method() }
//    return this
//}

/*
    扩展Button可用性
// */
//fun Button.enable(et:EditText,method: () -> Boolean){
//    val btn = this
//    et.addTextChangedListener(object : DefaultTextWatcher(){
//        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            btn.isEnabled = method()
//        }
//    })
//}

///*
//    ImageView加载网络图片
// */
//fun ImageView.loadUrl(url: String) {
//    GlideUtils.loadUrlImage(context, url, this)
//}

/*
    多状态视图开始加载
// */
//fun MultiStateView.startLoading(){
//    viewState = MultiStateView.VIEW_STATE_LOADING
//    val loadingView = getView(MultiStateView.VIEW_STATE_LOADING)
//    val animBackground = loadingView!!.find<View>(R.id.loading_anim_view).background
//    (animBackground as AnimationDrawable).start()
//}

/*
    扩展视图可见性
 */
//fun View.setVisible(visible:Boolean){
//    this.visibility = if (visible) View.VISIBLE else View.GONE
//}
