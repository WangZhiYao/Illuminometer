package com.paperloong.illuminometer.data.database.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import com.paperloong.illuminometer.data.database.entity.DetectRecordEntity

/**
 *
 *
 * @author WangZhiYao
 * @since 2024/4/27
 */
@Dao
interface DetectRecordDao : IDao<DetectRecordEntity> {

    @Query("SELECT * FROM detect_record ORDER BY create_time DESC")
    fun observeDetectRecordList(): PagingSource<Int, DetectRecordEntity>

    @Query("DELETE FROM detect_record")
    suspend fun deleteAllRecord(): Int

}