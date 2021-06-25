package com.example.mydata.ui.qr_code

import com.example.mydata.server.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QRRepository @Inject constructor(private val api: Api) {

    suspend fun getText() = "return"
}