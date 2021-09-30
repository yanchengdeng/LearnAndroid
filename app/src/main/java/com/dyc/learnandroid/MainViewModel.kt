package com.dyc.learnandroid

import android.util.Log
import android.widget.TextView
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.reactivex.disposables.Disposable
import io.reactivex.disposables.Disposables
import kotlinx.coroutines.*
import org.w3c.dom.Text
import kotlin.coroutines.EmptyCoroutineContext

/**
 *@Author : yancheng
 *@Date : 2020/12/30
 *@Time : 16:51
 *@Describe ：
 **/
class MainViewModel : ViewModel() {


    private var requestDatas: MutableLiveData<List<Repo>> = MutableLiveData()


    fun fetchDocs() {

        runBlocking {

        }


        viewModelScope.launch {

        }


        GlobalScope.launch (EmptyCoroutineContext,CoroutineStart.DEFAULT,block = {
            get("")

        })

        GlobalScope.async {

        }



        viewModelScope.launch {
            // Dispatchers.Main
            val result = get("https://developer.android.com") // Dispatchers.IO for `get`
//            requestDatas.value = result                         // Dispatchers.Main
         val job =    withContext(Dispatchers.Default) {
                Log.d("TAG", "withContext：${Thread.currentThread().name}")
                val current = System.currentTimeMillis()
                launch(Dispatchers.Default){
                    Log.d("TAG", "launch Default：${Thread.currentThread().name}")
                }
                val datas = ApiService.service.listRepos("octocat")
                Log.d("TAG", "消耗时间1：${System.currentTimeMillis() - current}")
                Log.d("TAG", "isActive1：$isActive")
                Log.d("TAG", "${datas.execute().body()}")
                launch(Dispatchers.Main){
                    Log.d("TAG", "launch MAIN：${Thread.currentThread().name}")
                }
                Log.d("TAG", "消耗时间2：${System.currentTimeMillis() - current}")
                Log.d("TAG", "isActive2：$isActive")
                launch(Dispatchers.IO){
                    Log.d("TAG", "launch IO：${Thread.currentThread().name}")
                }
//                cancel()
                Log.d("TAG", "isActive3：$isActive")
            }



        }


    }

    suspend fun get(url: String) = withContext(Dispatchers.IO) {
        ApiService.service.listRepos("octocat")
    }
}