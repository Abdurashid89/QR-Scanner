package com.example.mydata.ui.camera

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydata.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CameraViewModel @Inject constructor (private val repository: CameraRepository) : ViewModel() {

    private val _text = MutableStateFlow<UIState<String>>(UIState.EMPTY)
    val text: StateFlow<UIState<String>> = _text

    fun getText() = viewModelScope.launch {

        val res = repository.getText()

        _text.value = UIState.SUCCESS(res)

    }
}