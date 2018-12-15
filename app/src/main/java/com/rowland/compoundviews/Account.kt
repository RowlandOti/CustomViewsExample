package com.rowland.compoundviews

/**
 * Created by Rowland on 12/13/2018.
 */
sealed class Account {
    object LoggedOut: Account()
    data class Active(val name: String, val avatar: Int, val activated: String): Account()
}