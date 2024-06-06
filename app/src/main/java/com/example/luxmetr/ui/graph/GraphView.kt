package com.example.luxmetr.ui.graph

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import kotlin.math.max
import kotlin.math.min

class GraphView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var luxData: List<Int> = emptyList()
    private val paint = Paint().apply {
        color = Color.BLUE
        strokeWidth = 8f // Увеличение размера линии
    }

    private val axisPaint = Paint().apply {
        color = Color.BLACK
        strokeWidth = 8f
        textSize = 30f
    }

    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 40f
    }

    fun setData(data: List<Int>) {
        luxData = data
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (luxData.isEmpty()) return

        val dataSize = luxData.size
        val totalSize = 600
        val paddingFromLine = 30f
        val paddingForValues = 100f
        val paddingLowLine = 100f
        val minHeight = paddingLowLine
        val maxHeight = height.toFloat() - paddingLowLine

        var minLux = ((luxData.minOrNull() ?: 0)).toInt()
        var maxLux = min(((luxData.maxOrNull() ?: 40000)).toInt(), 40000)
        val deltaMinMax = max((maxLux - minLux).toFloat(), 10f)
        minLux -= (deltaMinMax * 0.1f).toInt()
        maxLux += (deltaMinMax * 0.1f).toInt()
        val currentLux = luxData.lastOrNull() ?: 0
        val widthStep = width / (totalSize - 1).toFloat()
        val heightScale = (maxHeight - minHeight) / (maxLux - minLux).toFloat()

        // Draw axes
        canvas.drawLine(0f, maxHeight, width.toFloat(), maxHeight, axisPaint) // X-axis
        canvas.drawLine(0f, minHeight, 0f, maxHeight, axisPaint) // Y-axis

        // Draw labels for axes
        val x = width / 12.toFloat()

        // Seconds labels on X-axis
        for (i in 0..12) {
            val secondsAgo = ((totalSize / 10f) - 5f * i.toFloat()).toInt()
            canvas.drawText("${secondsAgo}s", (x * i.toFloat()), height - paddingFromLine, textPaint)
        }

        // Min, Max, Current labels on Y-axis
        canvas.drawText(minLux.toString(), paddingFromLine, maxHeight - paddingFromLine, textPaint)
        canvas.drawText(maxLux.toString(), paddingFromLine, minHeight + paddingFromLine, textPaint)
        canvas.drawText(currentLux.toString(), paddingFromLine, maxHeight - (currentLux - minLux) * heightScale, textPaint)

        val delta = totalSize - dataSize

        // Draw the graph
        for ((iter, i) in (delta until totalSize - 1).withIndex()) {
            if (iter >= luxData.size - 1) break
            val startX = i * widthStep
            val startY = maxHeight - (luxData[iter] - minLux) * heightScale
            val stopX = (i + 1) * widthStep
            val stopY = maxHeight - (luxData[iter + 1] - minLux) * heightScale
            canvas.drawLine(startX, startY, stopX, stopY, paint)
        }
    }
}
