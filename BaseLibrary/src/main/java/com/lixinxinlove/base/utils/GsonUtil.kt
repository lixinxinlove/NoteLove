package com.lixinxinlove.base.utils

import com.google.gson.Gson
import com.google.gson.JsonParser

object GsonUtil {

    var GSON = Gson()


    fun getValueByKey(json: String, key: String): String {
        val obj = JsonParser().parse(json).asJsonObject
        return obj.get(key).asString
    }


}