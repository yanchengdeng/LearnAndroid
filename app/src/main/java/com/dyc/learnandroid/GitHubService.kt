package com.dyc.learnandroid

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path


/**
 *@Author : yancheng
 *@Date : 2020/12/30
 *@Time : 16:59
 *@Describe ：
 **/
interface GitHubService {

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Call<List<Repo>>
}