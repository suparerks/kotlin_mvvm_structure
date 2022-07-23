package com.example.myapplication.data.api

import okhttp3.Interceptor
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit

class BaseRetrofitBuilder(
    val baseUrl: String,
    val baseOkHttpClientBuilder: BaseOkHttpClientBuilder,
    val converterFactory: Converter.Factory,
    val callAdapterFactory: CallAdapter.Factory? = null
) {

    //TODO: shouldEnableTrustManager flag will be removed soon (when proxy api will be finished)
    inline fun <reified T> build(
        vararg interceptor: Interceptor,
        shouldEnableTrustManager: Boolean = false
    ): T {
        val builder = Retrofit.Builder()

        builder.addConverterFactory(converterFactory)
            .client(baseOkHttpClientBuilder.build(interceptor, shouldEnableTrustManager))
            .baseUrl(baseUrl)
        if (callAdapterFactory != null) {
            builder.addCallAdapterFactory(callAdapterFactory)
        }
        return builder.build()
            .create(T::class.java)
    }
}