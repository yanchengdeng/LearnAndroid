package com.dyc.learnandroid.coroutine

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.random.Random
import kotlin.system.measureTimeMillis

/**
 *@Author : yancheng
 *@Date : 2021/1/4
 *@Time : 9:51
 *@Describe ：
 **/

fun main() {


    /**
     * 直接挂起操作 耗时
     * randomNum:93
    randomNum:93
    count is :186
    cosume time  is :2037
     */
    val data = runBlocking {

        val time  = measureTimeMillis {
            val data1 = produceNum()
            val data2 = produceNum()

            println("count is :${data1 +data2}")
        }
        println("consume time  is :${time}")
    }


    val data1 = runBlocking {

        val time  = measureTimeMillis {
            val data1 = async() {  produceNum()}
            val data2 = async { produceNum()}

            println("async_count is :${data1.await() + data2.await()}")
        }
        println("async_consume time  is :${time}")
    }


//    如果我们希望只有调用了await方法才会开始启动协程，而不是在定义的时候就立马启动，
//    可以针对async方法传入start参数CoroutineStart.LAZY。


    val data2 = runBlocking {

        val time  = measureTimeMillis {
            val data1 = async(start = CoroutineStart.LAZY) {  produceNum()}
            val data2 = async(start = CoroutineStart.LAZY) { produceNum()}

            data1.start()
            data2.start()

            println("async_lazy_count is :${data1.await() + data2.await()}")
        }
        println("async_lazy_consume time  is :${time}")
    }

}

suspend fun produceNum(): Int{
    val randomNum = Random(100).nextInt(0,100)
    delay(1000)
    println("randomNum:$randomNum")
    return randomNum
}