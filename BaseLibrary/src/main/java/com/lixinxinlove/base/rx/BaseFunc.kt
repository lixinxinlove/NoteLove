package com.kotlin.base.rx

import com.kotlin.base.common.ResultCode
import com.kotlin.base.data.protocol.BaseResp
import io.reactivex.Observable
import io.reactivex.functions.Function


/*
    通用数据类型转换封装
 */
class BaseFunc<T>: Function<BaseResp<T>, Observable<T>> {

    override fun apply(t: BaseResp<T>): Observable<T> {
        if (t.code != ResultCode.SUCCESS){
            return Observable.error(BaseException(t.code,"error"))
        }
        return Observable.just(t.data)
    }

}
