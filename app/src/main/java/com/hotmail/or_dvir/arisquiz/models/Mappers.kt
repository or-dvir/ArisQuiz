package com.hotmail.or_dvir.arisquiz.models

import com.hotmail.or_dvir.arisquiz.models.local.CategoryLocalModel
import com.hotmail.or_dvir.arisquiz.models.remote.CategoryRemoteModel

fun List<CategoryRemoteModel>?.toLocalModels(): List<CategoryLocalModel> =
    this?.map { it.toLocalModel() }.orEmpty()

fun CategoryRemoteModel.toLocalModel() = CategoryLocalModel(
    id = id,
    name = name
)
