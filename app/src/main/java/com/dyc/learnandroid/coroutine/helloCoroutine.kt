package com.dyc.learnandroid

import kotlinx.coroutines.*

/**
 *@Author : yancheng
 *@Date : 2020/12/31
 *@Time : 16:53
 *@Describe ：
 **/

fun main() = runBlocking {
    val startTime = System.currentTimeMillis()
    val job = launch {
        var nextPrintTime = startTime
        var i = 0
        while (i < 10) { // 一个执行计算的循环，只是为了占用 CPU
            // 每秒打印消息两次
            if (System.currentTimeMillis() >= nextPrintTime) {
                println("I'm sleeping ${i++} ...")
                nextPrintTime += 500L
            }
        }
    }
    println("runBlocking start")
//    delay(10)
    println("runBlocking start  delay")
//    job.cancel()
    println("delay end")
}


