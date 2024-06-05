package com.example.luxmetr.ui.shared

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.math.abs

class SharedViewModel : ViewModel() {

    private val _luxValue = MutableLiveData<Int>().apply {
        value = 0
    }
    val luxValue: LiveData<Int> = _luxValue

    private val _luxStatus = MutableLiveData<String>().apply {
        value = "Unknown"
    }
    val luxStatus: LiveData<String> = _luxStatus

    private val luxData = mutableListOf<Int>()
    private val maxDataPoints = 10 * 60 * 10  // 10 minutes, 100ms intervals
    private var lastSavedTime: Long = 0

    fun setLuxValue(value: Int) {
        _luxValue.value = value
        _luxStatus.value = if (value < 100) "Dim" else "Bright"

        val currentTime = System.currentTimeMillis()
        if (currentTime - lastSavedTime >= 100) {
            lastSavedTime = currentTime
            luxData.add(value)
            if (luxData.size > maxDataPoints) {
                luxData.removeAt(0)
            }
        }
    }

    fun getMaxChange(): Int {
        if (luxData.size < 2) return 0
        var maxChange = 0
        for (i in 1 until luxData.size) {
            val change = abs(luxData[i] - luxData[i - 1])
            if (change > maxChange) {
                maxChange = change
            }
        }
        return maxChange
    }
}
