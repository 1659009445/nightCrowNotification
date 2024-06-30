package com.huiiro.ncn.util

import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder

object JsonUtils {
    fun createGson(): Gson {
        val gsonBuilder = GsonBuilder()

        //驼峰转下划线
        //java服务端使用驼峰 kotlin客户端使用下划线
        //gsonBuilder.setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)

        return gsonBuilder.create()
    }

    /**
     * 对象转json
     */
    fun toJson(data: Any?): String {
        return createGson().toJson(data)
    }

    /**
     * json转对象
     */
    fun <T> fromJson(data: String?, clazz: Class<T>?): T {
        return createGson().fromJson(data, clazz)
    }
}