package com.paperloong.illuminometer.ui.detect

import com.paperloong.illuminometer.constant.IlluminanceUnit

/**
 *
 *
 * @author WangZhiYao
 * @since 2024/4/23
 */
data class IlluminanceDetectUiState(
    val min: String = "",
    val avg: String = "",
    val max: String = "",
    val current: String = "",
    val unit: IlluminanceUnit? = null
)
