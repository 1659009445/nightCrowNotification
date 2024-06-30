package com.huiiro.ncn.http

object Api {

    const val BASE_URL = "https://api.huiiro.com/"
    //const val BASE_URL = "http://localhost:8080/"

    private const val VERSION = "v2"

    /**
     * check for update
     */
    const val CHECK_UPDATE = "crow/api/$VERSION/update"

    /**
     * crow warning
     */
    const val CROW_WARNING = "crow/api/$VERSION/warning"

    /**
     * crow info
     */
    const val CROW_INFO = "crow/api/$VERSION/crow"

    /**
     * tokens info
     */
    const val TOKEN_INFO = "crow/api/$VERSION/token"

    /**
     * wemix info
     */
    const val WEMIX_INFO = "crow/api/$VERSION/wemix"

    /**
     * notice info
     */
    const val NOTICE_INFO = "crow/api/$VERSION/notice"

    /**
     * notice detail
     * <p>param  id integer </p>
     */
    const val NOTICE_DETAIL = "crow/api/$VERSION/notice/"
}