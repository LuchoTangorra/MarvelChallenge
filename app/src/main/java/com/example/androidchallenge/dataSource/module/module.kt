package com.example.androidchallenge.dataSource.module

import com.example.androidchallenge.heroes.dataSource.HeroesViewModel
import com.example.androidchallenge.dataSource.api.MarvelAPI
import com.example.androidchallenge.events.dataSource.EventsViewModel
import com.example.androidchallenge.heroDetails.dataSource.ComicsViewModel
import com.example.androidchallenge.heroes.dataSource.MarvelRepository
import com.example.androidchallenge.utils.Constants.marvelAPIurl
import okhttp3.OkHttpClient
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModules = module {

    viewModel { HeroesViewModel(get()) }
    viewModel { ComicsViewModel(get()) }
    viewModel { EventsViewModel(get()) }

    single { provideHttpClient() }
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

fun provideHttpClient(): OkHttpClient = OkHttpClient.Builder().build()

fun provideMarvelAPI(retrofit: Retrofit): MarvelAPI =
    retrofit.create(MarvelAPI::class.java)