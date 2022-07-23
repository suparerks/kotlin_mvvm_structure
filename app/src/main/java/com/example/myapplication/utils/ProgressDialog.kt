package com.example.myapplication.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.TextView
import com.example.myapplication.R

class ProgressDialog {
    companion object {

        fun progressDialog(context: Context): Dialog {
            return progressDialog(context,null)
        }

        fun progressDialog(context: Context, textStatus: String?): Dialog {

            val dialog = Dialog(context)
            val inflate = LayoutInflater.from(context).inflate(R.layout.dialog_loading, null)

            if(!textStatus.isNullOrEmpty()) {
                inflate.findViewById<TextView>(R.id.txtStatus).visibility = View.VISIBLE
                inflate.findViewById<TextView>(R.id.txtStatus).text = textStatus
            }else{
                inflate.findViewById<TextView>(R.id.txtStatus).visibility = View.GONE
            }

            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialog.setContentView(inflate)
            dialog.setCancelable(false)
            dialog.window!!.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT)
            dialog.window!!.setBackgroundDrawable(
                ColorDrawable(Color.TRANSPARENT)
            )
            return dialog
        }
    }
}