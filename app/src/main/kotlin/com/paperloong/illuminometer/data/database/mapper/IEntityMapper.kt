package com.paperloong.illuminometer.data.database.mapper

/**
 *
 *
 * @author WangZhiYao
 * @since 2024/4/29
 */
interface IEntityMapper<E, M> {

    fun mapToEntity(model: M): E

    fun mapToModel(entity: E): M

}