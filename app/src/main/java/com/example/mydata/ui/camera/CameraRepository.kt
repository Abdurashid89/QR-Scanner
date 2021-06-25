package com.example.mydata.ui.camera

import com.example.mydata.server.Api
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CameraRepository @Inject constructor(private val api: Api) {

suspend fun getText() = "return"
}