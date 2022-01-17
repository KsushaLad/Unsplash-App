package com.codinginflow.imagesearchapp.scale

import android.annotation.SuppressLint
import android.content.Context
import android.view.ScaleGestureDetector
import android.view.MotionEvent
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.View


class MyScaleGestures(context: Context?) : View.OnTouchListener, OnScaleGestureListener {
    private var view: View? = null
    private val gestureScale by lazy { ScaleGestureDetector(context, this) }
    private var scaleFactor = 0.1f
    private var inScale = false

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouch(view: View?, event: MotionEvent?): Boolean {
        this.view = view
        gestureScale.onTouchEvent(event)
        return true
    }

    override fun onScale(detector: ScaleGestureDetector): Boolean {
        scaleFactor *= detector.scaleFactor
        scaleFactor =
            if (scaleFactor < 1) 1F else scaleFactor
        scaleFactor = (scaleFactor * 1150).toInt()
            .toFloat() / 1150
        view?.scaleX = scaleFactor
        view?.scaleY = scaleFactor
        return true
    }

    override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
        inScale = true
        return true
    }

    override fun onScaleEnd(detector: ScaleGestureDetector) {
        inScale = false
    }
}