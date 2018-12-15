package com.rowland.compoundviews.data

import com.rowland.compoundviews.Account
import com.rowland.compoundviews.contract.IAccountDataSource

/**
 * Created by Rowland on 12/15/2018.
 *
 * Our mock data source has the expected response as lateinit variable in a companion object,
 * which allows us to easily manipulate the response and thus trigger a state we're expecting.
 */
class MockAccountDataSource : IAccountDataSource {

    override fun getAccount() = account

    companion object {
        lateinit var account: Account
    }
}