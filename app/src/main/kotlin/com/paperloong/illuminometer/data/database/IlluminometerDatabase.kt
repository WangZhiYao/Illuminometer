package com.paperloong.illuminometer.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.paperloong.illuminometer.data.database.dao.DetectRecordDao
import com.paperloong.illuminometer.data.database.entity.DetectRecordEntity

/**
 *
 *
 * @author WangZhiYao
 * @since 2024/4/27
 */
@Database(
    entities = [DetectRecordEntity::class],
    version = 1
)
abstract class IlluminometerDatabase : RoomDatabase() {

    abstract fun detectRecordDao(): DetectRecordDao

}