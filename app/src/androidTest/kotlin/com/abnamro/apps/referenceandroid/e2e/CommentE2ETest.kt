package com.abnamro.apps.referenceandroid.e2e

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.abnamro.apps.referenceandroid.MainActivity
import org.junit.Rule
import org.junit.Test

class CommentE2ETest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule(MainActivity::class.java)

    @Test
    fun validateCommentAreLoadedAndSnackBarDisplayed() {
        composeTestRule.onNodeWithTag("reload_button").performClick()
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onAllNodesWithText("Comments loaded!").fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeTestRule.onNodeWithText("Comments loaded!").assertIsDisplayed()
    }
}