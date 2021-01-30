package com.crocsandcoffee.seattleplacesearch.dagger

import com.crocsandcoffee.seattleplacesearch.main.api.FourSquareService
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

const val BASE_URL = "https://api.foursquare.com/v2/"

/**
 * @author Omid
 *
 * Network configuration + dependencies
 */
@Module
object NetworkingModule {

    @Singleton
    @Provides
    fun provideFourSquareService(retrofit: Retrofit): FourSquareService {
        return retrofit.create(FourSquareService::class.java)
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        moshiConverterFactory: MoshiConverterFactory
    ): Retrofit {

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(moshiConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BASIC })
            .build()
    }

    @Singleton
    @Provides
    fun provideMoshi(): Moshi {
        return Moshi
            .Builder()
            .build()
    }

    /**
     * Return the converter factory which uses Moshi for JSON
     *
     * Note: Moshi's adapters are ordered by precedence, any custom adapters added should
     * be added before the kotlin reflection adapter [KotlinJsonAdapterFactory] to ensure
     * they are called when converting kotlin classes to and from JSON.
     *
     */
    @Singleton
    @Provides
    fun provideMoshiConverterFactory(moshi: Moshi): MoshiConverterFactory {
        return MoshiConverterFactory.create(moshi)
    }
}