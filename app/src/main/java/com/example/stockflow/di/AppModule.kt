package com.example.stockflow.di

import com.example.stockflow.common.Constants
import com.example.stockflow.data.remote.AuthApi
import com.example.stockflow.data.remote.BankApi
import com.example.stockflow.data.remote.BillItemApi
import com.example.stockflow.data.remote.BillsApi
import com.example.stockflow.data.remote.CategoryApi
import com.example.stockflow.data.remote.DayBookReportApi
import com.example.stockflow.data.remote.InventoryApi
import com.example.stockflow.data.remote.MoneyReportApi
import com.example.stockflow.data.remote.PartyApi
import com.example.stockflow.data.remote.SellingUnitApi
import com.example.stockflow.data.remote.StockSummaryApi
import com.example.stockflow.data.remote.TransactionReportApi
import com.example.stockflow.data.remote.UserApi
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
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideAuthApi(retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

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
    fun provideBillItemApi(retrofit: Retrofit): BillItemApi {
        return retrofit.create(BillItemApi::class.java)
    }

    @Singleton
    @Provides
    fun provideDayBookReportApi(retrofit: Retrofit): DayBookReportApi{
        return retrofit.create(DayBookReportApi::class.java)
    }

    @Singleton
    @Provides
    fun provideMoneyReportApi(retrofit: Retrofit): MoneyReportApi {
        return retrofit.create(MoneyReportApi::class.java)
    }

    @Singleton
    @Provides
    fun provideSellingUnitApi(retrofit: Retrofit): SellingUnitApi {
        return retrofit.create(SellingUnitApi::class.java)
    }

    @Singleton
    @Provides
    fun provideStockSummaryApi(retrofit: Retrofit): StockSummaryApi {
        return retrofit.create(StockSummaryApi::class.java)
    }

    @Singleton
    @Provides
    fun provideTransactionReportApi(retrofit: Retrofit): TransactionReportApi {
        return retrofit.create(TransactionReportApi::class.java)
    }

}