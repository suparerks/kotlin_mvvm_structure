package com.example.myapplication.data.providers

import android.content.Context
import android.net.ConnectivityManager

interface NetworkProvider {
    fun isNetworkAvailable(): Boolean
}

class NetworkProviderImpl(private val context: Context) : NetworkProvider {

    override fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?
        val netInfo = connectivityManager?.activeNetworkInfo
        return netInfo != null && netInfo.isConnectedOrConnecting
    }
}