package com.rowland.loading.layouts

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import android.widget.RelativeLayout

/**
 * Created by Rowland on 10/21/2017.
 */

class TransluscentLoadingLayout : RelativeLayout {

    constructor(context: Context) : super(context) {}

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {}

    public override fun onFinishInflate() {
        super.onFinishInflate()

    }

    public override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        super.onLayout(changed, l, t, r, b)
    }
}
