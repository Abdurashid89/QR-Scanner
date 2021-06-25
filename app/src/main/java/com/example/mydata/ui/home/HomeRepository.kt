package com.example.mydata.ui.home

import com.example.mydata.server.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepository @Inject constructor(private val api: Api) {

    suspend fun sendBarcode(barcode: String) = api.sendCode(barcode)

    suspend fun sendImageBarcode(barcode: String) = api.sendImage(barcode)
}