package com.paperloong.illuminometer.di

import android.content.Context
import androidx.room.Room
import com.paperloong.illuminometer.data.database.IlluminometerDatabase
import com.paperloong.illuminometer.data.database.dao.DetectRecordDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 *
 *
 * @author WangZhiYao
 * @since 2024/4/27
 */
@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "illuminometer.db"

    @Provides
    @Singleton
    fun provideIlluminometerDatabase(@ApplicationContext appContext: Context): IlluminometerDatabase =
        Room.databaseBuilder(appContext, IlluminometerDatabase::class.java, DATABASE_NAME)
            .build()

    @Provides
    @Singleton
    fun provideDetectRecordDao(database: IlluminometerDatabase): DetectRecordDao =
        database.detectRecordDao()
}