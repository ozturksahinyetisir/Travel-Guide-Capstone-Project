package com.ozturksahinyetisir.travelguideapp.data.di


import com.ozturksahinyetisir.travelguideapp.data.service.TravelAPIService
import com.ozturksahinyetisir.travelguideapp.utils.Constants.Companion.BASE_URL
import dagger.hilt.InstallIn
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@InstallIn(SingletonComponent::class)
@Module

class NetworkModule {

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory{
        return GsonConverterFactory.create()
    }

    @Provides
    @Singleton
    fun provideRetrofit(gsonConverterFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Provides
    @Singleton
    fun provideService(retrofit: Retrofit):TravelAPIService {
        return retrofit.create(TravelAPIService::class.java)
    }

}