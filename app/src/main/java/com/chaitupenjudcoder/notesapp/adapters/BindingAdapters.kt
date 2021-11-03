package com.chaitupenjudcoder.notesapp.adapters

import android.widget.NumberPicker
import androidx.databinding.BindingAdapter

object BindingAdapters {

    @JvmStatic
    @BindingAdapter("maxValue")
    fun setMaxValue(np: NumberPicker, value: Int) {
        np.maxValue = value
    }

    @JvmStatic
    @BindingAdapter("minValue")
    fun setMinValue(np: NumberPicker, value: Int) {
        np.minValue = value
    }

    @JvmStatic
    @BindingAdapter("setValue")
    fun setValue(np: NumberPicker, value: Int) {
        np.value = value
    }
}