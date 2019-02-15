package com.kotlin.base.rx

import com.kotlin.base.common.ResultCode
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import io.reactivex.functions.Function


/*
    Boolean类型转换封装
 */
class BaseFuncBoolean<T>: Function<BaseResp<T>, Observable<Boolean>> {
    override fun apply(t: BaseResp<T>): Observable<Boolean> {
        if (t.code != ResultCode.SUCCESS){

            return Observable.error(BaseException(t.code, "eee"))
        }
        return Observable.just(true)
    }

//    override fun call(t: BaseResp<T>): Observable<Boolean> {
//        if (t.status != ResultCode.SUCCESS){
//
//            return Observable.error(BaseException(t.status, t.message))
//        }
//
//        return Observable.just(true)
//    }
}
