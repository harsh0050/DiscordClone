package com.harsh.discordclone.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.customview.widget.Openable
import kotlin.math.abs

class CustomDrawerLayout(context: Context, attr: AttributeSet? = null) :
    Openable, ViewGroup(context, attr) {

    private var drawerView: View? = null
    private var mainContentView: View? = null
    private var isDrawerOpen = false
    private val gestureDetector = MyGestureDetector(context)


    private var isHorizontalScroll = false
    private var checkScroll = true

    override fun onFinishInflate() {
        super.onFinishInflate()

        // Assuming that there are only two children in this custom layout
        if (childCount == 2) {
            mainContentView = getChildAt(0)
            drawerView = getChildAt(1)
        } else {
            throw IllegalStateException("CustomDrawerLayout must have exactly two children.")
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        // Measure child views based on layout parameters
        mainContentView?.measure(
            getChildMeasureSpec(widthMeasureSpec, 0, mainContentView?.layoutParams?.width ?: 0),
            getChildMeasureSpec(heightMeasureSpec, 0, mainContentView?.layoutParams?.height ?: 0)
        )

        drawerView?.measure(
            getChildMeasureSpec(widthMeasureSpec, 0, drawerView?.layoutParams?.width ?: 0),
            getChildMeasureSpec(heightMeasureSpec, 0, drawerView?.layoutParams?.height ?: 0)
        )

        // Set the size of this layout based on the measured dimensions of child views

        setMeasuredDimension(
            mainContentView?.measuredWidth ?: 0,
            mainContentView?.measuredHeight ?: 0
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        mainContentView?.layout(
            0,
            0,
            mainContentView?.measuredWidth ?: 0,
            mainContentView?.measuredHeight ?: 0
        )
        drawerView?.layout(0, 0, drawerView?.measuredWidth ?: 0, drawerView?.measuredHeight ?: 0)
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouch(this, event)
        return isHorizontalScroll
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {
        return gestureDetector.onTouch(this, event)
    }

    override fun isOpen(): Boolean {
        return isDrawerOpen
    }

    override fun open() {
        //TODO
    }

    override fun close() {
        //TODO
    }

    private inner class MyGestureDetector(context: Context) : OnTouchListener,
        GestureDetector.OnGestureListener {

        private val _gestureDetector = GestureDetector(context, this)
        override fun onTouch(v: View?, event: MotionEvent): Boolean {
            if(event.action== MotionEvent.ACTION_UP)
                v?.performClick()
            return _gestureDetector.onTouchEvent(event)
        }

        override fun onDown(e: MotionEvent): Boolean {
            checkScroll = true
            isHorizontalScroll = false

            return true
        }

        override fun onShowPress(e: MotionEvent) {
        }

        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return true
        }

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if(checkScroll){
                checkScroll = false
                isHorizontalScroll = abs(distanceX)>abs(distanceY)
            }
            return true

        }

        override fun onLongPress(e: MotionEvent) {
        }

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            return true
        }

    }
}