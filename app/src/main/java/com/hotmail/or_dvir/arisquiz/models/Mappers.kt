package com.hotmail.or_dvir.arisquiz.models

import com.hotmail.or_dvir.arisquiz.models.local.CategoryLocalModel
import com.hotmail.or_dvir.arisquiz.models.remote.CategoriesRemoteModel
import com.hotmail.or_dvir.arisquiz.models.remote.CategoryRemoteModel

internal fun CategoriesRemoteModel?.toLocalModels(): List<CategoryLocalModel> =
    this?.categories?.map { it.toLocalModel() }.orEmpty()

private fun CategoryRemoteModel.toLocalModel() = CategoryLocalModel(
    id = id,
    name = name
)
