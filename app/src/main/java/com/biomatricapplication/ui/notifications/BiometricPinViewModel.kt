package com.biomatricapplication.ui.notifications

import android.text.TextUtils
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


class BiometricPinViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Biometric + Pin fragment"
    }
    fun setText(text: String?) {
        if (!TextUtils.isEmpty(text)) {
            this._text.setValue(text)
        }
    }
    val text: LiveData<String> = _text
}