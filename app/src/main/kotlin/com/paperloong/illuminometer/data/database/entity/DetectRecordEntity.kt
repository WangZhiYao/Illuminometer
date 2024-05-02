package com.paperloong.illuminometer.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.paperloong.illuminometer.constant.IlluminanceUnit

/**
 *
 *
 * @author WangZhiYao
 * @since 2024/4/27
 */
@Entity(tableName = "detect_record")
data class DetectRecordEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    val value: Float,
    val unit: IlluminanceUnit,
    val remark: String,
    @ColumnInfo(name = "create_time")
    val createTime: Long
)