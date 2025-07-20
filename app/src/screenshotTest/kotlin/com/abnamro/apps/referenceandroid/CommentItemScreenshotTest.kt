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
    fun CommentItemNormal() = CommentItem(
        comment = Comment(
            "This is comment from A. Christian",
            "aldochristianpareira@gmail.com",
            1,
            "Aldo Christian",
            1
        )
    )

    @PreviewTest
    @Preview
    @Composable
    fun CommentItemLongBody() = CommentItem(
        comment = Comment(
            "This is comment from A. Christian, This is comment from A. Christian, " +
                    "This is comment from A. Christian, This is comment from A. Christian, " +
                    "This is comment from A. Christian, This is comment from A. Christian",
            "aldochristianpareira@gmail.com",
            1,
            "Aldo Christian",
            1
        )
    )

    @PreviewTest
    @Preview
    @Composable
    fun CommentItemLongEmail() = CommentItem(
        comment = Comment(
            "This is comment from A. Christian",
            "aldochristianpareiraaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa@gmail.com",
            1,
            "Aldo Christian",
            1
        )
    )

    @PreviewTest
    @Preview
    @Composable
    fun CommentItemLongName() = CommentItem(
        comment = Comment(
            "This is comment from A. Christian",
            "aldochristianpareira@gmail.com",
            1,
            "Aldo Christian Aldo Christian Aldo Christian Aldo Christian",
            1
        )
    )
}