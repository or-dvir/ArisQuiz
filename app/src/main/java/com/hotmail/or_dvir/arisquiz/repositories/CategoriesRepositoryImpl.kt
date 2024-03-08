package com.hotmail.or_dvir.arisquiz.repositories

import android.util.Log
import com.hotmail.or_dvir.arisquiz.models.local.CategoryLocalModel
import com.hotmail.or_dvir.arisquiz.models.toLocalModels
import com.hotmail.or_dvir.arisquiz.retrofit.RetrofitClient
import com.hotmail.or_dvir.arisquiz.retrofit.TriviaResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CategoriesRepositoryImpl : CategoriesRepository {
    private val logTag = CategoriesRepositoryImpl::class.java.simpleName

    override suspend fun getAllCategories(): TriviaResponse<List<CategoryLocalModel>> {
        return withContext(Dispatchers.IO) {
            try {
                val result =
                    RetrofitClient.triviaApi.getAllCategories().execute()

                TriviaResponse.fromResponseCode(
                    result.code(),
                    result.body().toLocalModels(),
                    result.message()
                )
            } catch (e: Exception) {
                Log.e(logTag, e.message, e)
                TriviaResponse.Exception()
            }
        }
    }
}
