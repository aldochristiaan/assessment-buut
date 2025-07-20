package com.abnamro.apps.referenceandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.abnamro.apps.referenceandroid.screens.CommentsListScreen
import com.abnamro.apps.referenceandroid.viewmodel.CommentsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val viewModel: CommentsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val commentsUIState by viewModel.commentsUIState.collectAsStateWithLifecycle()
            val snackBarHostState = remember { SnackbarHostState() }

            CommentsListScreen(
                commentsUIState = commentsUIState,
                onClick = viewModel::getComments,
                snackBarHostState = snackBarHostState
            )

            LaunchedEffect(Unit) {
                viewModel.uiEvent.collect { event ->
                    when (event) {
                        is CommentsViewModel.UIEvent.ShowSnackBar -> {
                            snackBarHostState.showSnackbar(event.message)
                        }
                    }
                }
            }
        }
    }
}
