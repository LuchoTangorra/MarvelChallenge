package com.example.androidchallenge.DataSource.Module

import com.example.androidchallenge.Heroes.DataSource.HeroesViewModel
import com.example.androidchallenge.DataSource.API.MarvelAPI
import com.example.androidchallenge.Heroes.DataSource.MarvelRepository
import com.example.androidchallenge.Utils.Constants.marvelAPIurl
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val appModules = module {

    viewModel { HeroesViewModel(get()) }

    single { provideOkHttpClient() }
    single { provideRetrofit(get()) }

    single { provideMarvelAPI(get()) }
    single { MarvelRepository(get()) }
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl(marvelAPIurl)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient.Builder()
        .readTimeout(15, TimeUnit.SECONDS)
        .connectTimeout(15, TimeUnit.SECONDS)
        .build()
}

fun provideMarvelAPI(retrofit: Retrofit): MarvelAPI =
    retrofit.create(MarvelAPI::class.java)