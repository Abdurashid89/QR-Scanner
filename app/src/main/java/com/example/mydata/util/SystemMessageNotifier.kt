package com.example.mydata.util

import io.reactivex.Observable

class SystemMessageNotifier {
   // private val notifierRelay = PublishRelay.create<SystemMessage>()

//    val notifier: Observable<SystemMessage> = notifierRelay.hide()
//    fun send(message: SystemMessage) = notifierRelay.accept(message)
//    fun send(message: String) = notifierRelay.accept(SystemMessage(message))
}

data class SystemMessage(
    val text: String,
    val type: SystemMessageType = SystemMessageType.ALERT
)

enum class SystemMessageType {
    ALERT,
    TOAST
}