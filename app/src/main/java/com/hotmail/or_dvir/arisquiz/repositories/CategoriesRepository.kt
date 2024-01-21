package com.hotmail.or_dvir.arisquiz.repositories

import com.hotmail.or_dvir.arisquiz.models.local.CategoryLocalModel
import com.hotmail.or_dvir.arisquiz.retrofit.TriviaResponse

interface CategoriesRepository {
    suspend fun getAllCategories(): TriviaResponse<List<CategoryLocalModel>>
}
