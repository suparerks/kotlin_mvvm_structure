package com.example.myapplication.clazz

import android.os.SystemClock
import android.view.View

abstract class OnClickListener : View.OnClickListener {

    private var mLastClickTime: Long = 0
    abstract fun onClicked(v: View)
    override fun onClick(v: View) {
        val currentClickTime = SystemClock.uptimeMillis()
        val elapsedTime = currentClickTime - mLastClickTime
        mLastClickTime = currentClickTime

        if (elapsedTime <= MIN_CLICK_INTERVAL)
            return
        onClicked(v)
    }

    companion object {
        val MIN_CLICK_INTERVAL: Long = 600
    }

}