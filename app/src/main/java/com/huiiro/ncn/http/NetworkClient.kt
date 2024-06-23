package com.huiiro.ncn.http

import com.huiiro.ncn.AppContext
import com.huiiro.ncn.util.JsonUtils
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object NetworkClient {

    fun provideOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()

        //cache 10mb
        val cache = Cache(AppContext.instance.cacheDir, 1024 * 1024 * 10)
        builder.cache(cache)

        builder.connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(10, TimeUnit.SECONDS)

        //token拦截器
        //builder.addInterceptor(TokenInterceptor())

        //日志拦截器
        //生产环境建议关闭
        //val loggingInterceptor = HttpLoggingInterceptor()
        //loggingInterceptor.level = HttpLoggingInterceptor.Level.BASIC
        //builder.addInterceptor(loggingInterceptor)

        return builder.build()
    }

    fun provideRetrofit(okHttpClient: OkHttpClient?): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient!!)
            .baseUrl(Api.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(JsonUtils.createGson()))
            .build()
    }
}