package com.example.mydata.ui.qr_code

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydata.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QRViewModel @Inject constructor(private val repository: QRRepository) : ViewModel() {

    private val _text = MutableStateFlow<UIState<String>>(UIState.EMPTY)
    val text: StateFlow<UIState<String>> = _text

    fun sendQrCode(code : String) = viewModelScope.launch {

        val res = repository.getText()

        _text.value = UIState.SUCCESS(res)

    }
}