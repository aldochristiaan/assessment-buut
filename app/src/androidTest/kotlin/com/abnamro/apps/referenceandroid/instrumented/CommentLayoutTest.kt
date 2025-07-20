package com.abnamro.apps.referenceandroid.instrumented

import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.abnamro.apps.referenceandroid.model.Comment
import com.abnamro.apps.referenceandroid.screens.CommentItem
import com.abnamro.apps.referenceandroid.screens.CommentsListScreen
import com.abnamro.apps.referenceandroid.viewmodel.CommentsUIState
import com.kaspersky.components.alluresupport.withForcedAllureSupport
import com.kaspersky.components.composesupport.config.addComposeSupport
import com.kaspersky.kaspresso.kaspresso.Kaspresso
import com.kaspersky.kaspresso.testcases.api.testcase.TestCase
import org.junit.Rule
import org.junit.Test

class CommentLayoutTest:
    TestCase(kaspressoBuilder = Kaspresso.Builder.withForcedAllureSupport().addComposeSupport()) {

    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun validateFABisDisplayed() {
        composeTestRule.setContent {
            CommentsListScreen(
                commentsUIState = CommentsUIState(),
                snackBarHostState = SnackbarHostState(),
                onClick = {}
            )
        }
        composeTestRule.onNodeWithText("Comments").assertIsDisplayed()
        composeTestRule.onNodeWithTag("reload_button").assertIsDisplayed()
    }

    @Test
    fun validateButtonDisplayedAndClickable() {
        var clicked = false
        composeTestRule.setContent {
            CommentsListScreen(
                commentsUIState = CommentsUIState(),
                snackBarHostState = SnackbarHostState(),
                onClick = { clicked = true }
            )
        }
        composeTestRule.onNodeWithTag("reload_button").performClick()
        assert(clicked)
    }

    @Test
    fun validateButtonDisplayedAndNotClickable() {
        var clicked = false
        composeTestRule.setContent {
            CommentsListScreen(
                commentsUIState = CommentsUIState(isLoading = true),
                snackBarHostState = SnackbarHostState(),
                onClick = { clicked = true }
            )
        }
        assert(!clicked)
    }

    @Test
    fun validateSnackBaDisplayedAfterOnClick() {
        val initialState = CommentsUIState()
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
        var uiState by mutableStateOf(initialState)

        composeTestRule.setContent {
            CommentsListScreen(
                commentsUIState = uiState,
                snackBarHostState = SnackbarHostState(),
                onClick = { uiState = uiState.copy(comments = sampleComments) }
            )
        }

        composeTestRule.onNodeWithTag("reload_button").performClick()
        composeTestRule.onNodeWithText("Well written and insightful.").assertIsDisplayed()
    }

    @Test
    fun validateErrorState() {
        val errorState = CommentsUIState(
            comments = emptyList(),
            isLoading = false,
            error = "Something went wrong"
        )

        composeTestRule.setContent {
            CommentsListScreen(
                commentsUIState = errorState,
                snackBarHostState = SnackbarHostState(),
                onClick = { }
            )
        }
        composeTestRule.onNodeWithTag("reload_button").performClick()
        composeTestRule.onNodeWithText("Something went wrong").assertIsDisplayed()
    }

    @Test
    fun validateCommentItemDetailsDisplayed() {
        val comment = Comment(
            "This is comment from A. Christian",
            "aldochristianpareira@gmail.com",
            1,
            "Aldo Christian",
            1
        )

        composeTestRule.setContent {
            CommentItem(
                comment = comment
            )
        }
        composeTestRule.onNodeWithText("Aldo Christian").assertIsDisplayed()
        composeTestRule.onNodeWithText("aldochristianpareira@gmail.com").assertIsDisplayed()
        composeTestRule.onNodeWithText("This is comment from A. Christian").assertIsDisplayed()
    }
}