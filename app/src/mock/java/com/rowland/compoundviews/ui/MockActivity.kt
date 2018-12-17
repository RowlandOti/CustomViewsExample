package com.rowland.compoundviews.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity


/**
 * Created by Rowland on 12/15/2018.
 */
class MockActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layout)
    }

    companion object {
        var layout: Int = 0
    }
}