package com.hotmail.or_dvir.arisquiz.ui.categoriesScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.hotmail.or_dvir.arisquiz.R
import com.hotmail.or_dvir.arisquiz.models.local.CategoryLocalModel
import com.hotmail.or_dvir.arisquiz.ui.FullScreenProgressIndicator
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
    var messageToUser by remember { mutableStateOf("") }

    LaunchedEffect(viewModel.categoriesOneTimeEvents) {
        viewModel.apply {
            categoriesOneTimeEvents.collectLatest {
                when (it) {
                    is CategoriesOneTimeEvents.Message -> messageToUser = it.message
                }
            }
        }
    }

    val state by viewModel.categoriesState.collectAsStateWithLifecycle()

    Box {
        ScreenContent(state)

        if (messageToUser.isNotBlank()) {
            AlertDialog(
                onDismissRequest = { messageToUser = "" },
                confirmButton = {
                    TextButton(onClick = { messageToUser = "" }) {
                        Text(stringResource(android.R.string.ok))
                    }
                },
                text = { Text(messageToUser) }
            )
        }

        // this must be last in the Box to show above everything else
        if (viewModel.isLoading) {
            FullScreenProgressIndicator()
        }
    }
}

@Composable
private fun ScreenContent(state: CategoriesScreenState) {
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        ScreenTitle()
        Spacer(modifier = Modifier.height(32.dp))
        CategoriesList(state.categories)
    }
}

@Composable
private fun ScreenTitle() {
    Text(
        modifier = Modifier.fillMaxWidth(),
        // todo test this on read device
        style = MaterialTheme.typography.displaySmall,
        text = stringResource(R.string.categories_screenTitle),
        textAlign = TextAlign.Center
    )
}

@Composable
private fun CategoriesList(categories: List<CategoryLocalModel>) {
    LazyVerticalGrid(
        modifier = Modifier.fillMaxSize(),
        // todo test minSize
        columns = GridCells.Adaptive(minSize = 150.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            key = { it.id },
            items = categories
        ) {
            Category(name = it.name, onClick = { /*todo*/ })
        }
    }
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

@Preview(showBackground = true, backgroundColor = 0xffffff)
@Composable
private fun ScreenPreview() {
    ScreenContent(
        state = CategoriesScreenState(
            categories = listOf(
                CategoryLocalModel("history", "1"),
                CategoryLocalModel("games", "2"),
                CategoryLocalModel("geography", "3"),
                CategoryLocalModel("celebrities", "4"),
                CategoryLocalModel("cinema", "5")
            )
        )
    )
}
