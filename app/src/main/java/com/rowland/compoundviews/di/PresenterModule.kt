package com.rowland.compoundviews.di

import com.rowland.compoundviews.AccountViewPresenter
import com.rowland.compoundviews.contract.IAccountDataSource
import com.rowland.compoundviews.contract.IAccountViewContract
import dagger.Module
import dagger.Provides

/**
 * Created by Rowland on 12/15/2018.
 */

@Module
class PresenterModule {
    @Provides
    fun providesAccountViewPresenter(dataSource: IAccountDataSource): IAccountViewContract.IPresenter = AccountViewPresenter(dataSource)
}