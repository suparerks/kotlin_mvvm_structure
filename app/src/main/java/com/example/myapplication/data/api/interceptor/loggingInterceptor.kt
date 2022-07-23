package com.example.myapplication.data.api.interceptor

import com.example.myapplication.BuildConfig
import com.ihsanbal.logging.Level
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.internal.platform.Platform

val loggingInterceptor = LoggingInterceptor.Builder()
    //.loggable(false)
    //.loggable(BuildConfig.FLAVOR == "DEV" || ReadFileUtils.readFileDevice())
    .loggable(BuildConfig.DEBUG)
    .setLevel(Level.BASIC)
    .log(Platform.INFO)
////    .logger { _, _, msg ->
////        LogManager.api<BaseRetrofitBuilder>("$msg")
////    }
    .request("MyApp_LogRequest")
    .response("MyApp_LogResponse")
    .addHeader("version", BuildConfig.VERSION_NAME)
    .build()