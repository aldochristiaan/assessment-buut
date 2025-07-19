package com.abnamro.apps.referenceandroid

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.abnamro.apps.referenceandroid.model.Comment
import com.abnamro.apps.referenceandroid.screens.CommentItem
import com.android.tools.screenshot.PreviewTest

class CommentItemScreenshotTest {

    @PreviewTest
    @Preview
    @Composable
    fun CommentItemPreview() = CommentItem(
        comment = Comment(
            "This is comment from A. Christian",
            "aldochristianpareira@gmail.com",
            1,
            "Aldo Christian",
            1
        )
    )
}