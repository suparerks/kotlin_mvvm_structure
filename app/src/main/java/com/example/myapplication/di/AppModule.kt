package com.example.myapplication.di

import com.example.myapplication.application.app
import com.example.myapplication.data.api.BaseOkHttpClientBuilder
import com.example.myapplication.data.api.BaseOkHttpClientBuilderImpl
import com.example.myapplication.data.api.BaseRetrofitBuilder
import com.example.myapplication.data.providers.NetworkProvider
import com.example.myapplication.data.providers.NetworkProviderImpl
import com.example.myapplication.data.api.ApiTestInterface
import com.example.myapplication.data.api.interceptor.loggingInterceptor
import com.franmontiel.persistentcookiejar.PersistentCookieJar
import com.google.gson.Gson

import com.ihsanbal.logging.LoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.converter.gson.GsonConverterFactory

const val NAME_HOME_BASE_URL = "Home base URL"

private const val NAME_APP_GSON_DEPENDENCY = "App's Gson"
const val NAME_APP_GSON_CONVERTER_DEPENDENCY = "App's Gson Converter"
const val NAME_LOCALE = "Locale"

val appModule = module {

    factory(named(NAME_HOME_BASE_URL)) { "https://jsonplaceholder.typicode.com/" }

    factory(named(NAME_APP_GSON_CONVERTER_DEPENDENCY)) {
        GsonConverterFactory.create()
    }

    factory { app.getInstance()!!.getCookieJar() }

    factory { loggingInterceptor }

    single<NetworkProvider> {
        NetworkProviderImpl(context = get())
    }

    factory<BaseOkHttpClientBuilder> {
        BaseOkHttpClientBuilderImpl(
            baseUrl = get(named(NAME_HOME_BASE_URL)),
            cookieJar = get<PersistentCookieJar>(),
            logInterceptor = get<LoggingInterceptor>()
        )
    }

    single<ApiTestInterface> {
        BaseRetrofitBuilder(
            baseUrl = get(named(NAME_HOME_BASE_URL)),
            baseOkHttpClientBuilder = get(),
            converterFactory = get<GsonConverterFactory>(named(NAME_APP_GSON_CONVERTER_DEPENDENCY))
        ).build()
    }
}