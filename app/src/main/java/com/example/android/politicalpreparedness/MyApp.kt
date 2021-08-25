package com.example.android.politicalpreparedness

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        /**
         * Use Koin Library as a service locator
         */
        val myModule = module {

        }

        startKoin {
            androidContext(this@MyApp)
            modules(myModule)
        }
    }
}