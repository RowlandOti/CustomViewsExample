package com.rowland.compoundviews.di

import com.rowland.compoundviews.contract.IAccountDataSource
import com.rowland.compoundviews.data.MockAccountDataSource
import dagger.Module
import dagger.Provides

/**
 * Created by Rowland on 12/15/2018.
 */

@Module
class AccountDataSourceModule {
    @Provides
    fun providesMockAccountDataSource(): IAccountDataSource = MockAccountDataSource()
}