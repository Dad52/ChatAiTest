package com.ands.chataitest

import androidx.lifecycle.ViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Created by Dad52(Sobolev) on 7/2/2022.
 */

@Module
@InstallIn(ViewModelComponent::class)
class DataModule {

    private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    @Provides
    fun provideApiService(): ApiService {
        return Retrofit.Builder()
            .baseUrl("http://api.brainshop.ai/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

}