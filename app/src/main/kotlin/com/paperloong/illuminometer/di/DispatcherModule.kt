package com.paperloong.illuminometer.di

import com.paperloong.illuminometer.constant.Dispatcher
import com.paperloong.illuminometer.constant.IlluminometerDispatcher
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 *
 *
 * @author WangZhiYao
 * @since 2024/4/24
 */
@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {

    @Provides
    @Dispatcher(IlluminometerDispatcher.IO)
    fun providerIODispatcher(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(IlluminometerDispatcher.DEFAULT)
    fun providerDefaultDispatcher(): CoroutineDispatcher = Dispatchers.Default

}