package com.chaitupenjudcoder.notesapp.adapters;

import android.widget.NumberPicker;

import androidx.databinding.BindingAdapter;

public class NoteBindingAdapters {

    @BindingAdapter("maxValue")
    public static void setMaxValue(NumberPicker np, int value)  {
        np.setMaxValue(value);
    }

    @BindingAdapter("minValue")
    public static void setMinValue(NumberPicker np, int value) {
        np.setMinValue(value);
    }

    @BindingAdapter("setValue")
    public static void setValue(NumberPicker np, int value) {
        np.setValue(value);
    }
}
