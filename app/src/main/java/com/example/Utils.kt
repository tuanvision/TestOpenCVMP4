package com.example

import android.content.Context
import android.widget.Toast

//LCE -> Loading/Content/Error
sealed class LCE<out T> {
    data class Result<T>(val data: T, val error: Boolean = true, val message: String = "error") : LCE<T>()
    data class Error<T>(val message: String) : LCE<T>() {
        constructor(t: Throwable) : this(t.message ?: "")
    }
}

fun Context.toast(message: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, length).show()
}