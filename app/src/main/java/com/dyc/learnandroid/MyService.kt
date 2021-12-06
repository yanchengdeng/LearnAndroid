package com.dyc.learnandroid

import android.app.Service
import android.bluetooth.BluetoothClass
import android.content.Intent
import android.hardware.display.DeviceProductInfo
import android.net.wifi.WifiInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi

/**
 *@Author : yancheng
 *@Date : 2021/12/6
 *@Time : 16:23
 *@Describe ：
 **/
class MyService : Service() {

    companion object{
        const val TAG = "MyTestService"
    }


    override fun onCreate() {
        super.onCreate()
        Log.d(TAG,"onCreate")
    }

    private val mStub = object :IMyAidlInterface.Stub(){
        /**
         * Demonstrates some basic types that you can use as parameters
         * and return values in AIDL.
         */
        override fun basicTypes(
            anInt: Int,
            aLong: Long,
            aBoolean: Boolean,
            aFloat: Float,
            aDouble: Double,
            aString: String?
        ) {

        }

        override fun add(num1: Int, num2: Int): Int {
           return num1 + num2
        }

    }
    override fun onBind(intent: Intent?): IBinder {
        Log.d(TAG,"onBind = $mStub")
        return mStub
    }
}