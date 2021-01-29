package com.crocsandcoffee.seattleplacesearch.dagger

import javax.inject.Qualifier

@Qualifier
annotation class IoDispatcher

@Qualifier
annotation class MainDispatcher

@Qualifier
annotation class DefaultDispatcher

@Qualifier
annotation class AppContext