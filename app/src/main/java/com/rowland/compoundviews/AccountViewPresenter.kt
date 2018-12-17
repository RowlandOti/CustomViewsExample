package com.rowland.compoundviews

import com.rowland.compoundviews.contract.IAccountDataSource
import com.rowland.compoundviews.contract.IAccountViewContract

/**
 * Created by Rowland on 12/15/2018.
 */
class AccountViewPresenter(private val accountDataSource: IAccountDataSource) : IAccountViewContract.IPresenter {

    private var view: IAccountViewContract.IAccountView? = null

    override fun onViewAttached(view: IAccountViewContract.IAccountView) {
        this.view = view
        val account = accountDataSource.getAccount()
        handleAccount(account)
    }

    override fun onViewDetached() {
        // tear down, cancel subscriptions, etc...
    }

    private fun handleAccount(account: Account) {
        when (account) {
            Account.LoggedOut -> handleLoggedOut()
            is Account.Active -> {
                view?.setName(account.name)
                view?.setAvatar(account.avatar)
                handleAccountActivation(account.activated)
            }
        }
    }

    private fun handleLoggedOut() {
        view?.hideBadge()
        view?.setLoggedOutName()
        view?.setLoggedOutAvatar()
    }

    private fun handleAccountActivation(activation: String) = when (activation) {
        "done" -> view?.hideBadge()
        "missing" -> view?.showActivationMissingBadge()
        "pending" -> view?.showActivationPendingBadge()
        else -> throw IllegalStateException()
    }
}