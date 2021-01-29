package com.crocsandcoffee.seattleplacesearch.dagger

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

/**
 *
 * @author Omid
 *
 * Main Dagger component for the application.
 *
 */
@Singleton
@Component(
    modules = [
        AppModule::class,
        NetworkingModule::class
    ])
interface ApplicationComponent  {

    // Factory to create instances of the AppComponent
    @Component.Factory
    interface Factory {
        // With @BindsInstance, the Context passed in will be available in the graph
        fun create(@BindsInstance context: Context): ApplicationComponent

        // injectable activities, fragments, views, etc
    }
}