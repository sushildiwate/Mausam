package com.sushil.mausam

import android.app.Application
import com.google.android.libraries.places.api.Places
import com.sushil.mausam.database.MausamDataBase
import com.sushil.mausam.network.network
import com.sushil.mausam.network.repository
import com.sushil.mausam.network.viewModel
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin


class MausamApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        instance = this
        startKoin {
            androidLogger()
            androidContext(this@MausamApplication)
            modules(listOf(network, repository, viewModel))
        }
    }

    fun getRoomDAO(): MausamDataBase {
        return MausamDataBase.getDatabase(this)
    }

    companion object {
        @get:Synchronized
        var instance: MausamApplication? = null
            private set
    }
}