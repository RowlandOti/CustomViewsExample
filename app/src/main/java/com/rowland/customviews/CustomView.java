package com.rowland.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.Paint;
import android.graphics.RectF;
import android.os.Build;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by Rowland on 7/16/2017.
 */

public class CustomView extends View {

    private Paint paint;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);
    }

    /*
    * Called when the View is first assigned a diameter, and agina if the diameter chnages for any reason.
    * Calculate positions, dimensions and any other View values here
    * */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        float xPad = getPaddingLeft() + getPaddingRight();
        float yPad = getPaddingTop() + getPaddingBottom();

        float ww = w - xPad;
        float hh = h - yPad;
    }

    /**
     * <p>
     * Measure the view and its content to determine the measured width and the
     * measured height. This method provides accurate and efficient
     * measurement of their contents.
     * </p>
     */
/*    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int measuredWidth = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        int measuredHeight = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);
        setMeasuredDimension(measuredWidth, measuredHeight);
    }*/

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int minW = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int measuredWidth = getDefaultSize(minW, widthMeasureSpec);

        int minH = getPaddingBottom() + getPaddingTop() + getSuggestedMinimumHeight();
        int measuredHeight = getDefaultSize(minH, heightMeasureSpec);

        setMeasuredDimension(measuredWidth, measuredHeight);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        //drawCircle(canvas);
        drawRect(canvas);
        drawArc(canvas);
        drawText(canvas);
        drawOval(canvas);
        drawArcOne(canvas);
        drawArcTwo(canvas);
        onDrawBitmap(canvas, BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher_round));
    }

    private void drawArcOne(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10f);

        float left = getLeft();
        float top = getTop();
        float right = getMeasuredWidth() ;
        float bottom = getMeasuredHeight() ;
        RectF bounds = new RectF(left, top, right, bottom);
        canvas.drawArc(bounds, 0f, 45f, true, paint);
    }

    private void drawArcTwo(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.RED);
        paint.setStrokeWidth(10f);

        float left = getMeasuredWidth() / 2 - 200;
        float top = getMeasuredHeight() / 2 - 200;
        float right = getMeasuredWidth() / 2 + 200;
        float bottom = getMeasuredHeight() / 2 + 200;
        RectF bounds = new RectF(left, top, right, bottom);
        canvas.drawArc(bounds, 180f, 45f, false, paint);
    }

    private void drawArc(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GREEN);

        float left = getMeasuredWidth() / 2 - 200;
        float top = getMeasuredHeight() / 2 - 200;
        float right = getMeasuredWidth() / 2 + 200;
        float bottom = getMeasuredHeight() / 2 + 200;
        RectF bounds = new RectF(left, top, right, bottom);
        canvas.drawArc(bounds, 45f, 180f, true, paint);
    }


    private void drawRect(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);

        float left = getMeasuredWidth() / 2 - 200;
        float top = getMeasuredHeight() / 2 - 200;
        float right = getMeasuredWidth() / 2 + 200;
        float bottom = getMeasuredHeight() / 2 + 200;
        canvas.drawRect(left, top, right, bottom, paint);
    }

    private void drawText(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);
        paint.setTextSize(40);

        float left = getMeasuredWidth() / 2;
        float top = getMeasuredHeight() / 2 ;
        canvas.drawText("Unsullied", left, top, paint);
    }

    private void drawOval(Canvas canvas) {
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.GRAY);

        float left = getMeasuredWidth() / 2 - 300;
        float top = getMeasuredHeight() / 2 - 200;
        float right = getMeasuredWidth() / 2 + 300;
        float bottom = getMeasuredHeight() / 2 + 200;
        RectF bounds = new RectF(left, top, right, bottom);
        canvas.drawOval(bounds, paint);
    }

    private void drawCircle(Canvas canvas) {
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.RED);

        float left = getMeasuredWidth() / 2 - 200;
        float top = getMeasuredHeight() / 2 - 200;
        float right = getMeasuredWidth() / 2 + 200;
        float bottom = getMeasuredHeight() / 2 + 200;
        RectF bounds = new RectF(left, top, right, bottom);
        canvas.drawOval(bounds, paint);
    }

    private void onDrawBitmap(Canvas canvas, Bitmap iconBitmap) {
        float left = getMeasuredWidth() / 2 - iconBitmap.getWidth();
        float top = getMeasuredHeight() / 2 - iconBitmap.getHeight();
        float right = getMeasuredWidth() / 2 + iconBitmap.getWidth();
        float bottom = getMeasuredHeight() / 2 + iconBitmap.getHeight();
        RectF bounds = new RectF(left, top, right, bottom);
        canvas.drawBitmap(iconBitmap, null , bounds, null);
    }
}
