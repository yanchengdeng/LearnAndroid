package com.dyc.learnandroid.clazz

/**
 *@Author : yancheng
 *@Date : 2021/1/4
 *@Time : 13:58
 *@Describe ：
 **/
interface IProduction<out T> {
    fun produce() : T
}