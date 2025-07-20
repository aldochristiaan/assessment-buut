package com.abnamro.apps.referenceandroid.e2e.screen

import androidx.compose.ui.test.SemanticsNodeInteractionsProvider
import io.github.kakaocup.compose.node.element.ComposeScreen
import io.github.kakaocup.compose.node.element.KNode

class CommentScreen(semanticsProvider: SemanticsNodeInteractionsProvider) :
    ComposeScreen<CommentScreen>(
        semanticsProvider = semanticsProvider,
        viewBuilderAction = { hasTestTag("compose_main_screen") }
    ) {
    val reloadButton: KNode = child {
        hasTestTag("reload_button")
    }

    val commentItem: KNode = child {
        hasTestTag("comment_item")
    }

    val snackBar: KNode = child {
        hasTestTag("snackBar")
    }
}