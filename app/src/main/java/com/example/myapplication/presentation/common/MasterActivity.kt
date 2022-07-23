package com.example.myapplication.presentation.common


import android.app.Activity
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.myapplication.R
import com.example.myapplication.clazz.OnClickListener
import com.example.myapplication.utils.DialogUtils
import com.example.myapplication.utils.ProgressDialog
import com.example.myapplication.utils.UserInteractionAwareCallback
import kotlinx.android.synthetic.main.app_bar.*

open class MasterActivity : AppCompatActivity() {

    private var isBack: Boolean = true
    private var isNavbar: Boolean = true
    private var isLogout: Boolean = true
    private var isLogoutActivated: Boolean = false
    private val STATE_LOGOUT = "is_logout_activated"

    private var textTitle: TextView? = null

    private var progress: Dialog? = null
    private var dialogMsg: AlertDialog? = null
    private var activity: Activity? = null

    private var isCanSignoff: Boolean = true


    private var customTimeOut: Long = 30000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            isLogoutActivated = savedInstanceState.getBoolean(STATE_LOGOUT, false)
        }
        DialogUtils.dialogMsg = null
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(STATE_LOGOUT, isLogoutActivated)
    }

    fun setActivityTimeOut(timeout: Long) {
        customTimeOut = timeout
    }

    fun setButtonLogout(isSet: Boolean) {
        isLogout = isSet
    }

    fun initMaster() {
        if (isBack) {
            icBack.visibility = View.VISIBLE
            icBack.setOnClickListener(object : OnClickListener() {
                override fun onClicked(v: View) {
                    onBackPressed()
                }
            })
        } else {
            icBack.visibility = View.GONE
        }
    }

    fun initDialog(_activity: Activity) {
        activity = _activity
        progress = ProgressDialog.progressDialog(activity!!)
    }

    fun initDialog(_activity: Activity, textStatus: String) {
        activity = _activity
        progress = ProgressDialog.progressDialog(activity!!, textStatus)
    }

    fun getDevice(): Boolean {
        return true
    }

    fun setLogoAllOnline(value: Boolean) {
        if (value) {
            logoAllOnline.visibility = View.VISIBLE
        }
    }

    fun setLogoAllMemberVisible(value: Boolean) {
        if (value) {
            logoAllMember.visibility = View.VISIBLE
        }
    }

    fun setTitle(titleName: String) {
        if (textTitle != null) {
            return
        }

        textTitle = textTitleLeft
        textTitleLeft!!.visibility = View.VISIBLE
        textTitleCenter!!.visibility = View.GONE
        textTitleRight!!.visibility = View.GONE
        textTitle!!.text = titleName
    }

    fun setBackButtonVisible(value: Boolean) {
        isBack = value
    }

    fun setTitle(
        titleName: String,
        align: Paint.Align,
        color: Int = ContextCompat.getColor(this, R.color.colorDarkLight),
        backgroundColor: Int? = null
    ) {
        if (textTitle != null) {
            return
        }

        textTitleLeft.visibility = View.GONE
        textTitleCenter.visibility = View.GONE
        textTitleRight.visibility = View.GONE
        when (align) {
            Paint.Align.LEFT -> {
                textTitle = textTitleLeft
                textTitleLeft.visibility = View.VISIBLE
            }
            Paint.Align.CENTER -> {
                textTitle = textTitleCenter
                textTitleCenter.visibility = View.VISIBLE
            }
            Paint.Align.RIGHT -> {
                textTitle = textTitleRight
                textTitleRight.visibility = View.VISIBLE
            }
        }

        if (backgroundColor != null) {
            toolbar.setBackgroundResource(backgroundColor)
        }

        textTitle!!.text = titleName
        textTitle!!.setTextColor(color)
    }

    fun nextPage(intent: Intent) {
        nextPage(intent, null)
    }

    fun nextPage(intent: Intent, subtitle: String?) {
        startActivity(intent)
    }

    fun nextPage(clazz: Class<*>) {
        nextPage(clazz, null)
    }

    fun nextPage(clazz: Class<*>, subtitle: String?) {
        val intent = Intent(this@MasterActivity, clazz)
        startActivity(intent)
    }

    fun nextPageForResult(intent: Intent, requestCode: Int) {
        nextPageForResult(intent, requestCode, null)
    }

    fun nextPageForResult(intent: Intent, requestCode: Int, subtitle: String?) {

        startActivityForResult(intent, requestCode)
    }

    fun nextPageForResult(clazz: Class<*>, requestCode: Int) {
        nextPageForResult(clazz, requestCode, null)
    }

    fun nextPageForResult(clazz: Class<*>, requestCode: Int, subtitle: String?) {
        var intent = Intent(this@MasterActivity, clazz)
        startActivityForResult(intent, requestCode)
    }

    open fun onSettingButton() {}

    open fun onBackButton() {}

    open fun onSignoffButton() {}

    open fun onAutoSignoff() {}

    open fun onAutoSignoffCloseDialog() {}

    fun dismissDialog() {
        if (dialogMsg != null && dialogMsg!!.isShowing) {
            dialogMsg?.dismiss()
        }
    }

    private fun initDialogMaster() {
        if (activity == null) {
            initDialog(this@MasterActivity)
        }
    }

    fun initDialogMaster(_activity: Activity) {
        if (activity == null) {
            initDialog(_activity)
        }
    }

    fun setAutoSignoff(isSignoff: Boolean) {
        isCanSignoff = isSignoff
    }

    private fun canSignoff(): Boolean {
        return isCanSignoff
    }

    fun isLoading(): Boolean {
        return progress?.isShowing != false
    }

    fun showLoading() {
        if (progress?.isShowing == false)
            progress?.show()
    }

    fun closeLoading() {
        if (progress?.isShowing == true)
            progress?.dismiss()
    }

    private fun setDialogHideNavBar(dialog: Dialog?) {
        if (!isNavbar && dialog != null) {
            setWindowUIVisibility(dialog.window)
        }
    }

    private fun hideNavbar() {
        if (!isNavbar) {
            setWindowUIVisibility(window)
        }
    }

    private fun setWindowUIVisibility(window: Window?) {
        window!!.decorView.apply {
            systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
        }
    }

    fun showDialogMsgSuccess(
        msg: String,
        delayTime: Long = 1500L,
        fn: (() -> Unit?)? = null
    ) {
        DialogUtils.showDialogMsgSuccessAutoClose(
            this@MasterActivity,
            activity!!,
            msg,
            delayTime,
            fn
        )
    }

    fun showDialogMsgSuccess(
        title: String?,
        msg: String,
        btnText: String,
        listenerOk: DialogInterface.OnClickListener
    ) {
        DialogUtils.showDialogMsgSuccess(
            this,
            this@MasterActivity,
            title,
            msg,
            btnText,
            listenerOk
        )
//        dialogMsg = createDialogMsg(
//            R.drawable.ic_success,
//            title, msg, btnText, null, listenerOk, null
//        )
//        setDialogHideNavBar(dialogMsg)
//        dialogMsg?.show()
    }

    fun showDialogMsgError(
        title: String?,
        msg: String,
        btnText: String,
        listenerOk: DialogInterface.OnClickListener
    ) {
        DialogUtils.showDialogMsgError(
            this@MasterActivity,
            activity!!,
            title,
            msg,
            btnText,
            listenerOk
        )
//        dialogMsg = createDialogMsg(
//            R.drawable.ic_error,
//            title, msg, btnText, null, listenerOk, null
//        )
//        setDialogHideNavBar(dialogMsg)
//        dialogMsg?.show()
    }

    fun showDialogMsgWarning(
        title: String?,
        msg: String,
        btnText: String,
        listenerOk: DialogInterface.OnClickListener
    ) {
        DialogUtils.showDialogMsgWarning(
            this@MasterActivity,
            activity!!,
            title,
            msg,
            btnText,
            listenerOk
        )

//        dialogMsg = createDialogMsg(
//            R.drawable.ic_warning2,
//            title, msg, btnText, null, listenerOk, null
//        )
//        setDialogHideNavBar(dialogMsg)
//        dialogMsg?.show()
    }

    fun showDialogMsgWarning(
        msg: String,
        delayTime: Long = 1500L
    ) {
        DialogUtils.showDialogMsgWarningAutoClose(
            this,
            this@MasterActivity,
            msg,
            delayTime
        )
    }

    fun showDialogRetry(
        title: String?,
        msg: String,
        btnOkText: String,
        btnCancelText: String,
        listenerOk: DialogInterface.OnClickListener,
        listenerCancel: DialogInterface.OnClickListener,
        defaultFocus: Int = AlertDialog.BUTTON_NEGATIVE
    ) {
        DialogUtils.showDialogRetry(
            this@MasterActivity,
            activity!!,
            title,
            msg,
            btnOkText,
            btnCancelText,
            listenerOk,
            listenerCancel,
            defaultFocus
        )
    }


    private fun createDialogMsg(
        imgicon: Int?,
        title: String?,
        msg: String,
        btnOkText: String,
        btnCancelText: String?,
        listenerOk: DialogInterface.OnClickListener?,
        listenerCancel: DialogInterface.OnClickListener?,
        defaultFocus: Int = AlertDialog.BUTTON_NEGATIVE
    ): AlertDialog? {
        var dialog: AlertDialog? = null
        var customView = getCustomViewDialog()
        val builder = AlertDialog.Builder(activity!!)
        builder.setCancelable(false)
            .setView(customView)

        var imgIcon = customView.findViewById<ImageView>(R.id.ic_message)
        var txtTitle = customView.findViewById<TextView>(R.id.txtTitle)
        var txtContent = customView.findViewById<TextView>(R.id.txtContent)
        var btnOk = customView.findViewById<Button>(R.id.btnOk)
        var btnCancel = customView.findViewById<Button>(R.id.btnCancel)
        var layoutIcon = customView.findViewById<LinearLayout>(R.id.layoutIcon)

        if (imgicon != null) {
            imgIcon?.setImageResource(imgicon!!)
        } else {
            layoutIcon.visibility = View.GONE
        }

//        if(imgicon != null && title.isNullOrEmpty()){
//
//        }

        if (title.isNullOrEmpty().not()) {
            txtTitle?.text = title
        } else {
            txtTitle?.visibility = View.GONE
        }

        txtContent?.text = msg
        btnOk?.text = btnOkText

        if (btnCancelText == null) {
            btnCancel?.visibility = View.GONE
        } else {
            btnCancel?.text = btnCancelText
            btnCancel?.visibility = View.VISIBLE
        }

        btnOk?.setOnClickListener {
            if (listenerOk == null) {
                dialog?.dismiss()
            } else {
                listenerOk.onClick(dialogMsg, 0)
            }
        }
        btnOk?.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_UP) {
                btnOk?.performClick()
            }
            true
        }

        if (listenerCancel == null) {
            dialog?.dismiss()
        } else {
            btnCancel?.setOnClickListener { listenerCancel.onClick(dialogMsg, 0) }
            btnCancel?.setOnTouchListener { v, event ->
                if (event.action == MotionEvent.ACTION_UP) {
                    btnCancel?.performClick()
                }
                true
            }
        }

        if (btnCancelText == null) {
            btnOk?.requestFocus()
        } else {
            if (defaultFocus == AlertDialog.BUTTON_POSITIVE) {
                btnOk?.requestFocus()
            } else {
                btnCancel?.requestFocus()
            }
        }

        dialog = builder.create()
        dialog.window!!.callback = UserInteractionAwareCallback(
            dialog.window!!.callback,
            this@MasterActivity
        )
        return dialog
    }

    private fun getCustomViewDialog(): View {
        var factory = LayoutInflater.from(activity)
        return factory.inflate(R.layout.dialog_msg_default, null)
    }

}