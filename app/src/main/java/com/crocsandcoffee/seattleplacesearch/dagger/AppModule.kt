package com.crocsandcoffee.seattleplacesearch.dagger

import android.content.Context
import android.content.pm.PackageManager
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

/**
 * @author Omid
 *
 * App wide dependencies
 */
@Module
object AppModule {

    @Provides
    @Singleton
    @ClientSecret
    fun provideClientSecret(context: Context): String {
        return context
            .packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            .metaData
            .run {
                getString("com.crocsandcoffee.foursquare.CLIENT_SECRET", "")
            }
    }

    @Provides
    @Singleton
    @ClientID
    fun provideClientKey(context: Context): String {
        return context
            .packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            .metaData
            .run {
                getString("com.crocsandcoffee.foursquare.CLIENT_ID", "")
            }
    }

    @Provides
    @Singleton
    @MapsApiKey
    fun provideMapsApiKey(context: Context): String {
        return context
            .packageManager
            .getApplicationInfo(context.packageName, PackageManager.GET_META_DATA)
            .metaData
            .run {
                getString("com.google.android.geo.API_KEY", "")
            }
    }

    @Provides
    @Singleton
    @AppContext
    fun provideContext(context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    @IoDispatcher
    fun provideIoDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    @DefaultDispatcher
    fun provideDefaultDispatcher(): CoroutineDispatcher {
        return Dispatchers.Default
    }

    @Provides
    @Singleton
    @MainDispatcher
    fun provideMainDispatcher(): CoroutineDispatcher {
        return Dispatchers.Main
    }
}