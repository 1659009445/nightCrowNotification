package com.huiiro.ncn.http.repository

import com.huiiro.ncn.domain.CrowEntity
import com.huiiro.ncn.domain.CrowWarningEntity
import com.huiiro.ncn.domain.NoticeEntity
import com.huiiro.ncn.domain.TokenEntity
import com.huiiro.ncn.domain.WemixEntity
import com.huiiro.ncn.domain.common.Response
import com.huiiro.ncn.http.Api
import com.huiiro.ncn.http.service.ICrowService
import retrofit2.http.GET

/**
 * 网络数据仓库
 */
object CrowRepository {

    private val crowService: ICrowService by lazy {
        ICrowService.create()
    }

    suspend fun crow(): Response<CrowEntity> {
        return crowService.crow()
    }

    suspend fun token(): Response<List<TokenEntity>> {
        return crowService.token()
    }

    suspend fun wemix(): Response<WemixEntity> {
        return crowService.wemix()
    }

    suspend fun notice(): Response<NoticeEntity> {
        return crowService.notice()
    }

    suspend fun crowWarning(): Response<CrowWarningEntity> {
        return crowService.crowWarning()
    }
}