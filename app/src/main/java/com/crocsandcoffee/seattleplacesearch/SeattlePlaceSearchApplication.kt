package com.crocsandcoffee.seattleplacesearch

import androidx.multidex.MultiDexApplication
import com.crocsandcoffee.seattleplacesearch.dagger.ApplicationComponent
import com.crocsandcoffee.seattleplacesearch.dagger.DaggerApplicationComponent

class SeattlePlaceSearchApplication : MultiDexApplication() {

    lateinit var appComponent: ApplicationComponent

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerApplicationComponent.factory().create(applicationContext)
    }
}