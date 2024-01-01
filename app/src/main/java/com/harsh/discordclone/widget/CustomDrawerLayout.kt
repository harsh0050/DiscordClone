package com.harsh.discordclone.widget

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import com.harsh.discordclone.util.UtilMethod
import kotlin.math.abs

class CustomDrawerLayout(context: Context, attr: AttributeSet? = null) :
    ViewGroup(context, attr) {

    private var leftView: View? = null
    private var mainContentView: View? = null


    private var isHorizontalScroll = false
    private var checkScroll = true
    private var isDrawerOpen = false
    private var isDrawerInTransit = false

    private val elevationInPx: Float = UtilMethod.dpToPixels(4f, context).toFloat()

    private val gestureDetector = MyGestureDetector(context)

    //TODO (rename drawer and main container)

    override fun onFinishInflate() {
        super.onFinishInflate()

        // Assuming that there are only two children in this custom layout
        if (childCount == 2) {
            leftView = getChildAt(0)
            mainContentView = getChildAt(1)
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

        leftView?.measure(
            getChildMeasureSpec(widthMeasureSpec, 0, leftView?.layoutParams?.width ?: 0),
            getChildMeasureSpec(heightMeasureSpec, 0, leftView?.layoutParams?.height ?: 0)
        )

        // Set the size of this layout based on the measured dimensions of child views

        setMeasuredDimension(
            mainContentView?.measuredWidth ?: 0,
            mainContentView?.measuredHeight ?: 0
        )
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        leftView?.layout(0, 0, leftView?.measuredWidth ?: 0, leftView?.measuredHeight ?: 0)
        mainContentView?.layout(
            0,
            0,
            mainContentView?.measuredWidth ?: 0,
            mainContentView?.measuredHeight ?: 0
        )
    }

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        gestureDetector.onTouch(this, event)
        return isHorizontalScroll
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean { //In Transition
        isDrawerInTransit = true
        mainContentView?.elevation = elevationInPx

        if (event.action == MotionEvent.ACTION_UP) {
            if (isDrawerInTransit && mainContentView != null && leftView != null) {
                if ((mainContentView!!.translationX >= leftView!!.measuredWidth / 2)) {
                    open()
                } else {
                    close()
                }
            }

            mainContentView?.elevation = 0f
        }
        return gestureDetector.onTouch(this, event)
    }


    val isOpen: Boolean
        get() {
            return isDrawerOpen
        }

    fun open(initialVelocity: Float = 0f) {
        if (isDrawerOpen && !isDrawerInTransit)
            return
        isDrawerOpen = true

        mainContentView?.let {
            SpringAnimation(
                it,
                SpringAnimation.TRANSLATION_X,
                (leftView?.measuredWidth ?: 0).toFloat()
            ).apply {
                spring.dampingRatio = SpringForce.DAMPING_RATIO_NO_BOUNCY
                setStartVelocity(initialVelocity)
            }.start()
        }
        isDrawerInTransit = false
    }

    fun close(initialVelocity: Float = 0f) {
        if (!isDrawerOpen && !isDrawerInTransit)
            return
        isDrawerOpen = false
        mainContentView?.let {
            SpringAnimation(it, SpringAnimation.TRANSLATION_X, 0f).apply {
                spring.dampingRatio = SpringForce.DAMPING_RATIO_NO_BOUNCY
                setStartVelocity(initialVelocity)
            }.start()
        }
        isDrawerInTransit = false
    }

    private inner class MyGestureDetector(context: Context) : OnTouchListener,
        GestureDetector.OnGestureListener {

        private val _gestureDetector = GestureDetector(context, this)
        override fun onTouch(v: View?, event: MotionEvent): Boolean {
            if (event.action == MotionEvent.ACTION_UP)
                v?.performClick()
            return _gestureDetector.onTouchEvent(event)
        }

        override fun onDown(e: MotionEvent): Boolean {
            checkScroll = true
            isHorizontalScroll = false

            return true
        }

        override fun onShowPress(e: MotionEvent) {}

        override fun onSingleTapUp(e: MotionEvent): Boolean = true

        override fun onScroll(
            e1: MotionEvent?,
            e2: MotionEvent,
            distanceX: Float,
            distanceY: Float
        ): Boolean {
            if (checkScroll) {
                checkScroll = false
                isHorizontalScroll = abs(distanceX) > abs(distanceY)
            }
            if (!isHorizontalScroll)
                return true

            if (mainContentView != null && leftView != null) {
                (mainContentView!!.translationX - distanceX).let { finalTranslation ->
                    if (finalTranslation > 0 && finalTranslation < leftView!!.measuredWidth) {
                        mainContentView!!.translationX -= distanceX
                    }
                }
            }
            return true

        }

        override fun onLongPress(e: MotionEvent) {}

        override fun onFling(
            e1: MotionEvent?,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (velocityX > 0) {
                open(velocityX)
            } else {
                close(velocityX)
            }
            return true
        }

    }
}