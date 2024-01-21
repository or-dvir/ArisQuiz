package com.hotmail.or_dvir.arisquiz.ui.categoriesScreen

import android.app.Application
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.hotmail.or_dvir.arisquiz.R
import com.hotmail.or_dvir.arisquiz.models.local.CategoryLocalModel
import com.hotmail.or_dvir.arisquiz.repositories.CategoriesRepository
import com.hotmail.or_dvir.arisquiz.retrofit.TriviaResponse
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class CategoriesViewModel(
    private val app: Application,
    private val repo: CategoriesRepository
) : AndroidViewModel(app) {

    private var isLoading by mutableStateOf(false)

    private val _categoriesState = MutableStateFlow(CategoriesScreenState())
    internal val categoriesState = _categoriesState.asStateFlow()

    private val _categoriesOneTimeEvents = Channel<CategoriesOneTimeEvents>()
    internal val categoriesOneTimeEvents = _categoriesOneTimeEvents.receiveAsFlow()

    fun getAllCategories() = viewModelScope.launch {
        _categoriesState.apply {
            isLoading = true
            val result = repo.getAllCategories()
            Log.i("aaaaa", result.toString())
            Log.i("aaaaa", "data " + result.data)
            isLoading = false

            when (repo.getAllCategories()) {
                // success result means `data` is not null
                is TriviaResponse.Success -> emit(value.copy(categories = result.data!!))
                is TriviaResponse.Exception,
                is TriviaResponse.HttpError -> _categoriesOneTimeEvents.send(
                    CategoriesOneTimeEvents.Message(
                        app.getString(
                            R.string.categories_unknownServerError_d,
                            result.responseCode
                        )
                    )
                )

                is TriviaResponse.InvalidParameter,
                is TriviaResponse.NoResult,
                is TriviaResponse.TokenNotFound -> _categoriesOneTimeEvents.send(
                    CategoriesOneTimeEvents.Message(
                        app.getString(
                            R.string.categories_generalServerError_d,
                            result.responseCode
                        )
                    )
                )

                is TriviaResponse.RateLimit -> TODO()
                is TriviaResponse.TokenEmpty -> TODO()
            }
        }
    }
}

internal data class CategoriesScreenState(
    val categories: List<CategoryLocalModel> = emptyList()
)

sealed class CategoriesOneTimeEvents {
    data class Message(val message: String) : CategoriesOneTimeEvents()
}
