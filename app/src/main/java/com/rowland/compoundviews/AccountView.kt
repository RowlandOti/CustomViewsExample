package com.rowland.compoundviews

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import com.rowland.compoundviews.contract.IAccountViewContract
import com.rowland.customviews.R
import kotlinx.android.synthetic.main.view_account.view.*

/**
 * Created by Rowland on 12/13/2018.
 */
class AccountView(context: Context, attrs: AttributeSet) : LinearLayout(context, attrs), IAccountViewContract.IAccountView {

    private val presenter: IAccountViewContract.IPresenter by lazy {
        // simplicity first since this is not the focus
        (context.applicationContext as CustomViewApplication).component.accountViewPresenter()
    }

    init {
        View.inflate(context, R.layout.view_account, this)
    }

    override fun setName(name: String) {
        text_name.text = name
    }

    override fun setAvatar(imageResource: Int) {
        image_avatar.setImageResource(imageResource)
    }

    override fun setLoggedOutName() {
        text_name.setText(R.string.account_name_logged_out)
    }

    override fun setLoggedOutAvatar() {
        image_avatar.setImageResource(android.R.drawable.ic_delete)
    }

    override fun showActivationMissingBadge() {
        image_activated.visibility = View.VISIBLE
        image_activated.setImageResource(android.R.drawable.radiobutton_off_background)
    }

    override fun showActivationPendingBadge() {
        image_activated.visibility = View.VISIBLE
        image_activated.setImageResource(android.R.drawable.radiobutton_on_background)
    }

    override fun hideBadge() {
        image_activated.visibility = View.GONE
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        presenter.onViewAttached(this)
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        presenter.onViewDetached()
    }
}