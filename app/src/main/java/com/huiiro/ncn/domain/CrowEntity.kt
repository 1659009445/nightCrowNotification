package com.huiiro.ncn.domain

class CrowEntity {

    var status: Int? = 0

    var cost: Int? = 0

    var ttl: Int? = 0

    var message: String? = null

    var symbol: String? = null

    var updateTime: String? = null

    var dayBeginTime: String? = null

    var dayEndTime: String? = null

    var dayAveragePrice: Double? = null

    var dayMaxPrice: Double? = null

    var dayMinPrice: Double? = null

    var dayAccuratePrice: Double? = null

    var hourAveragePrice: Double? = null

    var hourMaxPrice: Double? = null

    var hourMinPrice: Double? = null

    var dayHourDetails: List<CrowHourDetailEntity>? = emptyList()

    var dayTradeDetails: List<CrowTradeDetailEntity>? = emptyList()

    var changeHistory: List<CrowPriceChangeHistory>? = emptyList()

    var warningHistory: List<CrowWarningHistoryEntity>? = emptyList()
}