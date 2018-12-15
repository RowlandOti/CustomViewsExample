package com.rowland.compoundviews.contract

import com.rowland.compoundviews.Account

/**
 * Created by Rowland on 12/15/2018.
 */
interface IAccountDataSource {
    fun getAccount(): Account
}