package net.eucalypto.bignerdranch.draganddraw

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import timber.log.Timber

class BoxDrawingView(context: Context, attributeSet: AttributeSet? = null) :
    View(context, attributeSet) {

    private var currentBox: Box? = null
    private val boxes = mutableListOf<Box>()
    private val boxPaint = Paint().apply { color = 0x22ff0000.toInt() }
    private val backgroundPaint = Paint().apply { color = 0xfff8efe8.toInt() }

    override fun onDraw(canvas: Canvas) {
        canvas.drawPaint(backgroundPaint)

        boxes.forEach { box ->
            canvas.drawRect(box.left, box.top, box.right, box.bottom, boxPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        val current = PointF(event.x, event.y)
        var action = ""
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                action = "ACTION_DOWN"
                currentBox = Box(current).also {
                    boxes.add(it)
                }
            }
            MotionEvent.ACTION_MOVE -> {
                action = "ACTION_MOVE"
                updateCurrentBox(current)
            }
            MotionEvent.ACTION_UP -> {
                action = "ACTION_UP"
                updateCurrentBox(current)
                currentBox = null
            }
            MotionEvent.ACTION_CANCEL -> {
                action = "ACTION_CANCEL"
                currentBox = null
            }
        }

        Timber.d("$action at x=${current.x}, y=${current.y}")

        return true
    }

    private fun updateCurrentBox(current: PointF) {
        currentBox?.let {
            it.end = current
            invalidate()
        }
    }
}