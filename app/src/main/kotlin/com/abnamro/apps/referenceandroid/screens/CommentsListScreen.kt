package com.abnamro.apps.referenceandroid.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abnamro.apps.referenceandroid.viewmodel.CommentsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsListScreen(
    viewModel: CommentsViewModel,
    modifier: Modifier = Modifier
) {
    val snackBarHostState = remember { SnackbarHostState() }
    val commentsUIState by viewModel.commentsUIState.collectAsStateWithLifecycle()

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is CommentsViewModel.UIEvent.ShowSnackBar -> {
                    snackBarHostState.showSnackbar(event.message)
                }
            }
        }
    }

    Scaffold(modifier = modifier, topBar = {
        TopAppBar(
            title = { Text(text = "Comments") }, colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primary,
                titleContentColor = MaterialTheme.colorScheme.onPrimary,
            )
        )
    }, floatingActionButton = {
        FloatingActionButton(
            onClick = {
                if (!commentsUIState.isLoading) {
                    viewModel.getComments()
                }
            },
            modifier = Modifier.testTag("reload_button")
        ) {
            Icon(imageVector = Icons.Default.Refresh, contentDescription = "Refresh comment")
        }
    }, snackbarHost = {
        SnackbarHost(hostState = snackBarHostState)
    }, content = { paddingValues ->
        CommentList(paddingValues, commentsUIState)
    })
}


