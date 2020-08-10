package com.example.androidchallenge

import android.app.Application
import com.example.androidchallenge.DataSource.Module.appModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

@Suppress("unused")
class MarvelApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MarvelApplication)
            modules(appModules)
        }
    }
}