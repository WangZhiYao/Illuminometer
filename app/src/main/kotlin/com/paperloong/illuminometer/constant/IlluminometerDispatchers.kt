package com.paperloong.illuminometer.constant

import javax.inject.Qualifier

/**
 *
 *
 * @author WangZhiYao
 * @since 2024/4/24
 */

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val illuminometerDispatcher: IlluminometerDispatcher)

enum class IlluminometerDispatcher {

    DEFAULT,

    IO
}