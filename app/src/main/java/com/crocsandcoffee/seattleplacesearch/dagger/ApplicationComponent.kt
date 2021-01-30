package com.crocsandcoffee.seattleplacesearch.dagger

import android.content.Context
import com.crocsandcoffee.seattleplacesearch.main.ui.activity.MainActivity
import com.crocsandcoffee.seattleplacesearch.main.ui.fragment.DetailsFragment
import com.crocsandcoffee.seattleplacesearch.main.ui.fragment.SearchFragment
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
    }

    // injectable activities
    fun inject(activity: MainActivity)

    // injectable fragments
    fun inject(fragment: SearchFragment)
    fun inject(fragment: DetailsFragment)
}