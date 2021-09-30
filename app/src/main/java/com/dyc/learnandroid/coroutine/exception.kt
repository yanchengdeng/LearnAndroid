package com.dyc.learnandroid.coroutine

import kotlinx.coroutines.*
import java.lang.ArithmeticException
import java.lang.Exception
import java.lang.IndexOutOfBoundsException

/**
 *@Author : yancheng
 *@Date : 2021/1/4
 *@Time : 10:15
 *@Describe ：
 **/

fun main() {

    /**launch和actor构建器是不传播异常的，
     * async和produce是传播异常的
     *
     */

    /**
     * 错误日志：
     * DefaultDispatcher-worker-1 : Throw exception from launch
    Exception in thread "DefaultDispatcher-worker-1" java.lang.IndexOutOfBoundsException
    at com.dyc.learnandroid.coroutine.ExceptionKt$main$1$job$1.invokeSuspend(exception.kt:21)
    at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
    at kotlinx.coroutines.DispatchedTask.run(Dispatched.kt:241)
    at kotlinx.coroutines.scheduling.CoroutineScheduler.runSafely(CoroutineScheduler.kt:594)
    at kotlinx.coroutines.scheduling.CoroutineScheduler.access$runSafely(CoroutineScheduler.kt:60)
    at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.run(CoroutineScheduler.kt:740)
    Exception in thread "main" java.lang.ArithmeticException
    at com.dyc.learnandroid.coroutine.ExceptionKt$main$1$deferred$1.invokeSuspend(exception.kt:28)
    at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
    at kotlinx.coroutines.DispatchedTask.run(Dispatched.kt:241)
    at kotlinx.coroutines.scheduling.CoroutineScheduler.runSafely(CoroutineScheduler.kt:594)
    at kotlinx.coroutines.scheduling.CoroutineScheduler.access$runSafely(CoroutineScheduler.kt:60)
    at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.run(CoroutineScheduler.kt:740)
    Joined failed job



    1. 可以看到 [launch]  在协程中抛出异常，不会造成主线程异常
    2.async  抛出的异常为  at com.dyc.learnandroid.coroutine.ExceptionKt$main$1$deferred$1.invokeSuspend(exception.kt:28) 主线程异常  则直接程序中断
     */
/*    runBlocking {
        val job = GlobalScope.launch {
            println("${Thread.currentThread().name} : Throw exception from launch")
            throw IndexOutOfBoundsException()
        }
        job.join()
        println("Joined failed job")


        val deferred = GlobalScope.async {
            println("${Thread.currentThread().name} :Throw exception from async")
            throw ArithmeticException()
        }
        deferred.await()
        println("unreached")
    }*/

    /**
     * 使用try catch 捕获
     *
     * DefaultDispatcher-worker-1 : Throw exception from launch
    Exception in thread "DefaultDispatcher-worker-1" java.lang.IndexOutOfBoundsException
    at com.dyc.learnandroid.coroutine.ExceptionKt$main$1$job$1.invokeSuspend(exception.kt:74)
    at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
    at kotlinx.coroutines.DispatchedTask.run(Dispatched.kt:241)
    at kotlinx.coroutines.scheduling.CoroutineScheduler.runSafely(CoroutineScheduler.kt:594)
    at kotlinx.coroutines.scheduling.CoroutineScheduler.access$runSafely(CoroutineScheduler.kt:60)
    at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.run(CoroutineScheduler.kt:740)
    java.lang.ArithmeticException
    at com.dyc.learnandroid.coroutine.ExceptionKt$main$1$deferred$1.invokeSuspend(exception.kt:86)
    at kotlin.coroutines.jvm.internal.BaseContinuationImpl.resumeWith(ContinuationImpl.kt:33)
    at kotlinx.coroutines.DispatchedTask.run(Dispatched.kt:241)
    at kotlinx.coroutines.scheduling.CoroutineScheduler.runSafely(CoroutineScheduler.kt:594)
    at kotlinx.coroutines.scheduling.CoroutineScheduler.access$runSafely(CoroutineScheduler.kt:60)
    at kotlinx.coroutines.scheduling.CoroutineScheduler$Worker.run(CoroutineScheduler.kt:740)


    Joined failed job
    DefaultDispatcher-worker-1 :Throw exception from async
    try catch for await  kotlin.Unit
    unreached


     1.发现 捕获到 [await]异常，但[join] 无法捕获

     */


  /*  runBlocking {
        val job = GlobalScope.launch {
            println("${Thread.currentThread().name} : Throw exception from launch")
            throw IndexOutOfBoundsException()
        }
        try {
            job.join()
        } catch (e: Exception) {
            println("try catch for join  ${e.printStackTrace()}")
        }
        println("Joined failed job")


        val deferred = GlobalScope.async {
            println("${Thread.currentThread().name} :Throw exception from async")
            throw ArithmeticException()
        }
        try {
            deferred.await()
        } catch (e: Exception) {
            println("try catch for await  ${e.printStackTrace()}")
        }

        println("unreached")
    }*/


    /**
     * 使用 [CoroutineExceptionHandler]  捕获异常
     *
     */

    val coroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("捕获异常coroutineContext：${coroutineContext.javaClass.canonicalName}")
        println("捕获异常throwable：${throwable}")
    }

    runBlocking {
        val job = GlobalScope.launch(context = coroutineExceptionHandler) {
            println("${Thread.currentThread().name} : Throw exception from launch")
            throw IndexOutOfBoundsException()
        }
//        try {
            job.join()
//        } catch (e: Exception) {
//            println("try catch for join  ${e.printStackTrace()}")
//        }
//        println("Joined failed job")


      /*  val deferred = GlobalScope.async() {
            println("${Thread.currentThread().name} :Throw exception from async")
            throw ArithmeticException()
        }
        try {
            deferred.await()
        } catch (e: Exception) {
            println("try catch for await  ${e.printStackTrace()}")
        }
*/
        println("unreached")
    }

}