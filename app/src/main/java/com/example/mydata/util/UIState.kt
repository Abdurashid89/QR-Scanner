package com.example.mydata.util

sealed class UIState<out T>{
    data class SUCCESS<out T>(val data : T) : UIState<T>()
    data class ERROR(val message : String) : UIState<Nothing>()
    object LOADING : UIState<Nothing>()
    object EMPTY : UIState<Nothing>()
}
