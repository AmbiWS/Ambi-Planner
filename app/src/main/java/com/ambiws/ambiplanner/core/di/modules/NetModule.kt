package com.ambiws.ambiplanner.core.di.modules

import com.ambiws.ambiplanner.BuildConfig
import com.ambiws.ambiplanner.core.network.adapters.ErrorCallAdapterFactory
import com.ambiws.ambiplanner.core.network.adapters.ExceptionParser
import com.ambiws.ambiplanner.utils.providers.ResourceProvider
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class NetModule {

    private val isDebugBuild = BuildConfig.DEBUG

    @Provides
    fun provideGson(): Gson {
        return GsonBuilder().setLenient().serializeNulls().create()
    }

    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        return interceptor
    }

    @Provides
    fun provideErrorCallAdapterFactory(): ErrorCallAdapterFactory {
        return ErrorCallAdapterFactory()
    }

    @Provides
    fun provideExceptionParser(resourceProvider: ResourceProvider): ExceptionParser {
        return ExceptionParser(resourceProvider)
    }

    @Provides
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return if (isDebugBuild) {
            OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
        } else {
            OkHttpClient.Builder().build()
        }
    }

    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        callAdapterFactory: ErrorCallAdapterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(callAdapterFactory)
            .client(okHttpClient)
            .build()
    }
}
