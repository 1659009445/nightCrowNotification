package com.huiiro.ncn.domain.common

class Category {

    lateinit var id: String
    lateinit var title: String
    var icon: String? = null
    var desc: String? = null
    var children: List<Category>? = null

    companion object {
        fun create(id: String, title: String): Category {
            val r = Category()
            r.id = id
            r.title = title

            return r
        }
    }
}