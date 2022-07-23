package com.example.myapplication.application

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import com.example.myapplication.di.initKoin
import com.example.myapplication.tools.Contextor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.franmontiel.persistentcookiejar.cache.SetCookieCache
import com.franmontiel.persistentcookiejar.persistence.SharedPrefsCookiePersistor

class app : Application() {

    companion object {
        private var cookieJar: PersistentCookieJar? = null
        private var instance: app? = null
        fun getInstance(): app {
            return instance!!
        }
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        Contextor.getInstance().init(this)
        initKoin(this)
    }

    fun getCookieJar(): PersistentCookieJar {
        if (cookieJar == null) {
            cookieJar = PersistentCookieJar(
                SetCookieCache(),
                SharedPrefsCookiePersistor(applicationContext)
            )
        }
        return cookieJar!!
    }
}