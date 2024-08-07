package com.huiiro.ncn.http.service

import com.huiiro.ncn.domain.CrowEntity
import com.huiiro.ncn.domain.CrowUpdateEntity
import com.huiiro.ncn.domain.CrowWarningEntity
import com.huiiro.ncn.domain.CrowWarningHistoryEntity
import com.huiiro.ncn.domain.NoticeDetailEntity
import com.huiiro.ncn.domain.NoticeEntity
import com.huiiro.ncn.domain.TokenEntity
import com.huiiro.ncn.domain.WemixEntity
import com.huiiro.ncn.http.Api
import com.huiiro.ncn.http.NetworkClient
import com.huiiro.ncn.domain.common.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ICrowService {

    @GET(Api.CHECK_UPDATE)
    suspend fun checkUpdate(): Response<CrowUpdateEntity>

    @GET(Api.CROW_WARNING)
    suspend fun crowWarning(): Response<CrowWarningEntity>

    @GET(Api.CROW_WARNING_HISTORY)
    suspend fun crowWarningHistory(): Response<CrowWarningHistoryEntity>

    @GET(Api.CROW_INFO)
    suspend fun crow(): Response<CrowEntity>

    @GET(Api.TOKEN_INFO)
    suspend fun token(): Response<List<TokenEntity>>

    @GET(Api.WEMIX_INFO)
    suspend fun wemix(): Response<WemixEntity>

    @GET(Api.NOTICE_INFO)
    suspend fun notice(): Response<List<NoticeEntity>>

    @GET("${Api.NOTICE_DETAIL}{id}")
    suspend fun noticeDetail(@Path("id") id: Int): Response<NoticeDetailEntity>

    companion object {
        fun create(): ICrowService {
            return NetworkClient
                .provideRetrofit(NetworkClient.provideOkHttpClient())
                .create(ICrowService::class.java)
        }
    }
}