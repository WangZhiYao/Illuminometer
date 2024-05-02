package com.paperloong.illuminometer.model

import com.paperloong.illuminometer.constant.IlluminanceUnit

/**
 *
 *
 * @author WangZhiYao
 * @since 2024/4/27
 */
data class DetectRecord(
    val id: Long = 0,
    val value: Float,
    val unit: IlluminanceUnit,
    val remark: String = "",
    val createTime: Long = System.currentTimeMillis()
)