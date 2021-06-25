package com.example.mydata.server

import com.readystatesoftware.chuck.internal.ui.MainActivity
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit = Retrofit.Builder()
//        .baseUrl("http://base url")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    @Singleton
    fun getApi(retrofit: Retrofit): Api = retrofit.create(Api::class.java)

}