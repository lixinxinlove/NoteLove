package com.lixinxinlove.notelove.data.api

import com.kotlin.base.data.protocol.BaseResp
import com.lixinxinlove.notelove.data.protocol.User
import io.reactivex.Observable
import retrofit2.http.*

/**
 * 同步笔记api
 */
interface NoteApi {
    /*
          用户登录
         */
    @FormUrlEncoded
    @Headers("contentType: application/x-www-form-urlencoded")
    @POST("user/login")
    // fun login(@Body req: LoginReq):Observable<BaseResp<User>>
    fun login(@Field("phone") phone: String, @Field("password") password: String): Observable<BaseResp<User>>


    @Headers("contentType: application/x-www-form-urlencoded")
    @GET("address/list")
    fun addressList(@Query(value = "userId") userId: Int): Observable<BaseResp<List<User>>>


    @FormUrlEncoded
    @Headers("contentType:application/json,application/x-www-form-urlencoded")
    @POST("note/save")
    fun save(@Field("notes") notes: String, @Field("userId") userId: Int): Observable<BaseResp<Int>>


    @FormUrlEncoded
    @Headers("contentType:application/json,application/x-www-form-urlencoded")
    @POST("note/update")
    fun update(@Field("notes") notes: String, @Field("userId") userId: Int): Observable<BaseResp<Int>>

}