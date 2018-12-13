package com.rowland.loading.views

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View

import com.rowland.customviews.R
import com.rowland.loading.animations.LoadingArcAnimation
import com.rowland.loading.utils.ArcUtils

/**
 * Created by Rowland on 10/21/2017.
 */

class LoadingView : View {

    private var iconBitmap: Bitmap? = null

    private var iconScaleFactor: Float = 0.toFloat()
    private var circleRadius: Float = 0.toFloat()
    private var circleCentrePoint: PointF? = null
    private var iconBitmapBounds: RectF? = null

    private var arcPaint: Paint? = null
    private var bitmapPaint: Paint? = null
    private var bitmapBackgroundPaint: Paint? = null
    private var circlePaint: Paint? = null

    private var mStartAngleArcOne: Float = 0.toFloat()
    private var mStartAngleArcTwo: Float = 0.toFloat()
    private var mSweepAngle: Float = 0.toFloat()

    constructor(context: Context) : super(context) {
        printLogInfo("Programmatic constructor")
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        printLogInfo("XML constructor")
        init()
    }

    private fun init() {
        // Other resources
        iconBitmap = BitmapFactory.decodeResource(resources, R.drawable.motorbike)
        mStartAngleArcOne = 0f
        mStartAngleArcTwo = 180f
        mSweepAngle = 45f
        // Dimensions
        iconScaleFactor = 0.5f

        cfgPaint()
        cfgAnimation(100000)
    }

    private fun printLogInfo(methodName: String) {
        Log.i("TAG", "$methodName called, measured size: ($measuredWidth, $measuredHeight)")
    }


    private fun cfgPaint() {
        // Arc Paint
        arcPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        arcPaint!!.style = Paint.Style.STROKE
        arcPaint!!.color = Color.YELLOW
        arcPaint!!.strokeWidth = 25f
        // Circle Paint
        circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
        circlePaint!!.style = Paint.Style.STROKE
        circlePaint!!.color = Color.GRAY
        circlePaint!!.alpha = 50
        circlePaint!!.strokeWidth = 1f
        //Bitmap Paint
        bitmapPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        bitmapPaint!!.style = Paint.Style.FILL
        // Bitmap back
        bitmapBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        bitmapBackgroundPaint!!.style = Paint.Style.FILL
        bitmapBackgroundPaint!!.color = Color.YELLOW
    }

    private fun cfgAnimation(duration: Long) {
        val animation = LoadingArcAnimation(this, 10)
        animation.duration = duration
        startAnimation(animation)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        printLogInfo("onFinishInflate")
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        printLogInfo("onAttachedToWindow")
    }


    public override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        printLogInfo("onMeasure")

        val xPad = paddingLeft + paddingRight
        val yPad = paddingTop + paddingBottom

        val minW = xPad + suggestedMinimumWidth
        val measuredWidth = View.getDefaultSize(minW, widthMeasureSpec)

        val minH = yPad + suggestedMinimumHeight
        val measuredHeight = View.getDefaultSize(minH, heightMeasureSpec)

        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    public override fun onSizeChanged(newWidth: Int, newHeight: Int, oldWidth: Int, oldHeight: Int) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight)
        printLogInfo("onSizeChanged")

        val xPad = (paddingLeft + paddingRight).toFloat()
        val yPad = (paddingTop + paddingBottom).toFloat()

        val rectifiedWidth = newWidth - xPad
        val rectifiedHeight = newHeight - yPad

        val xCord = rectifiedWidth / 2f
        val yCord = rectifiedHeight / 2f

        circleCentrePoint = PointF(xCord, yCord)
        circleRadius = Math.min(xCord, yCord)

        val left = xCord - circleRadius / 2 * iconScaleFactor
        val top = yCord - circleRadius / 2 * iconScaleFactor
        val right = xCord + circleRadius / 2 * iconScaleFactor
        val bottom = yCord + circleRadius / 2 * iconScaleFactor
        iconBitmapBounds = RectF(left, top, right, bottom)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        printLogInfo("onLayout")
    }

    public override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        printLogInfo("onDraw")
        onDrawCircle(canvas)
        onDrawArcs(canvas)
        onDrawBitmap(canvas)
    }

    private fun onDrawCircle(canvas: Canvas) {
        canvas.drawCircle(circleCentrePoint!!.x, circleCentrePoint!!.y, circleRadius, circlePaint!!)
    }

    private fun onDrawArcs(canvas: Canvas) {
        ArcUtils.drawArc(canvas, circleCentrePoint!!, circleRadius, mStartAngleArcOne, mSweepAngle, arcPaint!!)
        ArcUtils.drawArc(canvas, circleCentrePoint!!, circleRadius, mStartAngleArcTwo, mSweepAngle, arcPaint!!)
    }

    private fun onDrawBitmap(canvas: Canvas) {
        val backgroundCircleRadius = Math.sqrt(Math.pow(iconBitmapBounds!!.height().toDouble(), 2.0) + Math.pow(iconBitmapBounds!!.width().toDouble(), 2.0)) / 2f
        canvas.drawCircle(iconBitmapBounds!!.centerX(), iconBitmapBounds!!.centerY(), backgroundCircleRadius.toFloat(), bitmapBackgroundPaint!!)
        canvas.drawBitmap(iconBitmap!!, null, iconBitmapBounds!!, bitmapPaint)
    }

    var arcStartAngles: FloatArray
        get() = floatArrayOf(mStartAngleArcOne, mStartAngleArcTwo)
        set(targetStartAngles) {
            mStartAngleArcOne = targetStartAngles[0]
            mStartAngleArcTwo = targetStartAngles[1]
        }
}
