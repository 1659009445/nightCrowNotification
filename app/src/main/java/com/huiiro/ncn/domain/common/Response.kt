package com.huiiro.ncn.domain.common


import java.io.Serializable

class Response<T> : Serializable {

    private var code: Int? = null

    private var message: String? = null

    private var data: T? = null

    fun setCode(code: Int) {
        this.code = code
    }

    fun getCode(): Int? {
        return code
    }

    fun setMessage(message: String) {
        this.message = message
    }

    fun getMessage(): String? {
        return message
    }

    fun setData(data: T) {
        this.data = data
    }

    fun getData(): T? {
        return data
    }

}