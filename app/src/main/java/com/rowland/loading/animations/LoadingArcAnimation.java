package com.rowland.loading.animations;

import android.view.animation.Animation;
import android.view.animation.Transformation;

import com.rowland.loading.views.LoadingView;

/**
 * Created by Rowland on 10/21/2017.
 */

public class LoadingArcAnimation extends Animation {

    private LoadingView mLoadingView;
    private int mAnimationStep = 10;

    private float[] arcStartAngles;

    public LoadingArcAnimation(LoadingView loadingView, int animationStep) {
        this.mLoadingView = loadingView;
        this.mAnimationStep = animationStep;
        this.arcStartAngles = loadingView.getArcStartAngles();
    }

    @Override
    public void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        arcStartAngles[0] = arcStartAngles[0] + this.mAnimationStep;
        arcStartAngles[1] = arcStartAngles[1] + this.mAnimationStep;

        this.mLoadingView.setArcStartAngles(arcStartAngles);
        this.mLoadingView.requestLayout();
    }
}
