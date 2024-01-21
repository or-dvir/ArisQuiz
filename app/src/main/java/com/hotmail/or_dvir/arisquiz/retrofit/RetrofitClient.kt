package com.hotmail.or_dvir.arisquiz.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://opentdb.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    var triviaApi: TriviaApi = retrofit.create(TriviaApi::class.java)
}
