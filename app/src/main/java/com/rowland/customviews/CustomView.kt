package com.rowland.customviews

import android.annotation.TargetApi
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.EmbossMaskFilter
import android.graphics.Paint
import android.graphics.RectF
import android.os.Build
import android.util.AttributeSet
import android.view.View

/**
 * Created by Rowland on 7/16/2017.
 */

class CustomView : View {

    private var paint: Paint? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {
        paint = Paint(Paint.ANTI_ALIAS_FLAG)
        paint!!.style = Paint.Style.FILL
        paint!!.color = Color.RED
    }

    /*
    * Called when the View is first assigned a diameter, and agina if the diameter chnages for any reason.
    * Calculate positions, dimensions and any other View values here
    * */
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        val xPad = (paddingLeft + paddingRight).toFloat()
        val yPad = (paddingTop + paddingBottom).toFloat()

        val ww = w - xPad
        val hh = h - yPad
    }

    /**
     *
     *
     * Measure the view and its content to determine the measured width and the
     * measured height. This method provides accurate and efficient
     * measurement of their contents.
     *
     */
    /*    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int measuredHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }*/

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val minW = paddingLeft + paddingRight + suggestedMinimumWidth
        val measuredWidth = View.getDefaultSize(minW, widthMeasureSpec)

        val minH = paddingBottom + paddingTop + suggestedMinimumHeight
        val measuredHeight = View.getDefaultSize(minH, heightMeasureSpec)

        setMeasuredDimension(measuredWidth, measuredHeight)
    }


    override fun onDraw(canvas: Canvas) {
        //drawCircle(canvas);
        drawRect(canvas)
        drawArc(canvas)
        drawText(canvas)
        drawOval(canvas)
        drawArcOne(canvas)
        drawArcTwo(canvas)
        onDrawBitmap(canvas, BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher_round))
    }

    private fun drawArcOne(canvas: Canvas) {
        paint!!.style = Paint.Style.STROKE
        paint!!.color = Color.RED
        paint!!.strokeWidth = 10f

        val left = left.toFloat()
        val top = top.toFloat()
        val right = measuredWidth.toFloat()
        val bottom = measuredHeight.toFloat()
        val bounds = RectF(left, top, right, bottom)
        canvas.drawArc(bounds, 0f, 45f, true, paint!!)
    }

    private fun drawArcTwo(canvas: Canvas) {
        paint!!.style = Paint.Style.STROKE
        paint!!.color = Color.RED
        paint!!.strokeWidth = 10f

        val left = (measuredWidth / 2 - 200).toFloat()
        val top = (measuredHeight / 2 - 200).toFloat()
        val right = (measuredWidth / 2 + 200).toFloat()
        val bottom = (measuredHeight / 2 + 200).toFloat()
        val bounds = RectF(left, top, right, bottom)
        canvas.drawArc(bounds, 180f, 45f, false, paint!!)
    }

    private fun drawArc(canvas: Canvas) {
        paint!!.style = Paint.Style.STROKE
        paint!!.color = Color.GREEN

        val left = (measuredWidth / 2 - 200).toFloat()
        val top = (measuredHeight / 2 - 200).toFloat()
        val right = (measuredWidth / 2 + 200).toFloat()
        val bottom = (measuredHeight / 2 + 200).toFloat()
        val bounds = RectF(left, top, right, bottom)
        canvas.drawArc(bounds, 45f, 180f, true, paint!!)
    }


    private fun drawRect(canvas: Canvas) {
        paint!!.style = Paint.Style.STROKE
        paint!!.color = Color.BLACK

        val left = (measuredWidth / 2 - 200).toFloat()
        val top = (measuredHeight / 2 - 200).toFloat()
        val right = (measuredWidth / 2 + 200).toFloat()
        val bottom = (measuredHeight / 2 + 200).toFloat()
        canvas.drawRect(left, top, right, bottom, paint!!)
    }

    private fun drawText(canvas: Canvas) {
        paint!!.style = Paint.Style.STROKE
        paint!!.color = Color.GRAY
        paint!!.textSize = 40f

        val left = (measuredWidth / 2).toFloat()
        val top = (measuredHeight / 2).toFloat()
        canvas.drawText("Unsullied", left, top, paint!!)
    }

    private fun drawOval(canvas: Canvas) {
        paint!!.style = Paint.Style.STROKE
        paint!!.color = Color.GRAY

        val left = (measuredWidth / 2 - 300).toFloat()
        val top = (measuredHeight / 2 - 200).toFloat()
        val right = (measuredWidth / 2 + 300).toFloat()
        val bottom = (measuredHeight / 2 + 200).toFloat()
        val bounds = RectF(left, top, right, bottom)
        canvas.drawOval(bounds, paint!!)
    }

    private fun drawCircle(canvas: Canvas) {
        paint!!.style = Paint.Style.FILL
        paint!!.color = Color.RED

        val left = (measuredWidth / 2 - 200).toFloat()
        val top = (measuredHeight / 2 - 200).toFloat()
        val right = (measuredWidth / 2 + 200).toFloat()
        val bottom = (measuredHeight / 2 + 200).toFloat()
        val bounds = RectF(left, top, right, bottom)
        canvas.drawOval(bounds, paint!!)
    }

    private fun onDrawBitmap(canvas: Canvas, iconBitmap: Bitmap) {
        val left = (measuredWidth / 2 - iconBitmap.width).toFloat()
        val top = (measuredHeight / 2 - iconBitmap.height).toFloat()
        val right = (measuredWidth / 2 + iconBitmap.width).toFloat()
        val bottom = (measuredHeight / 2 + iconBitmap.height).toFloat()
        val bounds = RectF(left, top, right, bottom)
        canvas.drawBitmap(iconBitmap, null, bounds, null)
    }
}
