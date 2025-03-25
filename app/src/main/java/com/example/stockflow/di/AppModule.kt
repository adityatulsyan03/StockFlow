package com.example.stockflow.di

import com.example.stockflow.BuildConfig
import com.example.stockflow.data.remote.BankApi
import com.example.stockflow.data.remote.BillsApi
import com.example.stockflow.data.remote.CategoryApi
import com.example.stockflow.data.remote.InventoryApi
import com.example.stockflow.data.remote.PartyApi
import com.example.stockflow.data.remote.ReportApi
import com.example.stockflow.data.remote.SellingUnitApi
import com.example.stockflow.data.remote.UserApi
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {

        // Logging Interceptor
        val httpLoggingInterceptor = HttpLoggingInterceptor()
            .apply {
                level = HttpLoggingInterceptor.Level.BODY
            }

        // HTTP Client with bearer token
        return OkHttpClient
            .Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        okHttpClient: OkHttpClient
    ): Retrofit =
        Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Singleton
    @Provides
    fun provideUserApi(retrofit: Retrofit): UserApi {
        return retrofit.create(UserApi::class.java)
    }

    @Singleton
    @Provides
    fun providePartyApi(retrofit: Retrofit): PartyApi {
        return retrofit.create(PartyApi::class.java)
    }

    @Singleton
    @Provides
    fun provideInventoryApi(retrofit: Retrofit): InventoryApi {
        return retrofit.create(InventoryApi::class.java)
    }

    @Singleton
    @Provides
    fun provideCategoryApi(retrofit: Retrofit): CategoryApi {
        return retrofit.create(CategoryApi::class.java)
    }

    @Singleton
    @Provides
    fun provideBankApi(retrofit: Retrofit): BankApi {
        return retrofit.create(BankApi::class.java)
    }

    @Singleton
    @Provides
    fun provideBillsApi(retrofit: Retrofit): BillsApi {
        return retrofit.create(BillsApi::class.java)
    }

    @Singleton
    @Provides
    fun provideReportApi(retrofit: Retrofit): ReportApi {
        return retrofit.create(ReportApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSellingUnitApi(retrofit: Retrofit): SellingUnitApi {
        return retrofit.create(SellingUnitApi::class.java)
    }

    @Provides
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

}