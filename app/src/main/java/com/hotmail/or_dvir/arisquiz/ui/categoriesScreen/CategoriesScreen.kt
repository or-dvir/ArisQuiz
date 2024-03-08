package com.hotmail.or_dvir.arisquiz.ui.categoriesScreen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
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
        Column {
            Text("${state.categories.size} categories")

            // this must be last in the colum to show above everything else
            if (viewModel.isLoading) {
                FullScreenProgressIndicator()
            }
        }

        if(messageToUser.isNotBlank()) {
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
