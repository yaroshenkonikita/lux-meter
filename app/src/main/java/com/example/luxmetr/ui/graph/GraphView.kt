package com.example.luxmetr.ui.graph

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class GraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var luxData: List<Int> = emptyList()
    private val paint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 5f
    }

    fun setData(data: List<Int>) {
        luxData = data
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (luxData.isEmpty()) return

        val maxLux = luxData.maxOrNull() ?: 1
        val widthStep = width / (luxData.size - 1).toFloat()
        val heightScale = height / maxLux.toFloat()

        for (i in 0 until luxData.size - 1) {
            val startX = i * widthStep
            val startY = height - luxData[i] * heightScale
            val stopX = (i + 1) * widthStep
            val stopY = height - luxData[i + 1] * heightScale
            canvas.drawLine(startX, startY, stopX, stopY, paint)
        }
    }
}
