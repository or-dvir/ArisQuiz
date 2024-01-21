package com.hotmail.or_dvir.arisquiz.retrofit

import com.hotmail.or_dvir.arisquiz.models.remote.CategoriesRemoteModel
import retrofit2.Call
import retrofit2.http.GET

interface TriviaApi {
    @GET("/api_category.php")
    fun getAllCategories(): Call<CategoriesRemoteModel>
}
