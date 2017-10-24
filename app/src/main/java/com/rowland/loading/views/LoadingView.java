package com.rowland.loading.views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.rowland.customviews.R;
import com.rowland.loading.animations.LoadingArcAnimation;
import com.rowland.loading.utils.ArcUtils;

/**
 * Created by Rowland on 10/21/2017.
 */

public class LoadingView extends View {

    private Bitmap iconBitmap;

    // Values/Dimensions
    private float iconScaleFactor;
    private float circleRadius;
    private PointF circleCentrePoint;
    private RectF iconBitmapBounds;

    private Paint arcPaint;
    private Paint bitmapPaint;
    private Paint bitmapBackgroundPaint;
    private Paint circlePaint;

    private float mStartAngleArcOne;
    private float mStartAngleArcTwo;
    private float mSweepAngle;

    public LoadingView(Context context) {
        super(context);
        init();
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        // Other resources
        iconBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.motorbike);
        mStartAngleArcOne = 0f;
        mStartAngleArcTwo = 180f;
        mSweepAngle = 45f;
        // Dimensions
        iconScaleFactor = 0.5f;

        cfgPaint();
        cfgAnimation(100000);
    }

    private void cfgPaint() {
        // Arc Paint
        arcPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        arcPaint.setStyle(Paint.Style.STROKE);
        arcPaint.setColor(Color.YELLOW);
        arcPaint.setStrokeWidth(25f);
        // Circle Paint
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(Color.GRAY);
        circlePaint.setAlpha(50);
        circlePaint.setStrokeWidth(1f);
        //Bitmap Paint
        bitmapPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapPaint.setStyle(Paint.Style.FILL);
        // Bitmap back
        bitmapBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        bitmapBackgroundPaint.setStyle(Paint.Style.FILL);
        bitmapBackgroundPaint.setColor(Color.YELLOW);
    }

    private void cfgAnimation(long duration) {
        LoadingArcAnimation animation = new LoadingArcAnimation(this, 10);
        animation.setDuration(duration);
        startAnimation(animation);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int xPad = getPaddingLeft() + getPaddingRight();
        int yPad = getPaddingTop() + getPaddingBottom();

        int minW = xPad + getSuggestedMinimumWidth();
        int measuredWidth = getDefaultSize(minW, widthMeasureSpec);

        int minH = yPad + getSuggestedMinimumHeight();
        int measuredHeight = getDefaultSize(minH, heightMeasureSpec);

        setMeasuredDimension(measuredWidth, measuredHeight);
    }

    @Override
    public void onSizeChanged(int newWidth, int newHeight, int oldWidth, int oldHeight) {
        super.onSizeChanged(newWidth, newHeight, oldWidth, oldHeight);

        float xPad = getPaddingLeft() + getPaddingRight();
        float yPad = getPaddingTop() + getPaddingBottom();

        float rectifiedWidth = newWidth - xPad;
        float rectifiedHeight = newHeight - yPad;

        float xCord = rectifiedWidth / 2f;
        float yCord = rectifiedHeight / 2f;

        circleCentrePoint = new PointF(xCord, yCord);
        circleRadius = Math.min(xCord, yCord);

        float left = xCord - circleRadius / 2 * iconScaleFactor;
        float top = yCord - circleRadius / 2 * iconScaleFactor;
        float right = xCord + circleRadius / 2 * iconScaleFactor;
        float bottom = yCord + circleRadius / 2 * iconScaleFactor;
        iconBitmapBounds = new RectF(left, top, right, bottom);
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        onDrawCircle(canvas);
        onDrawArcs(canvas);
        onDrawBitmap(canvas);
    }

    private void onDrawCircle(Canvas canvas) {
        canvas.drawCircle(circleCentrePoint.x, circleCentrePoint.y, circleRadius, circlePaint);
    }

    private void onDrawArcs(Canvas canvas) {
        ArcUtils.drawArc(canvas, circleCentrePoint, circleRadius, mStartAngleArcOne, mSweepAngle, arcPaint);
        ArcUtils.drawArc(canvas, circleCentrePoint, circleRadius, mStartAngleArcTwo, mSweepAngle, arcPaint);
    }

    private void onDrawBitmap(Canvas canvas) {
        double backgroundCircleRadius = Math.sqrt(Math.pow(iconBitmapBounds.height(), 2) + Math.pow(iconBitmapBounds.width(), 2)) / 2f;
        canvas.drawCircle(iconBitmapBounds.centerX(), iconBitmapBounds.centerY(), (float) (backgroundCircleRadius), bitmapBackgroundPaint);
        canvas.drawBitmap(iconBitmap, null, iconBitmapBounds, bitmapPaint);
    }

    public float[] getArcStartAngles() {
        return new float[]{mStartAngleArcOne, mStartAngleArcTwo};
    }

    public void setArcStartAngles(float[] targetStartAngles) {
        mStartAngleArcOne = targetStartAngles[0];
        mStartAngleArcTwo = targetStartAngles[1];
    }
}
