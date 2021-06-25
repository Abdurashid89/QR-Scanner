package com.example.mydata.server

import com.example.mydata.entity.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Path

interface Api {


    @POST("api")
    fun sendCode(@Path("code") code: String): Response

    @Multipart
    @POST("api")
    fun sendImage(@Path("code") code: String): Response
}