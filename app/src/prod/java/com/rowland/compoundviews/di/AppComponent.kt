package com.rowland.compoundviews.di

import com.rowland.compoundviews.contract.IAccountViewContract
import dagger.Component
import javax.inject.Singleton

/**
 * Created by Rowland on 12/15/2018.
 */
@Singleton
@Component(modules = arrayOf(PresenterModule::class, AccountDataSourceModule::class))
interface AppComponent {
    fun accountViewPresenter(): IAccountViewContract.IPresenter
}