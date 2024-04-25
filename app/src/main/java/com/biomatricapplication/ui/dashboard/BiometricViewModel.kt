package com.biomatricapplication.ui.dashboard

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class BiometricViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    fun setText(text: String?) {
        if (!TextUtils.isEmpty(text)) {
            this._text.setValue(text)
        }
    }
    val text: LiveData<String> = _text
}