package com.kevinserrano.apps.memoviesapp

import android.app.Application
import com.google.android.gms.ads.MobileAds
import com.google.android.gms.ads.RequestConfiguration
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MeMoviesApp : Application() {

    companion object {
        lateinit var app: MeMoviesApp
    }

    override fun onCreate() {
        super.onCreate()
        app = this
        MobileAds.setRequestConfiguration(
            RequestConfiguration.Builder()
                .build())
    }

}