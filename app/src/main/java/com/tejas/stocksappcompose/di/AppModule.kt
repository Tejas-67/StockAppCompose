package com.tejas.stocksappcompose.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.tejas.stocksappcompose.data.local.StockDatabase
import com.tejas.stocksappcompose.data.remote.StockApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStockApi(
        client: OkHttpClient
    ): StockApi {
        return Retrofit
            .Builder()
            .baseUrl(StockApi.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .client(client)
            .build()
            .create(StockApi::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpClient(
        interceptor: HttpLoggingInterceptor
    ): OkHttpClient{
        val client = OkHttpClient.Builder()
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .addInterceptor(interceptor)
            .build()
        return client
    }

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor{
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        return interceptor
    }

    @Provides
    @Singleton
    fun provideStockDatabase(
        app: Application
    ): StockDatabase {
        return Room.databaseBuilder(
            app,
            StockDatabase::class.java,
            "stock_db"
        ).allowMainThreadQueries()
            .build()
    }
}