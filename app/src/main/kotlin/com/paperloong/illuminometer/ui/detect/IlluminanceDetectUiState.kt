package com.paperloong.illuminometer.ui.detect

import com.paperloong.illuminometer.constant.IlluminanceUnit

/**
 *
 *
 * @author WangZhiYao
 * @since 2024/4/23
 */
data class IlluminanceDetectUiState(
    val min: Float = 0f,
    val avg: Float = 0f,
    val max: Float = 0f,
    val current: Float = 0f,
    val unit: IlluminanceUnit = IlluminanceUnit.LUX,
    val time: Long = System.currentTimeMillis(),
    val initializedZero: Boolean = true
)
