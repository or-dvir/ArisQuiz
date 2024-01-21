package com.hotmail.or_dvir.arisquiz.di

import com.hotmail.or_dvir.arisquiz.repositories.CategoriesRepository
import com.hotmail.or_dvir.arisquiz.repositories.CategoriesRepositoryImpl
import com.hotmail.or_dvir.arisquiz.ui.categoriesScreen.CategoriesViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

internal val appModule = module {
    single<CategoriesRepository> { CategoriesRepositoryImpl() }

    viewModel { CategoriesViewModel(androidApplication(), get()) }
}
