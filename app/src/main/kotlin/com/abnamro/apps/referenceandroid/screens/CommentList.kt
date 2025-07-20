package com.abnamro.apps.referenceandroid.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.abnamro.apps.referenceandroid.model.Comment
import com.abnamro.apps.referenceandroid.viewmodel.CommentsUIState

@Composable
fun CommentList(
    paddingValues: PaddingValues,
    commentsUIState: CommentsUIState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(paddingValues),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        AnimatedVisibility(visible = commentsUIState.comments.isNotEmpty()) {
            LazyColumn {
                items(items = commentsUIState.comments) { comment ->
                    CommentItem(comment = comment)
                }
            }
        }

        AnimatedVisibility(
            visible = (commentsUIState.comments.isEmpty() && !commentsUIState.isLoading)
        ) {
            Text("Please press the button to load comments", fontSize = 16.sp)
        }

        AnimatedVisibility(visible = commentsUIState.isLoading) {
            CircularProgressIndicator()
        }

        AnimatedVisibility(visible = commentsUIState.error != null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Warning,
                    contentDescription = "Error",
                    tint = Color.Red,
                    modifier = Modifier
                        .size(60.dp)
                        .padding(bottom = 10.dp)
                )
                Text(
                    text = commentsUIState.error.toString(),
                    color = Color.Red,
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview
@Composable
fun CommentListPreview() {
    val sampleComments = listOf(
        Comment(
            body = "This is a great article! Thanks for sharing.",
            email = "alice@example.com",
            id = 1,
            name = "Alice",
            postId = 101
        ),
        Comment(
            body = "I totally agree with your points.",
            email = "bob@example.com",
            id = 2,
            name = "Bob",
            postId = 101
        ),
        Comment(
            body = "Well written and insightful.",
            email = "charlie@example.com",
            id = 3,
            name = "Charlie",
            postId = 101
        )
    )

    val commentsUIState = CommentsUIState(
        comments = sampleComments,
        isLoading = false,
        error = null
    )

    CommentList(
        paddingValues = PaddingValues(16.dp),
        commentsUIState = commentsUIState
    )
}

@Preview()
@Composable
fun CommentListWidePaddingPreview() {
    val sampleComments = listOf(
        Comment(
            body = "This is a great article! Thanks for sharing.",
            email = "alice@example.com",
            id = 1,
            name = "Alice",
            postId = 101
        ),
        Comment(
            body = "I totally agree with your points.",
            email = "bob@example.com",
            id = 2,
            name = "Bob",
            postId = 101
        ),
        Comment(
            body = "Well written and insightful.",
            email = "charlie@example.com",
            id = 3,
            name = "Charlie",
            postId = 101
        )
    )

    val commentsUIState = CommentsUIState(
        comments = sampleComments,
        isLoading = false,
        error = null
    )

    CommentList(
        modifier = Modifier.padding(horizontal = 32.dp),
        paddingValues = PaddingValues(16.dp),
        commentsUIState = commentsUIState
    )
}
