package com.rowland.loading.animations

import android.view.animation.Animation
import android.view.animation.Transformation

import com.rowland.loading.views.LoadingView

/**
 * Created by Rowland on 10/21/2017.
 */

class LoadingArcAnimation(private val mLoadingView: LoadingView, animationStep: Int) : Animation() {

    private var mAnimationStep = 10
    private var arcStartAngles: FloatArray

    init {
        this.mAnimationStep = animationStep
        this.arcStartAngles = mLoadingView.arcStartAngles
    }

    public override fun applyTransformation(interpolatedTime: Float, t: Transformation) {
        super.applyTransformation(interpolatedTime, t)

        arcStartAngles[0] = arcStartAngles[0] + this.mAnimationStep
        arcStartAngles[1] = arcStartAngles[1] + this.mAnimationStep

        this.mLoadingView.arcStartAngles = arcStartAngles
        this.mLoadingView.requestLayout()
    }
}
