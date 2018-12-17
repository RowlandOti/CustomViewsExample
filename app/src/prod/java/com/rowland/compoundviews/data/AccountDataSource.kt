package com.rowland.compoundviews.data

import com.rowland.compoundviews.Account
import com.rowland.compoundviews.contract.IAccountDataSource

/**
 * For simplicity, our "data source" will just return the account set in the constructor.
 */
class AccountDataSource(val dumbAccount: Account): IAccountDataSource {

    override fun getAccount(): Account = dumbAccount
}