package com.example.mydata.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mydata.util.UIState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: HomeRepository) : ViewModel() {

    private val _barcodeFlow = MutableStateFlow<UIState<String>>(UIState.EMPTY)
    val barcodeFlow: StateFlow<UIState<String>> get() = _barcodeFlow

    fun checkBarCode(barcode: String) = viewModelScope.launch {
        _barcodeFlow.value = UIState.LOADING
        val res = repository.sendBarcode(barcode)
        try {

            if (res.success) {
                _barcodeFlow.value = UIState.SUCCESS(res.message)
            } else {
                _barcodeFlow.value = UIState.ERROR(res.message)
            }
        } catch (e: Exception) {
            _barcodeFlow.value = UIState.ERROR(e.localizedMessage)
        }
    }

    private val _imageFlow = MutableStateFlow<UIState<String>>(UIState.EMPTY)
    val imageFlow: StateFlow<UIState<String>> get() = _imageFlow

    fun checkImageBarCode(barcode: String) = viewModelScope.launch {

        _imageFlow.value = UIState.LOADING
        val res = repository.sendBarcode(barcode)
        try {

            if (res.success) {
                _imageFlow.value = UIState.SUCCESS(res.message)
            } else {
                _imageFlow.value = UIState.ERROR(res.message)
            }
        } catch (e: Exception) {
            _imageFlow.value = UIState.ERROR(e.localizedMessage)
        }

    }
}