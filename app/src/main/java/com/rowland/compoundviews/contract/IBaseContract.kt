package com.rowland.compoundviews.contract

import android.arch.lifecycle.Lifecycle


/**
 * Created by Rowland on 12/13/2018.
 */

class IBaseContract {
    interface IView {

    }

    interface IPresenter<V : IView> {

    }
}