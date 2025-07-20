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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import com.abnamro.apps.referenceandroid.viewmodel.CommentsUIState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CommentsListScreen(
    commentsUIState: CommentsUIState,
    snackBarHostState: SnackbarHostState,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Scaffold(
        modifier = Modifier.semantics { testTag = "compose_main_screen" },
        topBar = {
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
                        onClick()
                    }
                },
                modifier = Modifier.testTag("reload_button"),
            ) {
                Icon(imageVector = Icons.Default.Refresh, contentDescription = "Reload comment")
            }
        }, snackbarHost = {
            SnackbarHost(
                modifier = modifier.semantics { testTag = "snackBar" },
                hostState = snackBarHostState
            )
        }, content = { paddingValues ->
            CommentList(paddingValues, commentsUIState)
        })
}




