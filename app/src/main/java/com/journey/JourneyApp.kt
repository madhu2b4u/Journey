package com.journey

import android.app.Application
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class JourneyApp : Application() {

    var mDataStore: DataStore<Preferences>? = null

    override fun onCreate() {
        super.onCreate()
    }

    fun getDataStore(): DataStore<Preferences>? {
        return mDataStore
    }

}