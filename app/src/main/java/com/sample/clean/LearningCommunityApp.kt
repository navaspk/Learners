package com.sample.clean

import android.app.Application
import com.sample.core.util.PreferenceManager
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class LearningCommunityApp : Application() {

    @Inject
    lateinit var preferenceManager: PreferenceManager

    companion object {
        lateinit var instance: LearningCommunityApp
    }

    override fun onCreate() {
        super.onCreate()
        instance = this

    }
}