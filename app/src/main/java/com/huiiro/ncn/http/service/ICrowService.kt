package com.huiiro.ncn.http.service

import com.huiiro.ncn.domain.CrowEntity
import com.huiiro.ncn.domain.NoticeEntity
import com.huiiro.ncn.domain.TokenEntity
import com.huiiro.ncn.domain.WemixEntity
import com.huiiro.ncn.http.Api
import com.huiiro.ncn.http.NetworkClient
import com.huiiro.ncn.domain.common.Response
import retrofit2.http.GET

interface ICrowService {

    @GET(Api.CROW_INFO)
    suspend fun crow(): Response<CrowEntity>

    @GET(Api.TOKEN_INFO)
    suspend fun token(): Response<List<TokenEntity>>

    @GET(Api.WEMIX_INFO)
    suspend fun wemix(): Response<WemixEntity>

    @GET(Api.NOTICE_INFO)
    suspend fun notice(): Response<NoticeEntity>

    companion object {
        fun create(): ICrowService {
            return NetworkClient
                .provideRetrofit(NetworkClient.provideOkHttpClient())
                .create(ICrowService::class.java)
        }
    }
}