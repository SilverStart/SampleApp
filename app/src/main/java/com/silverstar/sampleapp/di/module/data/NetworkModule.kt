package com.silverstar.sampleapp.di.module.data

import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import io.reactivex.schedulers.Schedulers
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
object NetworkModule {

    @Provides
    @JvmStatic
    @Singleton
    @IntoMap
    @StringKey(RX_FACTORY)
    fun provideRxFactory(): CallAdapter.Factory {
        return RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io())
    }

    @Provides
    @JvmStatic
    @Singleton
    @IntoMap
    @StringKey(GSON_CONVERTER)
    fun provideGsonConverter(): Converter.Factory {
        return GsonConverterFactory.create(GsonBuilder().create())
    }

    @Singleton
    @Provides
    fun provideRetrofitClient(
        factories: Map<String, @JvmSuppressWildcards CallAdapter.Factory>,
        converters: Map<String, @JvmSuppressWildcards Converter.Factory>
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://www.gccompany.co.kr/")
            .addConverterFactory(
                converters[GSON_CONVERTER]
                    ?: throw IllegalArgumentException("Gson factory cannot be null!!")
            )
            .addCallAdapterFactory(
                factories[RX_FACTORY]
                    ?: throw IllegalArgumentException("Rx factory cannot be null!!")
            )
            .build()
    }

    private const val RX_FACTORY = "rx_factory"
    private const val GSON_CONVERTER = "gson_converter"
}