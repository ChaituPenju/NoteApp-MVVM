package com.chaitupenjudcoder.notesapp.utils

import android.app.Activity
import android.widget.Toast

fun Activity.showToast(message: String) {
    Toast.makeText(this@showToast, message, Toast.LENGTH_LONG).show()
}