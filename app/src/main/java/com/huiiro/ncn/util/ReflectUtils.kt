package com.huiiro.ncn.util

import android.view.LayoutInflater
import java.lang.reflect.ParameterizedType

object ReflectUtils {

    /**
     * 反射创建 ViewBinging
     */
    fun <VB> newViewBinging(layoutInflater: LayoutInflater, clazz: Class<*>): VB {
        return try {
            val type: ParameterizedType = try {
                clazz.genericSuperclass as ParameterizedType
            } catch (e: ClassCastException) {
                clazz.superclass.genericSuperclass as ParameterizedType
            }

            val clazzVB = type.actualTypeArguments[0] as Class<VB>

            val inflateMethod = clazzVB.getMethod("inflate", LayoutInflater::class.java)
            inflateMethod.invoke(null, layoutInflater) as VB

        } catch (e: Exception) {
            e.printStackTrace()
            throw RuntimeException(e)
        }
    }
}