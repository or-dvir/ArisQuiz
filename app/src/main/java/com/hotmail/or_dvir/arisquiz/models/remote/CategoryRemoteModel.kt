package com.hotmail.or_dvir.arisquiz.models.remote

import com.google.gson.annotations.SerializedName

data class CategoryRemoteModel(
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: String
)
