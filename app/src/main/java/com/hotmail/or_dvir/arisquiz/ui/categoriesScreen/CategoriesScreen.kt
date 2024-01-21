package com.hotmail.or_dvir.arisquiz.ui.categoriesScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.compose.koinViewModel

@RootNavGraph(start = true)
@Destination
@Composable
fun CategorySelectionScreen(
    navigator: DestinationsNavigator,
    viewModel: CategoriesViewModel = koinViewModel()
) {
    LaunchedEffect(Unit) {
        viewModel.apply {
            getAllCategories()

            categoriesOneTimeEvents.collectLatest {
                when (it) {
                    is CategoriesOneTimeEvents.Message -> {
                        //todo
                    }
                }
            }
        }
    }

    val state by viewModel.categoriesState.collectAsStateWithLifecycle()

    Text("${state.categories.size} categories")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun Category(
    name: String,
    onClick: () -> Unit
) {
    OutlinedCard(onClick = onClick) {
        Box(
            modifier = Modifier.padding(16.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = name,
                // todo
                //  see which one looks best on real device
                //  color the text?
                style = MaterialTheme.typography.headlineLarge
//                style = MaterialTheme.typography.displaySmall
//                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryPreview() {
    Box(Modifier.padding(8.dp)) {
        Category(
            name = "category name",
            onClick = {}
        )
    }
}
