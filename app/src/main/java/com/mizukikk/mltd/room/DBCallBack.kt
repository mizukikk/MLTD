package com.mizukikk.mltd.room

interface DBCallBack<T> {
    fun success(result: T)
    fun fail()
}