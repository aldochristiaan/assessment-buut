package com.abnamro.apps.referenceandroid

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abnamro.apps.referenceandroid.model.Comment
import com.abnamro.apps.referenceandroid.screens.CommentList
import com.abnamro.apps.referenceandroid.viewmodel.CommentsUIState
import com.android.tools.screenshot.PreviewTest

class CommentListScreenshotTest {

    @PreviewTest
    @Preview
    @Composable
    fun CommentListDataLoaded() {
        val sampleComments = listOf(
            Comment(
                body = "This is a great article! Thanks for sharing.",
                email = "alice@example.com",
                id = 1,
                name = "Alice",
                postId = 103
            ),
            Comment(
                body = "I totally agree with your points.",
                email = "bob@example.com",
                id = 2,
                name = "Bob",
                postId = 102
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
            commentsUIState = commentsUIState,
            useLazyColumn = false
        )
    }

    @PreviewTest
    @Preview
    @Composable
    fun CommentListEmptyData() {
        val commentsUIState = CommentsUIState(
            comments = emptyList(),
            isLoading = false,
            error = null
        )

        CommentList(
            paddingValues = PaddingValues(16.dp),
            commentsUIState = commentsUIState
        )
    }

    @PreviewTest
    @Preview
    @Composable
    fun CommentListLoadingState() {
        val commentsUIState = CommentsUIState(
            comments = emptyList(),
            isLoading = true,
            error = null
        )

        CommentList(
            paddingValues = PaddingValues(16.dp),
            commentsUIState = commentsUIState
        )
    }

    @PreviewTest
    @Preview
    @Composable
    fun CommentListErrorState() {
        val commentsUIState = CommentsUIState(
            comments = emptyList(),
            isLoading = false,
            error = "Something went wrong. \n Please try again."
        )

        CommentList(
            paddingValues = PaddingValues(16.dp),
            commentsUIState = commentsUIState,
        )
    }
}