package com.abnamro.apps.referenceandroid

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import com.abnamro.apps.referenceandroid.model.Comment
import com.abnamro.apps.referenceandroid.screens.CommentItem
import com.abnamro.apps.referenceandroid.screens.CommentsListScreen
import org.junit.Rule
import org.junit.Test

class ExampleTest {

    @get:Rule
    val rule = createComposeRule();

    @Test
    fun testHOOHOHOH() {
        rule.setContent {
            CommentItem(
                comment = Comment(
                    "This is comment from A. Christian",
                    "aldochristianpareira@gmail.com",
                    1,
                    "Aldo Christian",
                    1
                )
            )
        }
    }
}