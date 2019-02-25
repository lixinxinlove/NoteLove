package com.kotlin.base.data.protocol

/*
    能用响应对象
    @status:响应状态码
    @message:响应文字消息
    @data:具体响应业务对象
 */
data class BaseResp<T>(var status:String, var code:Int, var data:T)
