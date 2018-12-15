package com.rowland.compoundviews.contract

/**
 * Created by Rowland on 12/15/2018.
 */
interface IAccountViewContract {
    interface IAccountView {
        fun setLoggedOutName()
        fun setLoggedOutAvatar()
        fun setName(name: String)
        fun setAvatar(imageResource: Int)
        fun showActivationMissingBadge()
        fun showActivationPendingBadge()
        fun hideBadge()
    }

    interface IPresenter: IBaseContract.IPresenter<IBaseContract.IView> {
        fun onViewAttached(view: IAccountView)
        fun onViewDetached()
    }
}