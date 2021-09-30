package com.dyc.learnandroid

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 *@Author : yancheng
 *@Date : 2020/12/30
 *@Time : 16:56
 *@Describe ：
 **/
object ApiService {

    var retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.github.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build()

    var service: GitHubService = retrofit.create(GitHubService::class.java)
}