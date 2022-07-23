package com.example.myapplication.data.api

import com.example.myapplication.BuildConfig
import com.ihsanbal.logging.LoggingInterceptor
import okhttp3.CookieJar
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import java.net.URL
import java.util.concurrent.TimeUnit

const val HTTP_TIMEOUT = 60L

interface BaseOkHttpClientBuilder {
    //TODO: shouldEnableTrustManager flag will be removed soon (when proxy api will be finished)
    fun build(
        interceptors: Array<out Interceptor>,
        shouldEnableTrustManager: Boolean = false
    ): OkHttpClient
}

class BaseOkHttpClientBuilderImpl(
    private val baseUrl: String,
    private val cookieJar: CookieJar,
    private val logInterceptor: LoggingInterceptor
) : BaseOkHttpClientBuilder {

    override fun build(
        interceptors: Array<out Interceptor>,
        shouldEnableTrustManager: Boolean
    ): OkHttpClient {

        val url = URL(baseUrl)
        val serverHostname = url.host

//        if (shouldEnableTrustManager) {
//            val connection = url.openConnection() as HttpsURLConnection
//            connection.sslSocketFactory = trustKit.getSSLSocketFactory(serverHostname)
//        }

        return OkHttpClient.Builder()
            .readTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(HTTP_TIMEOUT, TimeUnit.SECONDS)
            .cookieJar(cookieJar)
            .apply {
                interceptors.forEach {
                    addInterceptor(it)
                }
            }
            .apply {
                addInterceptor(logInterceptor)
                if (BuildConfig.DEBUG) {
                    val logging = HttpLoggingInterceptor()
                    logging.level = HttpLoggingInterceptor.Level.BODY
                    addInterceptor(logging)
                }
//                if (shouldEnableTrustManager) {
//                    sslSocketFactory(trustKit.getSSLSocketFactory(serverHostname),
//                        trustKit.getTrustManager(serverHostname))
//                }
            }
            .build()
    }
}