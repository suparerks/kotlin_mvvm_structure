package com.example.myapplication.utils

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.example.myapplication.R

class DialogUtils {


    companion object {
        var dialogMsg: AlertDialog? = null

        fun dismissDialog() {
            if (dialogMsg != null && dialogMsg!!.isShowing) {
                dialogMsg?.dismiss()
                dialogMsg = null
            }
        }

        fun showDialogMsgSuccessAutoClose(
            context: Context,
            activity: Activity,
            msg: String,
            delayTime: Long = 1500L,
            fn: (() -> Unit?)? = null
        ) {
            dialogMsg = createDialogMsg(
                R.drawable.ic_success, msg, context = context, activity = activity
            )
            dialogMsg?.show()

            val handler = android.os.Handler()
            val runnable = Runnable {
                if (dialogMsg!!.isShowing) {
                    dialogMsg!!.dismiss()
                }
                if (fn != null) {
                    fn()
                }
            }

            dialogMsg!!.setOnDismissListener {
                handler.removeCallbacks(
                    runnable
                )
                if (fn != null) {
                    fn()
                }
            }

            handler.postDelayed(runnable, delayTime)
        }

        fun showDialogMsgWarningAutoClose(
            context: Context,
            activity: Activity,
            msg: String,
            delayTime: Long = 1500L
        ) {
            dialogMsg = createDialogMsg(
                R.drawable.ic_warning, msg, context = context, activity = activity
            )
            dialogMsg?.show()

            val handler = android.os.Handler()
            val runnable = Runnable {
                if (dialogMsg!!.isShowing) {
                    dialogMsg!!.dismiss()
                    dialogMsg = null
                }
            }

            dialogMsg!!.setOnDismissListener {
                handler.removeCallbacks(
                    runnable
                )
            }

            handler.postDelayed(runnable, delayTime)
        }

        fun showDialogMsgSuccess(
            context: Context,
            activity: Activity,
            title: String?,
            msg: String,
            btnText: String,
            listenerOk: DialogInterface.OnClickListener,
            delayTime: Int? = null
        ) {
            dialogMsg = createDialogMsg(
                R.drawable.ic_success,
                title, msg, btnText, null, listenerOk, null, context = context, activity = activity
            )
            dialogMsg?.show()
        }

        fun showDialogMsgError(
            context: Context,
            activity: Activity,
            title: String?,
            msg: String,
            btnText: String,
            listenerOk: DialogInterface.OnClickListener
        ) {
            dialogMsg = createDialogMsg(
                R.drawable.ic_error,
                title, msg, btnText, null, listenerOk, null, context = context, activity = activity
            )
            dialogMsg?.show()
        }

        fun showDialogMsgWarning(
            context: Context,
            activity: Activity,
            title: String?,
            msg: String,
            btnText: String,
            listenerOk: DialogInterface.OnClickListener
        ) {
            dialogMsg = createDialogMsg(
                R.drawable.ic_warning,
                title, msg, btnText, null, listenerOk, null, context = context, activity = activity
            )
            dialogMsg?.show()
        }


        fun showDialogRetry(
            context: Context,
            activity: Activity,
            title: String?,
            msg: String,
            btnOkText: String,
            btnCancelText: String,
            listenerOk: DialogInterface.OnClickListener,
            listenerCancel: DialogInterface.OnClickListener,
            defaultFocus: Int = AlertDialog.BUTTON_NEGATIVE
        ) {
            dialogMsg = createDialogMsg(
                null,
                title,
                msg,
                btnOkText,
                btnCancelText,
                listenerOk,
                listenerCancel,
                defaultFocus,
                context = context,
                activity = activity
            )
            dialogMsg?.show()
        }

        private fun createDialogMsg(
            imgicon: Int?,
            msg: String,
            context: Context,
            activity: Activity
        ): AlertDialog? {
            var dialog: AlertDialog? = null
            var customView = getCustomViewDialog(activity, R.layout.dialog_msg_auto_close)
            val builder = AlertDialog.Builder(activity)
            builder.setView(customView)

            var imgIcon = customView.findViewById<ImageView>(R.id.ic_message)
            var txtContent = customView.findViewById<TextView>(R.id.txtContent)
            var layoutIcon = customView.findViewById<LinearLayout>(R.id.layoutIcon)


            if (imgicon != null) {
                imgIcon?.setImageResource(imgicon!!)
            } else {
                layoutIcon.visibility = View.GONE
            }

            txtContent?.text = msg

            dialog = builder.create()
            dialog.window!!.callback = UserInteractionAwareCallback(
                dialog.window!!.callback,
                context as Activity
            )
            return dialog
        }

        private fun createDialogMsg(
            imgicon: Int?,
            title: String?,
            msg: String,
            btnOkText: String,
            btnCancelText: String?,
            listenerOk: DialogInterface.OnClickListener?,
            listenerCancel: DialogInterface.OnClickListener?,
            defaultFocus: Int = AlertDialog.BUTTON_NEGATIVE,
            context: Context,
            activity: Activity
        ): AlertDialog? {
            var dialog: AlertDialog? = null
            var customView = getCustomViewDialog(activity)
            val builder = AlertDialog.Builder(activity)
            builder.setCancelable(false)
                .setView(customView)

            var layoutTitle = customView.findViewById<LinearLayout>(R.id.layoutTitle)
            var imgIcon = customView.findViewById<ImageView>(R.id.ic_message)
            var txtTitle = customView.findViewById<TextView>(R.id.txtTitle)
            var txtContent = customView.findViewById<TextView>(R.id.txtContent)
            var btnOk = customView.findViewById<Button>(R.id.btnOk)
            var btnCancel = customView.findViewById<Button>(R.id.btnCancel)
            var layoutIcon = customView.findViewById<LinearLayout>(R.id.layoutIcon)


            if (imgicon != null) {
                imgIcon?.setImageResource(imgicon!!)

                when (imgicon) {
                    R.drawable.ic_success -> {
                        layoutTitle.setBackgroundResource(R.color.colorGreen)
                    }
                    R.drawable.ic_warning -> {
                        layoutTitle.setBackgroundResource(R.color.colorYellow)
                    }
                    R.drawable.ic_error -> {
                        layoutTitle.setBackgroundResource(R.color.colorRed)
                    }
                }

            } else {
                layoutIcon.visibility = View.GONE
            }

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
//            btnOk?.setOnTouchListener { v, event ->
//                if (event.action == MotionEvent.ACTION_UP) {
//                    btnOk?.performClick()
//                }
//                true
//            }

            if (listenerCancel == null) {
                dialog?.dismiss()
            } else {
                btnCancel?.setOnClickListener {
                    listenerCancel.onClick(dialogMsg, 0)
                }
//                btnCancel?.setOnTouchListener { v, event ->
//                    if (event.action == MotionEvent.ACTION_UP) {
//                        btnCancel?.performClick()
//                    }
//                    true
//                }
            }

//            if (btnCancelText == null) {
//                btnOk?.requestFocus()
//            } else {
//                if (defaultFocus == AlertDialog.BUTTON_POSITIVE) {
//                    btnOk?.requestFocus()
//                } else {
//                    btnCancel?.requestFocus()
//                }
//            }

            dialog = builder.create()
            dialog.window!!.setCallback(
                UserInteractionAwareCallback(
                    dialog.window!!.callback,
                    context as Activity
                )
            )
            return dialog
        }

        private fun getCustomViewDialog(activity: Activity, layout: Int): View {
            var factory = LayoutInflater.from(activity)
            return factory.inflate(layout, null)
        }

        private fun getCustomViewDialog(activity: Activity): View {
            var factory = LayoutInflater.from(activity)
            return factory.inflate(R.layout.dialog_msg_default, null)
        }
    }
}