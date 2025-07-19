package com.abnamro.apps.referenceandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.abnamro.apps.referenceandroid.screens.CommentsListScreen
import com.abnamro.apps.referenceandroid.viewmodel.CommentsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : ComponentActivity() {
    val viewModel: CommentsViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CommentsListScreen(
                viewModel = viewModel
            )
        }
    }
}
