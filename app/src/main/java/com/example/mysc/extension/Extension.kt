package com.example.mysc.extension

import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showMessage(message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}