package com.dyc.learnandroid.clazz

/**
 *@Author : yancheng
 *@Date : 2021/1/4
 *@Time : 13:59
 *@Describe ：
 **/
interface IConsumer<in T> {
    fun consumer(item : T)
}