package com.example.luxmetr.ui.luxview

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LuxViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is lux"
    }
    val text: LiveData<String> = _text
}