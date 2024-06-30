package com.huiiro.ncn.domain

class TokenEntity {

    var symbol: String? = null

    var icon: String? = null

    var close: String? = null

    var closeDollar: Double? = null

    var priceStatus: String? = null

    var price24hPercent: String? = null

    var price24hChanged: Double? = null

    var prev: Double? = null

    var high: Double? = null

    var low: Double? = null

    var volume: Double? = null

    var tradedVolume: Double? = null

    var tradedVolumeDollar: Double? = null

    var updateTime: String? = null

    var charts: List<TokenChartEntity> = emptyList()
}