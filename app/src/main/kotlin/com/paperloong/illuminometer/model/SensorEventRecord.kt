package com.paperloong.illuminometer.model

/**
 *
 *
 * @author WangZhiYao
 * @since 2024/4/25
 */
data class SensorEventRecord(
    val value: Float,
    val recordTime: Long = System.currentTimeMillis()
)
