package com.rowland.compoundviews

import android.app.Application
import com.rowland.compoundviews.di.AppComponent
import com.rowland.compoundviews.di.DaggerAppComponent
import com.rowland.compoundviews.di.AccountDataSourceModule
import com.rowland.compoundviews.di.PresenterModule

/**
 * Created by Rowland on 12/15/2018.
 */
class CustomViewApplication : Application() {

    val component: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .presenterModule(PresenterModule())
                .accountDataSourceModule(AccountDataSourceModule())
                .build()
    }
}