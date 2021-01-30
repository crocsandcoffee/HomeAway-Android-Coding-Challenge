package com.crocsandcoffee.seattleplacesearch.dagger

import javax.inject.Qualifier

/**
 * @author Omid
 *
 * Custom Dagger qualifiers
 */

@Qualifier
annotation class IoDispatcher

@Qualifier
annotation class MainDispatcher

@Qualifier
annotation class DefaultDispatcher

@Qualifier
annotation class AppContext

@Qualifier
annotation class MapsApiKey

@Qualifier
annotation class ClientID

@Qualifier
annotation class ClientSecret